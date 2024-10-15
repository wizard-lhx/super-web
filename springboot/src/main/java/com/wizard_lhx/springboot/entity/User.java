package com.wizard_lhx.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：数据库接口
 * 作者：wizard-lhx
 * 日期：2024/10/15 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String user_name;
    private String password;
    private String name;
    private String phone;
    private String avatar;
}
