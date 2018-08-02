package lone.wolf.house.biz.service;

import com.google.common.collect.Lists;
import lone.wolf.house.biz.mapper.UserMapper;
import lone.wolf.house.common.model.User;
import lone.wolf.house.common.utils.BeanHelper;
import lone.wolf.house.common.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 用户相关业务逻辑层
 * @Author hechunhui
 * @CreatedBy 2018/7/26 16:42
 */
@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;

    public List<User> getAllUsers() {
        return userMapper.selectUsers();
    }


    /**
     * 1、非激活、密码加盐MD5，保存头像到本地
     * 2、生成key,绑定email
     * 3、发邮件
     *
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()) {
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account, User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
//        userMapper.insert(account);
//        mailService.registerNotif(account.getEmail());
        return true;
    }

    /**
     * 激活邮件
     *
     * @param key
     * @return
     */
    public boolean enable(String key) {
        return mailService.enable(key);

    }

    /**
     * 用户名密码严重
     *
     * @param username
     * @param password
     * @return
     */
    public User auth(String username, String password) {
        User user = new User();
        user.setEmail(username);
        user.setPasswd(HashUtils.encryPassword(password));
        user.setEnable(1);
        List<User> list = getUserByQuery(user);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询用户信息
     *
     * @param user
     * @return
     */
    public List<User> getUserByQuery(User user) {
        List<User> list = userMapper.selectUsersByQuery(user);
        list.forEach(u -> {
            u.setAvatar(imgPrefix + u.getAvatar());
        });
        return list;
    }

    /**
     * 修改个人信息
     *
     * @param updateUser
     */
    public void updateUser(User updateUser) {
        BeanHelper.onUpdate(updateUser);
        userMapper.update(updateUser);
    }
}
