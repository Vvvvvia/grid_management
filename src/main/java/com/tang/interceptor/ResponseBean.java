package com.tang.interceptor;




/**
 * @program: 测试
 * @description:返回的JSON数据结构标准
 **/
public class ResponseBean <T>{
    private boolean success;
    private T data;
    private String code;
    private String msg;


    public ResponseBean(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "success=" + success +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ResponseBean(boolean success, T data, String code, String msg) {
        super();
        this.success = success;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ResponseBean(boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
    public ResponseBean(boolean success,UnicomResponseEnums enums){
        this.success=success;
        this.code=enums.getCode();
        this.msg=enums.getMsg();
    }
    public ResponseBean(boolean success,T data,UnicomResponseEnums enums){
        this.success=success;
        this.data=data;
        this.code=enums.getCode();
        this.msg=enums.getMsg();
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}