package com.ccarlos.rabbit.task.annotation;

import com.ccarlos.rabbit.task.autoconfigure.JobParserAutoConfigurartion;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(JobParserAutoConfigurartion.class)
public @interface EnableElasticJob {

}
