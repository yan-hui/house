package lone.wolf.house.biz.mapper;

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
    public int delete(String value);

    public List<User> selectUsers();

    public int insert(User account);

    public int update(User updateUser);

    public List<User> selectUsersByQuery(User user);
}
