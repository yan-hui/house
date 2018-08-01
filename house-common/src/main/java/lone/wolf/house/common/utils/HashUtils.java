package lone.wolf.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * @Description 加密类
 * @Author hechunhui
 * @CreatedBy 2018/7/31 11:53
 */
public class HashUtils {
    private static final HashFunction FUNCTION = Hashing.md5();

    private static final String SALT = "lone.wolf";//加盐

    /**
     * 对密码进行加盐md5操作
     *
     * @param password
     * @return
     */
    public static String encryPassword(String password) {
        HashCode hashCode = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
