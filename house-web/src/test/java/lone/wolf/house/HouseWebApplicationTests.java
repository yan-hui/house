package lone.wolf.house;

import lone.wolf.house.biz.service.UserService;
import lone.wolf.house.common.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HouseWebApplicationTests {

    @Test
    public void contextLoads() {

    }
    @Autowired
    private UserService userService;

    @Test
    public void test(){
        User auth = userService.auth("hch_alone@163.com", "123456");
        assert auth != null;
        System.out.println(auth.getAboutme());
    }

}
