package lone.wolf.house.web.interceptor;

import lone.wolf.house.common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/8/2 14:34
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user = UserContext.getUser();
        if (null==user){
            String msg = URLEncoder.encode("请先登录","UTF-8");
            String target = URLEncoder.encode(request.getRequestURL().toString(),"UTF-8");
            if ("GET".equalsIgnoreCase(request.getMethod())){
                response.sendRedirect("/accounts/signin?errorMsg="+msg+"&target="+target);
            }else {
                response.sendRedirect("/accounts/signin?errorMsg="+msg);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
