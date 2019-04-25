package com.tang.interceptor;


import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域处理器
 */

    public class CrosFilter  implements HandlerInterceptor {
    //静态的声明
    private static CrosFilter crosFilter;

    //使用私有的构造方法 构造单例模式
    private CrosFilter() {
    }

    //唯一的静态方法 用于获取单一实例
    public static CrosFilter getInstance() {
        //采用双锁机制，安全并且能在多线程的环境下保持高性能
        if (crosFilter == null) {
            synchronized (CrosFilter.class) {
                if (crosFilter == null) {
                    crosFilter = new CrosFilter();
                }
            }
        }
        return crosFilter;
    }

    /**
     * 该方法需要preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
            throws Exception {

    }

    /**
     * 这个方法在preHandle方法返回值为true的时候才会执行。
     * 执行时间是在处理器进行处理之 后，也就是在Controller的方法调用之后执行。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mv) throws Exception {

    }

    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
     * 当preHandle的返回值为false的时候整个请求就结束了。
     * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//request.getHeader("Origin")
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "0");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("OPTIONS returns OK");
            return false;
        }
        return true;

    }
}