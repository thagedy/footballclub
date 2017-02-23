package com.thagedy.footballclub.common.pojo;

/**
 * Created by thagedy on 2017/2/18.
 */
public class ClubResult {
    private Integer code;

    private String msg;

    private Object data;

    public ClubResult() {
    }

    public ClubResult(Object data) {
        this.code = 10000;
        this.msg = "Success";
        this.data = data;
    }

    public ClubResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * code 10000
     * msg "Success"
     *
     * @param data
     * @return
     */
    public static ClubResult ok(Object data) {
        return new ClubResult(data);
    }


    public static ClubResult ok() {
        return new ClubResult(10000, "Success", true);
    }

    /**
     * code 10001
     * data null
     *
     * @param msg
     * @return
     */
    public static ClubResult error(String msg) {
        return new ClubResult(10001, msg, false);
    }

    /**
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ClubResult build(Integer code, String msg, Object data) {
        return new ClubResult(code, msg, data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SynapseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
