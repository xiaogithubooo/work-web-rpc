package com.work.easycommon.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户实体
 */
@Data
public class User implements Serializable {

    /**
     * 名字
     */
    private String name;

    @Serial
    private static final long serialVersionUID = 1L;

}
