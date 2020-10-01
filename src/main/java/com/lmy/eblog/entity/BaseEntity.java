package com.lmy.eblog.entity;
/**
 * @Project eblog
 * @Package com.lmy.eblog.entity
 * @author Administrator
 * @date 2020/9/30 10:45
 * @version V1.0
 */

import lombok.Data;

import java.util.Date;

/**
 * @author Lmy
 * @ClassName BaseEntity
 * @Description 实体公共部分
 * @date 2020/9/30 10:45
 **/
@Data
public class BaseEntity {

    private Long id;
    private Date created;
    private Date modified;

}
