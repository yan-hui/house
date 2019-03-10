package lone.wolf.house.biz.service;

import lone.wolf.house.biz.mapper.AgencyMapper;
import lone.wolf.house.common.model.Agency;
import lone.wolf.house.common.model.User;
import lone.wolf.house.common.page.PageData;
import lone.wolf.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 经纪人相关业务
 * @author: hch
 * @create: 2019/2/20 16:47
 * @version: v1.0
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 访问user表获取详情
     * 添加用户头像
     *
     * @param userId
     * @return
     */
    public User getAgentDetail(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setType(2);
        List<User> users = agencyMapper.selectAgent(user, PageParams.build(1, 1));
        setImg(users);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    private void setImg(List<User> list) {
        list.forEach(i -> {
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }


    public List<Agency> getAllAgency() {
        return agencyMapper.select(new Agency());
    }

    /**
     * 经纪人列表
     *
     * @param pageParams
     * @return
     */
    public PageData<User> getAllAgent(PageParams pageParams) {
        List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
        setImg(agents);
        Long count = agencyMapper.selectAgnetCount(new User());

        return PageData.buildPage(agents, count, pageParams.getPageSize(), pageParams.getPageNum());
    }
}
