package com.scloud.apigateway;

import com.scloud.apigateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

/**
 * @EnableZuulProxy注解开启Zuul的API网关功能
 */
@SpringBootApplication
@EnableZuulProxy
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

    /**
     * 让开发者通过正则表达式来自定义服务与路由映射的生成关系
     * servicePattern:用来匹配服务名称是否符合该自定义规则的正则表达式
     * routePattern:根据服务名中定义的内容转换出的路径表达式
     * 如果没有匹配上的服务规则，还是会采用完整服务名作为前缀的路径表达式
     * @return
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper(){
        return new PatternServiceRouteMapper(
                "(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}"
        );
    }

}
