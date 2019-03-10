package lone.wolf.house.biz.mapper;

import lone.wolf.house.common.constants.HouseUserTypeEnum;
import lone.wolf.house.common.model.*;
import lone.wolf.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: hch
 * @create: 2019/2/15$ 22:35$
 * @version: v1.0
 */
@Mapper
public interface HouseMapper {
    /**
     * 列表分页
     *
     * @param house
     * @param pageParams
     * @return
     */
    List<House> selectPageHouse(@Param("house") House house, @Param("pageParams") PageParams pageParams);

    /**
     * 返回总数
     *
     * @param query
     * @return
     */
    Long selectPageCount(@Param("house") House query);


    /**
     * 查询小区
     *
     * @param community
     * @return
     */
    List<Community> selectCommunity(Community community);

    /**
     * 添加房产
     *
     * @param house
     * @return
     */
    int insert(House house);

    int insertHouseUser(HouseUser houseUser);

    HouseUser selectHouseUser(@Param("userId") Long userId, @Param("houseId") Long houseId, @Param("type") HouseUserTypeEnum houseUserTypeEnum);

    int insertUserMsg(UserMsg userMsg);

    HouseUser selectSaleHouseUser(@Param("id") Long houseId);
}
