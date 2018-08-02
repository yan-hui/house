package lone.wolf.house.web.interceptor;

import com.google.common.base.Joiner;
import lone.wolf.house.common.constants.CommonConstants;
import lone.wolf.house.common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description 用户鉴权拦截器
 * @Author hechunhui
 * @CreatedBy 2018/8/2 14:14
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    //contorller方法执行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> paramMap = request.getParameterMap();
       //提示信息设置到request
        paramMap.forEach((key, value) -> {
            if ("errorMsg".equals(key) || "successMsg".equals(key) || "target".equals(key)) {
                request.setAttribute(key, Joiner.on(",").join(value));
            }
        });

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
            return true;
        }
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if (user != null) {
            UserContext.setUser(user);
        }
        return true;
    }

    //contorller方法执行之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //页面渲染之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        UserContext.remove();
    }
}
