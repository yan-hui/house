package lone.wolf.house.web.controller;

import lone.wolf.house.biz.service.UserService;
import lone.wolf.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/26 17:53
 */
@Controller
public class HelloController {
    @Resource
    private UserService userService;

    @RequestMapping("hello")
    public String hello(ModelMap modelMap) {
        List<User> users = userService.getAllUsers();
        User user = users.get(0);
        modelMap.put("user", user);
        return "hellow";
    }

    @RequestMapping("index")
    public String index() {
        return "homepage/index";
    }
}
