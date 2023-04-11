package com.livk.autoconfigure.http.factory;

import com.livk.autoconfigure.http.annotation.Provider;
import com.livk.commons.util.AnnotationUtils;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * HttpFactory
 * </p>
 *
 * @author livk
 */
@Setter
public class HttpServiceRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<String> packages = AutoConfigurationPackages.get(beanFactory);
        ClassPathBeanDefinitionScanner scanner = getScanner(registry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpExchange.class));
        for (String basePackage : packages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                Class<?> clazz = ClassUtils.resolveClassName(Objects.requireNonNull(candidateComponent.getBeanClassName()), null);
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HttpFactoryBean.class);
                builder.addPropertyValue("httpInterfaceType", clazz.getName());
                builder.addPropertyValue("beanFactory", beanFactory);
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, clazz.getName());

                Provider provider = AnnotationUtils.findAnnotation(clazz, Provider.class);
                String beanName = Optional.ofNullable(provider)
                        .map(Provider::value)
                        .orElse(StringUtils.uncapitalize(clazz.getSimpleName()));
                BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, clazz.getName(), new String[]{beanName});
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
            }
        }
    }

    private ClassPathBeanDefinitionScanner getScanner(BeanDefinitionRegistry registry) {
        return new ClassPathBeanDefinitionScanner(registry, false, environment, resourceLoader) {
            @Override
            protected boolean isCandidateComponent(@NonNull AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation();
            }
        };
    }
}
