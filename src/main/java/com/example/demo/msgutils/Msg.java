package com.example.demo.msgutils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

@ApiModel("返回信息对象")
public class Msg {
    @ApiModelProperty(value = "返回码",dataType = "int")
    private int status;
    @ApiModelProperty(value = "错误/正确信息",dataType = "String")
    private String msgContent;

//    private JSONObject data;

//    Msg(MsgCode msg, JSONObject data){
//        this.status = msg.getStatus();
//        this.msg = msg.getMsg();
////        this.data = data;
//    }

//    Msg(MsgCode msg, String extra, JSONObject data){
//        this.status = msg.getStatus();
//        this.msg = extra;
////        this.data = data;
//    }

    Msg(MsgCode msg){
        this.status = msg.getStatus();
        this.msgContent = msg.getMsg();
//        this.data = null;
    }

    Msg(MsgCode msg, String extra){
        this.status = msg.getStatus();
        this.msgContent = extra;
//        this.data = null;
    }

//    Msg(int status, String extra, JSONObject data){
//        this.status = status;
//        this.msg = extra;
////        this.data = data;
//    }

    Msg(int status, String extra){
        this.status = status;
        this.msgContent = extra;
//        this.data = null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msgContent;
    }

    public void setMsg(String msg) {
        this.msgContent = msg;
    }

//    public JSONObject getData() {
//        return data;
//    }

//    public void setData(JSONObject data) {
//        this.data = data;
//    }






}
