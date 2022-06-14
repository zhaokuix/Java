package com.example.mqdemo.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@ConfigurationProperties(
        prefix = "hq.rabbitmq"
)
@Component
public class HqRabbitProperties {
    private static final int DEFAULT_PORT = 5672;
    private static final int DEFAULT_PORT_SECURE = 5671;
    private String host = "";
    private Integer port;
    private String username = "";
    private String password = "";
    private final Ssl ssl = new Ssl();
    private String virtualHost;
    private String addresses;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration requestedHeartbeat;
    private int requestedChannelMax = 2047;
    private boolean publisherReturns;
    private ConfirmType publisherConfirmType;
    private Duration connectionTimeout;
    private final RabbitProperties.Cache cache = new RabbitProperties.Cache();
    private final RabbitProperties.Listener listener = new RabbitProperties.Listener();
    private final RabbitProperties.Template template = new RabbitProperties.Template();
    private List<Address> parsedAddresses;

    public HqRabbitProperties() {
    }

    public String getHost() {
        return this.host;
    }

    public String determineHost() {
        return CollectionUtils.isEmpty(this.parsedAddresses) ? this.getHost() : ((Address)this.parsedAddresses.get(0)).host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public int determinePort() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            Integer port = this.getPort();
            if (port != null) {
                return port;
            } else {
                return (Boolean)Optional.ofNullable(this.getSsl().getEnabled()).orElse(false) ? 5671 : 5672;
            }
        } else {
            return ((Address)this.parsedAddresses.get(0)).port;
        }
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAddresses() {
        return this.addresses;
    }

