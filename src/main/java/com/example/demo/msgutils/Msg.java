package com.example.demo.msgutils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

@ApiModel("返回信息对象")
public class Msg<T> {
    @ApiModelProperty(value = "返回码",dataType = "int")
    private int status;
    @ApiModelProperty(value = "错误/正确信息",dataType = "String")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public Msg(MsgCode msg, T data){
        this.status = msg.getStatus();
        this.msg = msg.getMsg();
        this.data = data;
    }

    public Msg(MsgCode msg, String extra, T data){
        this.status = msg.getStatus();
        this.msg = extra;
        this.data = data;
    }

    public Msg(MsgCode msg){
        this.status = msg.getStatus();
        this.msg = msg.getMsg();
        this.data = null;
    }

    public Msg(MsgCode msg, String extra){
        this.status = msg.getStatus();
        this.msg = extra;
        this.data = null;
    }

    public Msg(int status, String extra, T data){
        this.status = status;
        this.msg = extra;
        this.data = data;
    }

    public Msg(int status, String extra){
        this.status = status;
        this.msg = extra;
        this.data = null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
