package lone.wolf.house.web.controller;

import lone.wolf.house.biz.service.RecommandService;
import lone.wolf.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @description: 主页
 * @author: hch
 * @create: 2019/3/10 20:39
 * @version: v1.0
 */
@Controller
public class HomePageController {
    @Autowired
    private RecommandService recommandService;

    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        List<House> houses=recommandService.getLastest();
        modelMap.put("recomHouses",houses);
        return "homepage/index";
    }
    @RequestMapping("")
    public String home(ModelMap modelMap){
        return "redirect:/index";
    }
}
