package lone.wolf.house.web.controller;

import lone.wolf.house.biz.service.UserService;
import lone.wolf.house.common.constants.CommonConstants;
import lone.wolf.house.common.model.User;
import lone.wolf.house.common.result.ResultMsg;
import lone.wolf.house.common.utils.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/26 16:47
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountRegister(User account, ModelMap modelMap) {
        if (null == account || null == account.getName()) {
            return "/user/accounts/register";
        }
        //用户验证
        ResultMsg resultMsg = UserHelper.validate(account);
        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email", account.getEmail());
            return "/user/accounts/registerSubmit";
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }

    }

    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败，确认激活链接是否过期！");
        }

    }

    @RequestMapping("accounts/signin")
    public String signin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        if (null == username || null == password) {
            request.setAttribute("target", target);
            return "/user/accounts/signin";
        }
        User user = userService.auth(username, password);
        if (null == user) {
            return "redirect:/accounts/signin?target=" + target + "&username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
//            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
            return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
        }
    }

    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";

    }


    //---------------------------个人信息------------------------

    /**
     * 1、可以提供页面信息
     * 2、更新用户信息
     *
     * @param request
     * @param updateUser
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest request, User updateUser, ModelMap modelMap) {
        if (null == updateUser.getEmail()) {
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser);
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> users = userService.getUserByQuery(query);
        request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }

    /**
     * 修改密码操作
     *
     * @param email
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email, String oldPassword, String newPassword, String confirmPassword, ModelMap modelMap) {
        User user = userService.auth(email, oldPassword);
        if (null == user) {
            return "redirect:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/accounts/profile?" + ResultMsg.errorMsg("两次输入密码不一致").asUrlParams();

        }
        User updateUser = new User();
        updateUser.setPasswd(HashUtils.encryPassword(newPassword));
        updateUser.setEmail(email);
        userService.updateUser(updateUser);
        return "redirect:/accounts/profile?" + ResultMsg.errorMsg("更新成功").asUrlParams();

    }

}
