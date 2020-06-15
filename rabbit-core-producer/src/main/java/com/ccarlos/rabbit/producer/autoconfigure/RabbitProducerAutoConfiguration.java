package com.ccarlos.rabbit.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自动装配
 * @author: ccarlos
 * @date: 2020/6/15 20:39
 */
@Configuration
@ComponentScan({"com.ccarlos.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {


}
