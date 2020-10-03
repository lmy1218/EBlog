package com.lmy.eblog.dto;
/**
 * @Project eblog
 * @Package com.lmy.eblog.dto
 * @author Administrator
 * @date 2020/10/3 17:45
 * @version V1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Lmy
 * @ClassName ResultDto
 * @Description 统一返回结果
 * @date 2020/10/3 17:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto<T> implements Serializable {
    // 状态码
    private int status;
    // 信息
    private String msg;
    // 跳转目的地
    private String action;
    // 返回结果
    private T data;

    public ResultDto action(String action) {
        this.action = action;
        return this;
    }

    public ResultDto(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ResultDto ok() {
        return new ResultDto(200, "请求成功", null);
    }

    public static <T> ResultDto success(T data) {
        return ResultDto.success("请求成功", data);
    }

    /**
     * 请求成功
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultDto success(String msg, T data) {
        return new ResultDto(200, "请求成功", data);
    }

    /**
     * 请求失败
     * @param msg
     * @return
     */
    public static ResultDto fail(String msg) {
        return new ResultDto(-1, msg, null);
    }

}
