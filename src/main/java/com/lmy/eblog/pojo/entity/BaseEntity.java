package com.lmy.eblog.pojo.entity;
/**
 * @Project eblog
 * @Package com.lmy.eblog.pojo.entity
 * @author Administrator
 * @date 2020/9/30 10:45
 * @version V1.0
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lmy
 * @ClassName BaseEntity
 * @Description 实体公共部分
 * @date 2020/9/30 10:45
 **/
@Data
public class BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Date created;
    private Date modified;

}
