package lone.wolf.house.mapper;

import lone.wolf.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author hechunhui
 * @CreatedBy 2018/7/26 16:37
 */
@Mapper
public interface UserMapper {
    public List<User> selectUsers();
}
