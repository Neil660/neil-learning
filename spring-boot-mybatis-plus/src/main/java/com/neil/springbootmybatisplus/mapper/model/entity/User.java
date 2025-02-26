package com.neil.springbootmybatisplus.mapper.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/*
 * @Classname User
 * @Version information V1.0
 * @Date 2025/2/26
 * @Copyright notice iWhaleCloud
 * @userName 11508
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
