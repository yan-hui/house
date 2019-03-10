package lone.wolf.house.biz.service;

/**
 * @description:
 * @author: hch
 * @create: 2019/2/15$ 22:25$
 * @version: v1.0
 */

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lone.wolf.house.biz.mapper.HouseMapper;
import lone.wolf.house.common.constants.HouseUserTypeEnum;
import lone.wolf.house.common.model.*;
import lone.wolf.house.common.page.PageData;
import lone.wolf.house.common.page.PageParams;
import lone.wolf.house.common.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HouseService {
    @Value("${file.prefix}")
    private String imgPrefix;

    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private MailService mailService;

    /**
     * 1、查询小区
     * 2、添加图片服务地址前缀
     * 3、构建分页结果
     *
     * @param query
     * @param pageParams
     * @return
     */
    public PageData<House> queryHouse(House query, PageParams pageParams) {
        List<House> houses = Lists.newArrayList();
        /**
         * 查询小区
         */
        if (!Strings.isNullOrEmpty(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());
            List<Community> communities = houseMapper.selectCommunity(community);
            if (!communities.isEmpty()) {
                query.setCommunityId(communities.get(0).getId());
            }
        }
        //添加图片服务地址前缀
        houses = queryAndSetImg(query, pageParams);
        Long pageCount = houseMapper.selectPageCount(query);
        return PageData.buildPage(houses, pageCount, pageParams.getPageSize(), pageParams.getPageNum());

    }

    public List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouse(query, pageParams);
        houses.forEach(h -> {
            h.setFirstImg(imgPrefix + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> (imgPrefix + img).replaceAll("/", "\\\\")).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(pic -> (imgPrefix + pic).replaceAll("/", "\\\\")).collect(Collectors.toList()));
        });
        return houses;
    }

    /**
     * 获取所有小区
     *
     * @return
     */
    public List<Community> getAllCommunitys() {
        Community community = new Community();
        return houseMapper.selectCommunity(community);

    }

    /**
     * 1、添加房产图片
     * 2、添加户型图
     * 3、插入房产信息
     * 4、绑定用户到房产的关系
     *
     * @param house
     * @param user
     * @return
     */
    public int addHouse(House house, User user) {
        if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
            String images = Joiner.on(",").join(fileService.getImgPath(house.getHouseFiles()));
            house.setImages(images);
        }
        if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
            String images = Joiner.on(",").join(fileService.getImgPath(house.getFloorPlanFiles()));
            house.setFloorPlan(images);
        }
        BeanHelper.onInsert(house);
        houseMapper.insert(house);
        bindUserToHouse(house.getId(), user.getId(), false);
        return 0;
    }

    /**
     * 绑定用户到房产的关系
     *
     * @param houseId
     * @param userId
     * @param isCollect
     *         是否收藏
     */
    private void bindUserToHouse(Long houseId, Long userId, boolean isCollect) {
        HouseUser existHouseUser = houseMapper.selectHouseUser(userId, houseId, isCollect ? HouseUserTypeEnum.BOOKMARK : HouseUserTypeEnum.SALE);
        if (existHouseUser != null) {
            return;
        }
        HouseUser houseUser = new HouseUser();
        houseUser.setHouseId(houseId);
        houseUser.setUserId(userId);
        houseUser.setType(isCollect ? HouseUserTypeEnum.BOOKMARK.value : HouseUserTypeEnum.SALE.value);
        BeanHelper.setDefaultProp(houseUser, HouseUser.class);
        BeanHelper.onInsert(houseUser);
        houseMapper.insertHouseUser(houseUser);
    }

    /**
     * 查询房屋
     *
     * @param id
     * @return
     */
    public House queryOneHouse(Long id) {
        House query = new House();
        query.setId(id);
        List<House> houses = queryAndSetImg(query, PageParams.build(1, 1));
        if (!houses.isEmpty()) {
            return houses.get(0);
        }
        return null;
    }

    /**
     * 添加留言信息
     *
     * @param userMsg
     */
    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        houseMapper.insertUserMsg(userMsg);
        User agent = agencyService.getAgentDetail(userMsg.getAgentId());
        mailService.sendMail("来自用户" + userMsg.getEmail() + "的留言", userMsg.getMsg(), agent.getEmail());


    }

    public HouseUser getHouseUser(Long houseId) {
        HouseUser houseUser = houseMapper.selectSaleHouseUser(houseId);
        return houseUser;
    }
}
