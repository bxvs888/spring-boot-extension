package com.livk.commons.http;

import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.commons.http.annotation.EnableHttpClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * HttpConfiguration
 * </p>
 *
 * @author livk
 */
@AutoConfiguration
@SpringAutoService(EnableHttpClient.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RestTemplateConfiguration {

    /**
     * Rest template rest template.
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    /**
     * The type Ok http client configuration.
     */
    @Configuration(enforceUniqueMethods = false)
    @ConditionalOnClass(OkHttpClient.class)
    public static class OkHttpClientConfiguration {
        /**
         * Rest template customizer rest template customizer.
         *
         * @return the rest template customizer
         */
        @Bean
        @ConditionalOnMissingBean(OkHttpClient.class)
        public RestTemplateCustomizer restTemplateCustomizer() {
            ConnectionPool pool = new ConnectionPool(200, 300, TimeUnit.SECONDS);
            OkHttpClient httpClient = new OkHttpClient().newBuilder()
                    .connectionPool(pool)
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();
            return restTemplate -> restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(httpClient));
        }

        /**
         * Rest template customizer rest template customizer.
         *
         * @param okHttpClient the ok http client
         * @return the rest template customizer
         */
        @Bean
        @ConditionalOnBean(OkHttpClient.class)
        public RestTemplateCustomizer restTemplateCustomizer(OkHttpClient okHttpClient) {
            return restTemplate -> restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(okHttpClient));
        }
    }
}