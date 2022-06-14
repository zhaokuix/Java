package com.example.mqdemo.rabbitmq.defalutconfig;//package com.yonyougov.fbp.basedata.common.rabbitmq.defalutconfig;

import com.example.mqdemo.rabbitmq.HqRetryTemplateFactory;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * rabbit自动配置类
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({RabbitTemplate.class, Channel.class})
@EnableConfigurationProperties(RabbitProperties.class)
public class DefaultRabbitAutoConfiguration {

	/**
	 * 默认mq数据源
	 */
	@Configuration(proxyBeanMethods = false)
	protected static class DefaultRabbitConnectionFactoryCreator {
		protected DefaultRabbitConnectionFactoryCreator() {
		}

		@Bean
		@Primary
		public CachingConnectionFactory rabbitConnectionFactory(RabbitProperties properties,
																ObjectProvider<ConnectionNameStrategy> connectionNameStrategy) throws Exception {
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

		private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean(RabbitProperties properties)
				throws Exception {
			PropertyMapper map = PropertyMapper.get();
			RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
			map.from(properties::determineHost).whenNonNull().to(factory::setHost);
			map.from(properties::determinePort).to(factory::setPort);
			map.from(properties::determineUsername).whenNonNull().to(factory::setUsername);
			map.from(properties::determinePassword).whenNonNull().to(factory::setPassword);
			map.from(properties::determineVirtualHost).whenNonNull().to(factory::setVirtualHost);
			map.from(properties::getRequestedHeartbeat).whenNonNull().asInt(Duration::getSeconds)
					.to(factory::setRequestedHeartbeat);
			RabbitProperties.Ssl ssl = properties.getSsl();
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
	protected static class DefaultRabbitTemplateConfiguration {
		protected DefaultRabbitTemplateConfiguration() {
		}


		@Bean
		@Primary
		public RabbitTemplate rabbitTemplate(RabbitProperties properties,
											 ObjectProvider<MessageConverter> messageConverter,
											 ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers,
											 ConnectionFactory connectionFactory) {
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

		private boolean determineMandatoryFlag(RabbitProperties properties) {
			Boolean mandatory = properties.getTemplate().getMandatory();
			return (mandatory != null) ? mandatory : properties.isPublisherReturns();
		}
	}
}
