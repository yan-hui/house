package lone.wolf.house.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 结果帮助类
 * @Author hechunhui
 * @CreatedBy 2018/7/31 10:42
 */
public class ResultMsg {
    private static final String errorMsgKey = "errorMsg";
    private static final String successMsgKey = "successMsg";
    private String errorMsg;
    private String successMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    /**
     * 验证是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return null == errorMsg;
    }

    /**
     * 异常信息
     *
     * @param msg
     * @return
     */
    public static ResultMsg errorMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;

    }

    /**
     * 成功信息
     *
     * @param msg
     * @return
     */
    public static ResultMsg successMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;

    }

    public Map<String, String> asMap() {
        Map<String, String> map = Maps.newHashMap();
        map.put("successMsgKey", successMsg);
        map.put("errorMsgKey", errorMsg);
        return map;
    }

    /**
     * 参数返回成url的形式
     *
     * @return
     */
    public  String asUrlParams() {
        Map<String, String> map = asMap();
        Map<String, String> newMap = Maps.newHashMap();
        map.forEach((key, value) -> {
            if (value != null) {
                try {
                    newMap.put(key, URLEncoder.encode(value, "UTf-8"));
                } catch (UnsupportedEncodingException e) {

                }
            }
        });
        //字符串拼接
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
