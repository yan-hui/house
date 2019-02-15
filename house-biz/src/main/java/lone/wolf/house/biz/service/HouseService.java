package lone.wolf.house.biz.service;

/**
 * @description:
 * @author: hch
 * @create: 2019/2/15$ 22:25$
 * @version: v1.0
 */

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lone.wolf.house.biz.mapper.HouseMapper;
import lone.wolf.house.common.model.Community;
import lone.wolf.house.common.model.House;
import lone.wolf.house.common.page.PageData;
import lone.wolf.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    @Value("${file.prefix}")
    private String imgPrefix;

    @Autowired
    private HouseMapper houseMapper;

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

    private List<House> queryAndSetImg(House query, PageParams pageParams) {
        List<House> houses = houseMapper.selectPageHouse(query, pageParams);
        houses.forEach(h -> {
            h.setFirstImg(imgPrefix + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getImageList().stream().map(pic -> imgPrefix + pic).collect(Collectors.toList()));
        });
        return null;
    }
}
