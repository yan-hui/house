package lone.wolf.house.biz.service;

import com.google.common.base.Objects;
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

import java.util.List;
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


    private final Cache<String, String> registerCache = CacheBuilder.newBuilder()
            .maximumSize(100)//设置Cache的最大容量数，当缓存数量达到或接近该最大值时，Cache将清除掉那些最近最少使用的缓存
            .expireAfterAccess(15, TimeUnit.MINUTES)//缓存项在创建后，在给定时间内没有被读/写访问，则清除
            .removalListener(new RemovalListener<String, String>() {//缓存移除的时候执行指定事件
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
        //SimpleMailMessage只能用来发送text格式的邮件
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
        String randomKey = RandomStringUtils.randomAlphabetic(10);//生成随机[a-z]字符串，包含大小写
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
