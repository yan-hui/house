package lone.wolf.house.controller;

import lone.wolf.house.common.model.User;
import lone.wolf.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/26 16:47
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("user")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }
}
