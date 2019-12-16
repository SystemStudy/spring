package com.Lirs.Spring.model;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
    private static final long serialVersionUID = -6309732882044872298L;

    private String id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String ip;
    private Date create_time;
    private String params;

    @Override
    public String toString() {
        return "SysLog{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", operation='" + operation + '\'' +
                ", time=" + time +
                ", method='" + method + '\'' +
                ", ip='" + ip + '\'' +
                ", create_time=" + create_time +
                ", params='" + params + '\'' +
                '}';
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
