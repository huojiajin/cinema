package howard.cinema.manage.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @name: MyFilters
 * @description: 自定义过滤器
 * @author: huojiajin
 * @time: 2020/5/29 11:31
 */
@WebFilter(filterName = "MyFilter", urlPatterns = "/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //解决跨域问题
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("multipart/form-data")) {
            chain.doFilter(request, response);
        }else {
            ServletRequest requestWrapper = null;
            if (request instanceof HttpServletRequest) {
                requestWrapper = new MyHttpServletRequestWrapper((HttpServletRequest) request);
            }

            if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
                response.getWriter().println("ok");
                return;
            }
            if (requestWrapper == null) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }

}