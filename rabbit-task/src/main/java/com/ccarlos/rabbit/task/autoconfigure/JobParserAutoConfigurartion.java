package com.ccarlos.rabbit.task.autoconfigure;

import com.ccarlos.rabbit.task.parser.ElasticJobConfParser;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 解析定时任务配置类
 * @author: ccarloselastic.job.zk
 * @date: 2020/6/22 22:47
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "elastic.job.zk",
        name = {"namespace", "serverLists"}, matchIfMissing = false)
@EnableConfigurationProperties(JobZookeeperProperties.class)
public class JobParserAutoConfigurartion {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(
            JobZookeeperProperties jobZookeeperProperties) {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration
                (jobZookeeperProperties.getServerLists(), jobZookeeperProperties.getNamespace());
        zkConfig.setBaseSleepTimeMilliseconds(zkConfig.getBaseSleepTimeMilliseconds());
        zkConfig.setMaxSleepTimeMilliseconds(zkConfig.getMaxSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(zkConfig.getConnectionTimeoutMilliseconds());
        zkConfig.setSessionTimeoutMilliseconds(zkConfig.getSessionTimeoutMilliseconds());
        zkConfig.setMaxRetries(zkConfig.getMaxRetries());
        zkConfig.setDigest(zkConfig.getDigest());
        log.info("初始化job注册中心配置成功, zkaddress : {}, namespace : {}",
                jobZookeeperProperties.getServerLists(), jobZookeeperProperties.getNamespace());
        return new ZookeeperRegistryCenter(zkConfig);
    }

    @Bean
    public ElasticJobConfParser elasticJobConfParser
            (JobZookeeperProperties jobZookeeperProperties,
             ZookeeperRegistryCenter zookeeperRegistryCenter) {
        return new ElasticJobConfParser(jobZookeeperProperties, zookeeperRegistryCenter);
    }

}
