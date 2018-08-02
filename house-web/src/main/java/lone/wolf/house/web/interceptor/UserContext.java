package lone.wolf.house.web.interceptor;

import lone.wolf.house.common.model.User;

/**
 * @Description ThreadLocal操作类
 * @Author hechunhui
 * @CreatedBy 2018/8/2 14:22
 */
public class UserContext {
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void setUser(User user) {
        USER_HOLDER.set(user);
    }
    //每次请求用户都不一样，所以业务请求完要移除user
    public static void remove() {
        USER_HOLDER.remove();
    }

    public static User getUser() {
        return USER_HOLDER.get();
    }

}
