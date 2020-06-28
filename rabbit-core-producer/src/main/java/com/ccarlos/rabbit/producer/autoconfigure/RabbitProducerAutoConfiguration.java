package com.ccarlos.rabbit.producer.autoconfigure;

import com.ccarlos.rabbit.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自动装配
 * @author: ccarlos
 * @date: 2020/6/15 20:39
 */
@EnableElasticJob
@Configuration
@ComponentScan({"com.ccarlos.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {


}
