package com.ericarias.quiz.Model;

/**
 * Objeto respuesta de peticiones HTTP
 */
public class RespHTTP {

    private boolean resp;
    private String desc;

    public RespHTTP(boolean resp, String desc) {
        this.resp = resp;
        this.desc = desc;
    }

    public boolean getResp() {
        return resp;
    }

    public void setResp(boolean resp) {
        this.resp = resp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
