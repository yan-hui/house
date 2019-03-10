package lone.wolf.house.biz.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lone.wolf.house.common.constants.CommonConstants;
import lone.wolf.house.common.constants.RedisKeys;
import lone.wolf.house.common.model.House;
import lone.wolf.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: hch
 * @create: 2019/3/10 19:56
 * @version: v1.0
 */
@Service
public class RecommandService {
    @Autowired
    private HouseService houseService;

    /**
     * 点击增加的计算器
     *
     * @param id
     */
    public void increase(Long id) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.zincrby(RedisKeys.HOT_HOUSE_KEY, 1.0D, id + "");
        //只需要前10的，超过的删除
        jedis.zremrangeByRank(RedisKeys.HOT_HOUSE_KEY, 10, -1);
        jedis.close();
    }

    /**
     * 获取热门房产的id
     *
     * @return
     */
    private List<Long> getHot() {
        Jedis jedis = new Jedis("127.0.0.1");
        //从高到低
        Set<String> idSet = jedis.zrevrange(RedisKeys.HOT_HOUSE_KEY, 0, -1);
        jedis.close();
        List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
        return ids;
    }

    /**
     * 获取热门房产
     *
     * @param size
     * @return
     */
    public List<House> getHotHouse(Integer size) {
        House query = new House();
        List<Long> list = getHot();
        //处理排行和查的数据的顺序
        list = list.subList(0, Math.min(list.size(), size));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        query.setIds(list);

        final List<Long> orderList = list;
        List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
        Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
            return orderList.indexOf(hs.getId());
        });
        return houseSort.sortedCopy(houses);

    }

    public List<House> getLastest() {
        House query = new House();
        query.setSort(CommonConstants.TIME_DESC);
        List<House> houses = houseService.queryAndSetImg(query, PageParams.build(8, 1));
        return houses;

    }
}
