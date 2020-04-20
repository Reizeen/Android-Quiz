package com.ericarias.quiz.Model;

/**
 * Objeto respuesta de peticiones HTTP
 */
public class RespHTTP {

    private String resp;
    private String desc;

    public RespHTTP(String resp, String desc) {
        this.resp = resp;
        this.desc = desc;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
