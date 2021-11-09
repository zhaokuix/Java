# spring boot data jpa 2.2.2 开启二级缓存

#### 介绍
spring boot data jpa 2.2.2 开启二级缓存指引


### 在application.properties中配置:
```properties
    #显示sql
    spring.jpa.properties.hibernate.show_sql=true
    
    #开启二级缓存
    spring.jpa.properties.hibernate.cache.use_second_level_cache=true
    spring.jpa.properties.hibernate.cache.use_query_cache=true

    #redisson hibernate 二级缓存,见:https://github.com/redisson/redisson/tree/master/redisson-hibernate
    spring.jpa.properties.hibernate.cache.region.factory_class=org.redisson.hibernate.RedissonRegionFactory
    #redisson配置文件路径
    spring.jpa.properties.hibernate.cache.redisson.config=./src/main/resources/redisson.json
    #缓存的最大大小。使用LRU算法驱逐Redis中的多余条目。
    spring.jpa.properties.hibernate.cache.redisson[User].eviction.max_entrie=2
    # 每个缓存条目在Redis中的存活时间。以毫秒为单位定义。
    spring.jpa.properties.hibernate.cache.redisson[User].expiration.time_to_live=30000
    # Redis中每个缓存条目的最大空闲时间。以毫秒为单位定义。
    spring.jpa.properties.hibernate.cache.redisson[User].expiration.max_idle_time=30000
```
### 必需的依赖
```xml
        <!--Redisson for Hibernate v5.3.x - v5.4.x -->
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson-hibernate-53</artifactId>
            <version>3.11.5</version>
        </dependency>
```
