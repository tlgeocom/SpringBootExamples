package com.demo.advice;

import com.demo.service.DemoService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <p> @Title DemoImportBeanDefinationRegistar
 * <p> @Description @Import注解的实现类
 *
 * @author ACGkaka
 * @date 2023/4/25 21:18
 */
public class DemoImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        // 定义Bean
        beanDefinition.setBeanClass(DemoService.class);

        // 注册Bean
        registry.registerBeanDefinition("demoService", beanDefinition);
    }
}
