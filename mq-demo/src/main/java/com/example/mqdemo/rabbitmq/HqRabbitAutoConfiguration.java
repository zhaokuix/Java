
package com.example.mqdemo.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.util.stream.Collectors;


/**
 * rabbit自动配置类
 */

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ RabbitTemplate.class, Channel.class })
@EnableConfigurationProperties(HqRabbitProperties.class)
public class HqRabbitAutoConfiguration {


	/**
	 * 自定义mq数据源
	 */

	@Configuration(proxyBeanMethods = false)
	//@ConditionalOnMissingBean(ConnectionFactory.class)
	protected static class HqRabbitConnectionFactoryCreator {

		//@ConfigurationProperties(prefix = "hq.rabbitmq")
		@ConditionalOnProperty(name = "hq.rabbitmq.enable", havingValue = "true", matchIfMissing = false)
		@Bean(value = "hqRabbitmqProperties")
		public HqRabbitProperties hqRabbitProperties() {
			return new HqRabbitProperties();
		}


		@Bean(value = "hqConnectionFactory")
		public ConnectionFactory rabbitConnectionFactory(ConnectionFactory connectionFactory,
														 @Autowired(required = false) @Qualifier("hqRabbitmqProperties") HqRabbitProperties properties,
														  ObjectProvider<ConnectionNameStrategy> connectionNameStrategy) throws Exception {
			if (properties == null) {
				log.debug("【rabbitmq多数据源】使用默认的rabbit连接工厂{}",connectionFactory);
				return connectionFactory;
			}
			PropertyMapper map = PropertyMapper.get();
			CachingConnectionFactory factory = new CachingConnectionFactory(
					getRabbitConnectionFactoryBean(properties).getObject());
			map.from(properties::determineAddresses).to(factory::setAddresses);
			map.from(properties::isPublisherReturns).to(factory::setPublisherReturns);
			map.from(properties::getPublisherConfirmType).whenNonNull().to(factory::setPublisherConfirmType);
			RabbitProperties.Cache.Channel channel = properties.getCache().getChannel();
			map.from(channel::getSize).whenNonNull().to(factory::setChannelCacheSize);
			map.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis)
					.to(factory::setChannelCheckoutTimeout);
			RabbitProperties.Cache.Connection connection = properties.getCache().getConnection();
			map.from(connection::getMode).whenNonNull().to(factory::setCacheMode);
			map.from(connection::getSize).whenNonNull().to(factory::setConnectionCacheSize);
			map.from(connectionNameStrategy::getIfUnique).whenNonNull().to(factory::setConnectionNameStrategy);
			return factory;
 		}

		private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean(HqRabbitProperties properties) {
			PropertyMapper map = PropertyMapper.get();
			RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
			map.from(properties::determineHost).whenNonNull().to(factory::setHost);
			map.from(properties::determinePort).to(factory::setPort);
			map.from(properties::determineUsername).whenNonNull().to(factory::setUsername);
			map.from(properties::determinePassword).whenNonNull().to(factory::setPassword);
			map.from(properties::determineVirtualHost).whenNonNull().to(factory::setVirtualHost);
			map.from(properties::getRequestedHeartbeat).whenNonNull().asInt(Duration::getSeconds)
					.to(factory::setRequestedHeartbeat);
			HqRabbitProperties.Ssl ssl = properties.getSsl();
			if (ssl.determineEnabled()) {
				factory.setUseSSL(true);
				map.from(ssl::getAlgorithm).whenNonNull().to(factory::setSslAlgorithm);
				map.from(ssl::getKeyStoreType).to(factory::setKeyStoreType);
				map.from(ssl::getKeyStore).to(factory::setKeyStore);
				map.from(ssl::getKeyStorePassword).to(factory::setKeyStorePassphrase);
				map.from(ssl::getTrustStoreType).to(factory::setTrustStoreType);
				map.from(ssl::getTrustStore).to(factory::setTrustStore);
				map.from(ssl::getTrustStorePassword).to(factory::setTrustStorePassphrase);
				map.from(ssl::isValidateServerCertificate)
						.to((validate) -> factory.setSkipServerCertificateValidation(!validate));
				map.from(ssl::getVerifyHostname).to(factory::setEnableHostnameVerification);
			}
			map.from(properties::getConnectionTimeout).whenNonNull().asInt(Duration::toMillis)
					.to(factory::setConnectionTimeout);
			factory.afterPropertiesSet();
			return factory;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Import(HqRabbitConnectionFactoryCreator.class)
	protected static class HqRabbitTemplateConfiguration {

		@Bean(value = "hqRabbitTemplate")
		//@ConditionalOnSingleCandidate(ConnectionFactory.class)
		//@ConditionalOnMissingBean(RabbitOperations.class)
		public RabbitTemplate hqRabbitTemplate(RabbitTemplate rabbitTemplate,
												@Autowired(required = false) @Qualifier("hqRabbitmqProperties") HqRabbitProperties properties,
												ObjectProvider<MessageConverter> messageConverter,
												ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers,
												@Autowired(required = false) @Qualifier("hqConnectionFactory") ConnectionFactory connectionFactory) {
			if (properties == null) {
				log.debug("【rabbitmq多数据源】使用默认的rabbitTemplate {}",rabbitTemplate);
				return rabbitTemplate;
			}
			PropertyMapper map = PropertyMapper.get();
			RabbitTemplate template = new RabbitTemplate(connectionFactory);
			messageConverter.ifUnique(template::setMessageConverter);
			template.setMandatory(determineMandatoryFlag(properties));
			RabbitProperties.Template templateProperties = properties.getTemplate();
			if (templateProperties.getRetry().isEnabled()) {
				template.setRetryTemplate(
						new HqRetryTemplateFactory(retryTemplateCustomizers.orderedStream().collect(Collectors.toList()))
								.createRetryTemplate(templateProperties.getRetry(),
										RabbitRetryTemplateCustomizer.Target.SENDER));
			}
			map.from(templateProperties::getReceiveTimeout).whenNonNull().as(Duration::toMillis)
					.to(template::setReceiveTimeout);
			map.from(templateProperties::getReplyTimeout).whenNonNull().as(Duration::toMillis)
					.to(template::setReplyTimeout);
			map.from(templateProperties::getExchange).to(template::setExchange);
			map.from(templateProperties::getRoutingKey).to(template::setRoutingKey);
			map.from(templateProperties::getDefaultReceiveQueue).whenNonNull().to(template::setDefaultReceiveQueue);
			return template;
		}

		private boolean determineMandatoryFlag(HqRabbitProperties properties) {
			Boolean mandatory = properties.getTemplate().getMandatory();
			return (mandatory != null) ? mandatory : properties.isPublisherReturns();
		}
	}
}

