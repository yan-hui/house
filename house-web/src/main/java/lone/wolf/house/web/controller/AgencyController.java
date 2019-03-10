package lone.wolf.house.web.controller;

import lone.wolf.house.biz.service.AgencyService;
import lone.wolf.house.biz.service.HouseService;
import lone.wolf.house.common.model.House;
import lone.wolf.house.common.model.User;
import lone.wolf.house.common.model.UserMsg;
import lone.wolf.house.common.page.PageData;
import lone.wolf.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 经纪人/经纪机构controller
 * @author: hch
 * @create: 2019/2/20 17:54
 * @version: v1.0
 */
@Controller
@RequestMapping("/agency")
public class AgencyController {
    @Autowired
    private AgencyService acencyService;
    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "agentList")
    public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
        PageData<User> ps = acencyService.getAllAgent(PageParams.build(pageSize, pageNum));
        modelMap.put("ps", ps);
        return "/user/agent/agentList";
    }

    @RequestMapping(value = "/agentDetail")
    public String agnetDetail(Long id, ModelMap modelMap) {
        User user = acencyService.getAgentDetail(id);
        House query = new House();
        query.setUserId(id);
        query.setBookMarked(false);
        PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3, 1));
        if (bindHouse != null) {
            modelMap.put("bindHouses", bindHouse.getList());
        }
        modelMap.put("agent", user);
        return "/user/agent/agentDetail";
    }

}
