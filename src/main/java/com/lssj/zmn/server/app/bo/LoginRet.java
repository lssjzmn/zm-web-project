package com.lssj.zmn.server.app.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginRet implements Serializable {

    private Long id;
    private String status;
    private String info;
    private Map<String, Object> body = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
