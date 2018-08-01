package lone.wolf.house.web.controller;

import lone.wolf.house.common.model.User;
import lone.wolf.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description 用户帮助类
 * @Author hechunhui
 * @CreatedBy 2018/7/31 10:50
 */
public class UserHelper {
    public static ResultMsg validate(User account) {
        if (StringUtils.isBlank(account.getEmail())) {
            return ResultMsg.errorMsg("Email 有误");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd()) || StringUtils.isBlank(account.getPasswd())) {
            return ResultMsg.errorMsg("密码不能为空");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd())) {
            return ResultMsg.errorMsg("请输入确认密码");
        }
        if (!account.getPasswd().equals(account.getConfirmPasswd())) {
            return ResultMsg.errorMsg("密码输入不一致");
        }
        if (account.getPasswd().length() < 6) {
            return ResultMsg.errorMsg("密码必须大于6位");
        }
        return ResultMsg.successMsg("");
    }
}
