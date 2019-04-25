package com.scloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class ThrowExceptionFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(ThrowExceptionFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("This is a pre filter, it will throw a RuntimeException!");
        try {
            doSomething();
        } catch (Exception e) {
            int statusCode = HttpStatus.BAD_GATEWAY.value();
            Throwable cause = e;
            String message = e.getMessage();
            throw new ZuulException((Throwable)cause, "Forwarding error", statusCode, message);
        }
        return null;
    }

    private void doSomething(){
        throw new RuntimeException("Exists some error!");
    }
}
