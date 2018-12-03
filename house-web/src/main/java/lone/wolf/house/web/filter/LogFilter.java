package lone.wolf.house.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/26 16:03
 */
public class LogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.warn("=-=-=-=-=-=-=-=-=-=-=-=-="+req.getRequestURL()+"  request coming=-=-=-=-=-=-=-=-=-=-=-=-=");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
