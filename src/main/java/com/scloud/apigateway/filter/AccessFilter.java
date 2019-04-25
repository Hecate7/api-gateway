package com.scloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 过滤器的类型，决定过滤器在请求的哪个生命周期中执行
     * pre/routing/post/error
     * 路由之前/路由请求转发阶段/routing和error过滤器之后被调用/发生错误时被调用
     * pre:进行请求路由之前，前置加工
     * routing：将外部请求转发到具体服务实例，当请求结果返回之后,routing阶段结束
     * post:不仅可以获取到请求信息，还可以获取到服务实例的返回信息，可以对处理结果进行一些加工或转换
     * error:上述三个阶段发生异常时触发，最终流向post
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序，当请求在一个阶段存在多个过滤器时，需要根据该方法返回的值来依次执行
     * 数值越小，优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断该过滤器是否需要被执行，可利用该函数来指定过滤器的有效范围
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * 确定是否拦截当前的请求或是在请求路由返回结果之后对处理结果进行加工
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();


        logger.info("Send {} request to {}",request.getMethod(), request.getRequestURL().toString());

        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null){
            logger.warn("accessToken is null");
            /**
             * Zuul过滤请求
             */
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        logger.info("request:{}",ctx.getRequestQueryParams());
        logger.info("accessToken:{}",accessToken);
        logger.info("accessToken OK!");
        return null;
    }
}
