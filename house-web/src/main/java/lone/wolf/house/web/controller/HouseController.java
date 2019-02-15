package lone.wolf.house.web.controller;

import lone.wolf.house.biz.service.HouseService;
import lone.wolf.house.common.model.House;
import lone.wolf.house.common.page.PageData;
import lone.wolf.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
        modelMap.put("ps",houseData);
        modelMap.put("vo",query);
        return "house/listing";
    }
}
