package howard.cinema.core.dao.entity.acl;

import howard.cinema.core.dao.entity.common.AbstractInsertTimeEntity;

/**
 * @name: User
 * @description: 用户表（电脑端）
 * @author: huojiajin
 * @time: 2020/5/25 15:14
 */
public class User extends AbstractInsertTimeEntity {

    private String employeeNum;//工号
    private UserStatus status = UserStatus.NORMAL;// 状态
    private String roleId;// 用户所属角色Id
    private String name;// 用户名
    private String mobile;// 手机号
    private String loginName;// 登录名
    private String password;//密码

    public enum UserStatus {

        NORMAL("正常") {
        },
        INVALID("禁用") {
        },;

        private String value;

        private UserStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
