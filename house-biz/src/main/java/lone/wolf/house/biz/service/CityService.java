package lone.wolf.house.biz.service;

import com.google.common.collect.Lists;
import lone.wolf.house.common.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 城市相关业务
 * @author: hch
 * @create: 2019/2/19 16:53
 * @version: v1.0
 */
@Service
public class CityService {
    /**
     * 获取所有城市
     * @return
     */
    public List<City> getAllCities() {
        City city = new City();
        city.setId(1);
        city.setCityCode("10010");
        city.setCityName("广州市");
        return Lists.newArrayList(city);
    }
}
