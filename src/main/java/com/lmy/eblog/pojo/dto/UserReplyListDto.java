package com.lmy.eblog.pojo.dto;

import lombok.Data;

/**
 * @author Yang
 * @version 1.0.0
 * @className UserReplyListDto.java
 * @description TODO
 * @createTime 2021年03月09日 14:34:00
 */
@Data
public class UserReplyListDto {

    private Long userId;

    private Integer count;

    private String avatar;

    private String username;
}
