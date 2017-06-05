package com.bc.xx.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseController
 *
 * @author xiaobc
 * @date 17/6/4
 */
public class BaseController {

    protected Map<String,Object> resData;

    protected static final Integer RESPONSE_OK = 200;
    protected static final Integer RESPONSE_ERROR = 500;

    protected Map<String,Object> buildResponse(){
        resData = new HashMap<String,Object>();
        resData.put("code",200);
        resData.put("msg","请求成功");
        resData.put("data",null);
        return resData;
    }

    protected Map<String,Object> buildResponse(int code,String msg){
        resData = new HashMap<String,Object>();
        resData.put("code",code);
        resData.put("msg",msg);
        resData.put("data",null);
        return resData;
    }

    protected Map<String,Object> buildResponse(int code,Object data){
        resData = new HashMap<String,Object>();
        resData.put("code",code);
        resData.put("data",data);
        return resData;
    }

    protected Map<String,Object> buildResponse(int code,String msg,Object data){
        resData = new HashMap<String,Object>();
        resData.put("code",code);
        resData.put("msg",msg);
        resData.put("data",data);
        return resData;
    }


}
