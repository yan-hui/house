package lone.wolf.house.web.controller;

import lone.wolf.house.biz.service.AgencyService;
import lone.wolf.house.biz.service.CityService;
import lone.wolf.house.biz.service.HouseService;
import lone.wolf.house.biz.service.RecommandService;
import lone.wolf.house.common.constants.CommonConstants;
import lone.wolf.house.common.model.*;
import lone.wolf.house.common.page.PageData;
import lone.wolf.house.common.page.PageParams;
import lone.wolf.house.web.interceptor.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @description:
 * @author: hch
 * @create: 2019/2/15$ 21:56$
 * @version: v1.0
 */
@Controller
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private CityService cityService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private RecommandService recommandService;
    /**
     * 1、分页
     * 2、小区搜索、类型搜索
     * 3、排序
     * 4、展示图片、价格、标题、地址等信息
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
        PageData<House> houseData = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
        List<House> hotHouses = recommandService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouses);
        modelMap.put("ps", houseData);
        modelMap.put("vo", query);
        return "house/listing";
    }

    /**
     * 前往添加房产页面
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAdd")
    public String toAdd(ModelMap modelMap) {
        modelMap.put("citys", cityService.getAllCities());
        modelMap.put("communitys", houseService.getAllCommunitys());
        return "house/add";
    }

    /**
     * 添加房产
     *
     * @param house
     * @return
     */
    @RequestMapping(value = "/add")
    public String doAdd(House house) {
        User user = UserContext.getUser();
        house.setState(CommonConstants.HOUSE_STATE_UP);
        houseService.addHouse(house, user);
        return "redirect:/house/ownlist";
    }

    /**
     * 个人用户房产列表
     *
     * @param house
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/ownlist")
    public String ownlist(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookMarked(false);
        modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
        modelMap.put("pageType", "owm");
        return "house/ownlist";
    }

    /**
     * 房屋详情
     *
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail")
    public String houseDetail(Long id, ModelMap modelMap) {

        House house = houseService.queryOneHouse(id);
        HouseUser houseUser=houseService.getHouseUser(id);
        //点击增加点击数
        recommandService.increase(id);

        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
            modelMap.put("agent", agencyService.getAgentDetail(houseUser.getUserId()));
        }

        List<House> hotHouses = recommandService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouses);
        modelMap.put("house", house);
        return "house/detail";

    }
    /**
     * 用户留言信息
     *
     * @param userMsg
     * @return
     */
    @RequestMapping(value = "/agentMsg")
    public String houseMsg(UserMsg userMsg) {
        houseService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId();
    }

}
