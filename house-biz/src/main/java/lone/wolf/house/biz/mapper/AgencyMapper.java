package lone.wolf.house.biz.mapper;

import lone.wolf.house.common.model.Agency;
import lone.wolf.house.common.model.User;
import lone.wolf.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: hch
 * @create: 2019/2/20 17:05
 * @version: v1.0
 */
@Mapper
public interface  AgencyMapper {
    /**
     * 经纪人列表
     * @param user
     * @param pageParams
     * @return
     */
    List<User> selectAgent(@Param("user") User user, @Param("pageParams")PageParams pageParams);

    /**
     * 经纪人
     * @param agency
     * @return
     */
    List<Agency> select(Agency agency);

    Long selectAgnetCount(@Param("user") User user);

}
