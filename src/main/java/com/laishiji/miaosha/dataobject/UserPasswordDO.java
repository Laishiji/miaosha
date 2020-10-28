package com.laishiji.miaosha.dataobject;

public class UserPasswordDO {
    private Integer id;

    private String encrytPasswaord;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncrytPasswaord() {
        return encrytPasswaord;
    }

    public void setEncrytPasswaord(String encrytPasswaord) {
        this.encrytPasswaord = encrytPasswaord == null ? null : encrytPasswaord.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}