    public String determineAddresses() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.host + ":" + this.determinePort();
        } else {
            List<String> addressStrings = new ArrayList();
            Iterator var2 = this.parsedAddresses.iterator();

            while(var2.hasNext()) {
                Address parsedAddress = (Address)var2.next();
                addressStrings.add(parsedAddress.host + ":" + parsedAddress.port);
            }

            return StringUtils.collectionToCommaDelimitedString(addressStrings);
        }
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
        this.parsedAddresses = this.parseAddresses(addresses);
    }

    private List<Address> parseAddresses(String addresses) {
        List<Address> parsedAddresses = new ArrayList();
        String[] var3 = StringUtils.commaDelimitedListToStringArray(addresses);
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String address = var3[var5];
            parsedAddresses.add(new Address(address, (Boolean)Optional.ofNullable(this.getSsl().getEnabled()).orElse(false)));
        }

        return parsedAddresses;
    }

    public String getUsername() {
        return this.username;
    }

    public String determineUsername() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.username;
        } else {
            Address address = (Address)this.parsedAddresses.get(0);
            return address.username != null ? address.username : this.username;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public String determinePassword() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.getPassword();
        } else {
            Address address = (Address)this.parsedAddresses.get(0);
            return address.password != null ? address.password : this.getPassword();
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ssl getSsl() {
        return this.ssl;
    }

    public String getVirtualHost() {
        return this.virtualHost;
    }

    public String determineVirtualHost() {
        if (CollectionUtils.isEmpty(this.parsedAddresses)) {
            return this.getVirtualHost();
        } else {
            Address address = (Address)this.parsedAddresses.get(0);
            return address.virtualHost != null ? address.virtualHost : this.getVirtualHost();
        }
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = "".equals(virtualHost) ? "/" : virtualHost;
    }

    public Duration getRequestedHeartbeat() {
        return this.requestedHeartbeat;
    }

    public void setRequestedHeartbeat(Duration requestedHeartbeat) {
        this.requestedHeartbeat = requestedHeartbeat;
    }

    public int getRequestedChannelMax() {
        return this.requestedChannelMax;
    }

    public void setRequestedChannelMax(int requestedChannelMax) {
        this.requestedChannelMax = requestedChannelMax;
    }

    public boolean isPublisherReturns() {
        return this.publisherReturns;
    }

    public void setPublisherReturns(boolean publisherReturns) {
        this.publisherReturns = publisherReturns;
    }

    public Duration getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setPublisherConfirmType(ConfirmType publisherConfirmType) {
        this.publisherConfirmType = publisherConfirmType;
    }

    public ConfirmType getPublisherConfirmType() {
        return this.publisherConfirmType;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public RabbitProperties.Cache getCache() {
        return this.cache;
    }

    public RabbitProperties.Listener getListener() {
        return this.listener;
    }

    public RabbitProperties.Template getTemplate() {
        return this.template;
    }

    private static final class Address {
        private static final String PREFIX_AMQP = "amqp://";
        private static final String PREFIX_AMQP_SECURE = "amqps://";
        private String host;
        private int port;
        private String username;
        private String password;
        private String virtualHost;
        private Boolean secureConnection;

        private Address(String input, boolean sslEnabled) {
            input = input.trim();
            input = this.trimPrefix(input);
            input = this.parseUsernameAndPassword(input);
            input = this.parseVirtualHost(input);
            this.parseHostAndPort(input, sslEnabled);
        }

        private String trimPrefix(String input) {
            if (input.startsWith("amqps://")) {
                this.secureConnection = true;
                return input.substring("amqps://".length());
            } else if (input.startsWith("amqp://")) {
                this.secureConnection = false;
                return input.substring("amqp://".length());
            } else {
                return input;
            }
        }

        private String parseUsernameAndPassword(String input) {
            if (input.contains("@")) {
                String[] split = StringUtils.split(input, "@");
                String creds = split[0];
                input = split[1];
                split = StringUtils.split(creds, ":");
                this.username = split[0];
                if (split.length > 0) {
                    this.password = split[1];
                }
            }

            return input;
        }

        private String parseVirtualHost(String input) {
            int hostIndex = input.indexOf(47);
            if (hostIndex >= 0) {
                this.virtualHost = input.substring(hostIndex + 1);
                if (this.virtualHost.isEmpty()) {
                    this.virtualHost = "/";
                }

                input = input.substring(0, hostIndex);
            }

            return input;
        }

        private void parseHostAndPort(String input, boolean sslEnabled) {
            int portIndex = input.indexOf(58);
            if (portIndex == -1) {
                this.host = input;
                this.port = this.determineSslEnabled(sslEnabled) ? 5671 : 5672;
            } else {
                this.host = input.substring(0, portIndex);
                this.port = Integer.parseInt(input.substring(portIndex + 1));
            }

        }

        private boolean determineSslEnabled(boolean sslEnabled) {
            return this.secureConnection != null ? this.secureConnection : sslEnabled;
        }
    }

    public static class ListenerRetry extends RabbitProperties.Retry {
        private boolean stateless = true;

        public ListenerRetry() {
        }

        public boolean isStateless() {
            return this.stateless;
        }

        public void setStateless(boolean stateless) {
            this.stateless = stateless;
        }
    }

    public static class Retry {
        private boolean enabled;
        private int maxAttempts = 3;
        private Duration initialInterval = Duration.ofMillis(1000L);
        private double multiplier = 1.0D;
        private Duration maxInterval = Duration.ofMillis(10000L);

        public Retry() {
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxAttempts() {
            return this.maxAttempts;
        }

        public void setMaxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public Duration getInitialInterval() {
            return this.initialInterval;
        }

        public void setInitialInterval(Duration initialInterval) {
            this.initialInterval = initialInterval;
        }

        public double getMultiplier() {
            return this.multiplier;
        }

        public void setMultiplier(double multiplier) {
            this.multiplier = multiplier;
        }

        public Duration getMaxInterval() {
            return this.maxInterval;
        }

        public void setMaxInterval(Duration maxInterval) {
            this.maxInterval = maxInterval;
        }
    }

    public static class Template {
        private final RabbitProperties.Retry retry = new RabbitProperties.Retry();
        private Boolean mandatory;
        private Duration receiveTimeout;
        private Duration replyTimeout;
        private String exchange = "";
        private String routingKey = "";
        private String defaultReceiveQueue;

        public Template() {
        }

        public RabbitProperties.Retry getRetry() {
            return this.retry;
        }

        public Boolean getMandatory() {
            return this.mandatory;
        }

        public void setMandatory(Boolean mandatory) {
            this.mandatory = mandatory;
        }

        public Duration getReceiveTimeout() {
            return this.receiveTimeout;
        }

        public void setReceiveTimeout(Duration receiveTimeout) {
            this.receiveTimeout = receiveTimeout;
        }

        public Duration getReplyTimeout() {
            return this.replyTimeout;
        }

        public void setReplyTimeout(Duration replyTimeout) {
            this.replyTimeout = replyTimeout;
        }

        public String getExchange() {
            return this.exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return this.routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getDefaultReceiveQueue() {
            return this.defaultReceiveQueue;
        }

        public void setDefaultReceiveQueue(String defaultReceiveQueue) {
            this.defaultReceiveQueue = defaultReceiveQueue;
        }
    }

    public static class DirectContainer extends RabbitProperties.AmqpContainer {
        private Integer consumersPerQueue;
        private boolean missingQueuesFatal = false;

        public DirectContainer() {
        }

        public Integer getConsumersPerQueue() {
            return this.consumersPerQueue;
        }

        public void setConsumersPerQueue(Integer consumersPerQueue) {
            this.consumersPerQueue = consumersPerQueue;
        }

        public boolean isMissingQueuesFatal() {
            return this.missingQueuesFatal;
        }

        public void setMissingQueuesFatal(boolean missingQueuesFatal) {
            this.missingQueuesFatal = missingQueuesFatal;
        }
    }

    public static class SimpleContainer extends RabbitProperties.AmqpContainer {
        private Integer concurrency;
        private Integer maxConcurrency;
        private Integer batchSize;
        private boolean missingQueuesFatal = true;

        public SimpleContainer() {
        }

        public Integer getConcurrency() {
            return this.concurrency;
        }

        public void setConcurrency(Integer concurrency) {
            this.concurrency = concurrency;
        }

        public Integer getMaxConcurrency() {
            return this.maxConcurrency;
        }

        public void setMaxConcurrency(Integer maxConcurrency) {
            this.maxConcurrency = maxConcurrency;
        }

        public Integer getBatchSize() {
            return this.batchSize;
        }

        public void setBatchSize(Integer batchSize) {
            this.batchSize = batchSize;
        }

        public boolean isMissingQueuesFatal() {
            return this.missingQueuesFatal;
        }

        public void setMissingQueuesFatal(boolean missingQueuesFatal) {
            this.missingQueuesFatal = missingQueuesFatal;
        }
    }

    public abstract static class AmqpContainer {
        private boolean autoStartup = true;
        private AcknowledgeMode acknowledgeMode;
        private Integer prefetch;
        private Boolean defaultRequeueRejected;
        private Duration idleEventInterval;
        private final RabbitProperties.ListenerRetry retry = new RabbitProperties.ListenerRetry();

        public AmqpContainer() {
        }

        public boolean isAutoStartup() {
            return this.autoStartup;
        }

        public void setAutoStartup(boolean autoStartup) {
            this.autoStartup = autoStartup;
        }

        public AcknowledgeMode getAcknowledgeMode() {
            return this.acknowledgeMode;
        }

        public void setAcknowledgeMode(AcknowledgeMode acknowledgeMode) {
            this.acknowledgeMode = acknowledgeMode;
        }

        public Integer getPrefetch() {
            return this.prefetch;
        }

        public void setPrefetch(Integer prefetch) {
            this.prefetch = prefetch;
        }

        public Boolean getDefaultRequeueRejected() {
            return this.defaultRequeueRejected;
        }

        public void setDefaultRequeueRejected(Boolean defaultRequeueRejected) {
            this.defaultRequeueRejected = defaultRequeueRejected;
        }

        public Duration getIdleEventInterval() {
            return this.idleEventInterval;
        }

        public void setIdleEventInterval(Duration idleEventInterval) {
            this.idleEventInterval = idleEventInterval;
        }

        public abstract boolean isMissingQueuesFatal();

        public RabbitProperties.ListenerRetry getRetry() {
            return this.retry;
        }
    }

    public static class Listener {
        private RabbitProperties.ContainerType type;
        private final RabbitProperties.SimpleContainer simple;
        private final RabbitProperties.DirectContainer direct;

        public Listener() {
            this.type = RabbitProperties.ContainerType.SIMPLE;
            this.simple = new RabbitProperties.SimpleContainer();
            this.direct = new RabbitProperties.DirectContainer();
        }

        public RabbitProperties.ContainerType getType() {
            return this.type;
        }

        public void setType(RabbitProperties.ContainerType containerType) {
            this.type = containerType;
        }

        public RabbitProperties.SimpleContainer getSimple() {
            return this.simple;
        }

        public RabbitProperties.DirectContainer getDirect() {
            return this.direct;
        }
    }

    public static enum ContainerType {
        SIMPLE,
        DIRECT;

        private ContainerType() {
        }
    }

    public static class Cache {
        private final RabbitProperties.Cache.Channel channel = new RabbitProperties.Cache.Channel();
        private final RabbitProperties.Cache.Connection connection = new RabbitProperties.Cache.Connection();

        public Cache() {
        }

        public RabbitProperties.Cache.Channel getChannel() {
            return this.channel;
        }

        public RabbitProperties.Cache.Connection getConnection() {
            return this.connection;
        }

        public static class Connection {
            private CacheMode mode;
            private Integer size;

            public Connection() {
                this.mode = CacheMode.CHANNEL;
            }

            public CacheMode getMode() {
                return this.mode;
            }

            public void setMode(CacheMode mode) {
                this.mode = mode;
            }

            public Integer getSize() {
                return this.size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }
        }

        public static class Channel {
            private Integer size;
            private Duration checkoutTimeout;

            public Channel() {
            }

            public Integer getSize() {
                return this.size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public Duration getCheckoutTimeout() {
                return this.checkoutTimeout;
            }

            public void setCheckoutTimeout(Duration checkoutTimeout) {
                this.checkoutTimeout = checkoutTimeout;
            }
        }
    }

    public class Ssl {
        private Boolean enabled;
        private String keyStore;
        private String keyStoreType = "PKCS12";
        private String keyStorePassword;
        private String trustStore;
        private String trustStoreType = "JKS";
        private String trustStorePassword;
        private String algorithm;
        private boolean validateServerCertificate = true;
        private boolean verifyHostname = true;

        public Ssl() {
        }

        public Boolean getEnabled() {
            return this.enabled;
        }

        public boolean determineEnabled() {
            boolean defaultEnabled = (Boolean)Optional.ofNullable(this.getEnabled()).orElse(false);
            if (CollectionUtils.isEmpty(parsedAddresses)) {
                return defaultEnabled;
            } else {
                Address address = (Address)parsedAddresses.get(0);
                return address.determineSslEnabled(defaultEnabled);
            }
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getKeyStore() {
            return this.keyStore;
        }

        public void setKeyStore(String keyStore) {
            this.keyStore = keyStore;
        }

        public String getKeyStoreType() {
            return this.keyStoreType;
        }

        public void setKeyStoreType(String keyStoreType) {
            this.keyStoreType = keyStoreType;
        }

        public String getKeyStorePassword() {
            return this.keyStorePassword;
        }

        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }

        public String getTrustStore() {
            return this.trustStore;
        }

        public void setTrustStore(String trustStore) {
            this.trustStore = trustStore;
        }

        public String getTrustStoreType() {
            return this.trustStoreType;
        }

        public void setTrustStoreType(String trustStoreType) {
            this.trustStoreType = trustStoreType;
        }

        public String getTrustStorePassword() {
            return this.trustStorePassword;
        }

        public void setTrustStorePassword(String trustStorePassword) {
            this.trustStorePassword = trustStorePassword;
        }

        public String getAlgorithm() {
            return this.algorithm;
        }

        public void setAlgorithm(String sslAlgorithm) {
            this.algorithm = sslAlgorithm;
        }

        public boolean isValidateServerCertificate() {
            return this.validateServerCertificate;
        }

        public void setValidateServerCertificate(boolean validateServerCertificate) {
            this.validateServerCertificate = validateServerCertificate;
        }

        public boolean getVerifyHostname() {
            return this.verifyHostname;
        }

        public void setVerifyHostname(boolean verifyHostname) {
            this.verifyHostname = verifyHostname;
        }
    }
}
