package lone.wolf.house.biz.service;

<<<<<<< HEAD
import com.google.common.base.Objects;
=======
>>>>>>> 7a71b07cfda71a465090609b5476fbe510fc3e53
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lone.wolf.house.biz.mapper.UserMapper;
import lone.wolf.house.common.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description 邮件发送业务类
 * @Author hechunhui
 * @CreatedBy 2018/7/31 16:18
 */
@Service
public class MailService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${domain.name}")
    private String domainName;


    private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {
        @Override
        public void onRemoval(RemovalNotification<String, String> notification) {
            String email = notification.getValue();
            User user = new User();
            user.setEmail(email);
            List<User> targetUser = userMapper.selectUsersByQuery(user);
            if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(), 0)) {
                userMapper.delete(notification.getValue());//在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
            }
        }
    }).build();

    @Async
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(title);
        message.setText(url);
        message.setTo(email);
        javaMailSender.send(message);
    }

    /**
     * 1、缓存key-email的关系
     * 2、借助spring mail 发送邮件
     * 3、借助异步框架进行异步操作
     * 和调用放不在同一类
     *
     * @param email
     */
    @Async
    public void registerNotif(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        sendMail("房产平台激活邮件", url, email);
    }

    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User updateUser = new User();
        updateUser.setEnable(1);
        updateUser.setEmail(email);
        userMapper.update(updateUser);
        registerCache.invalidate(key);
        return true;
    }
}
