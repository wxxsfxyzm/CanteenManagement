package com.dyf.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * Web端的Admin管理Bean
 */
@Entity
@Data
public class AdministratorInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private String adminId;

    private String adminPassword;

}
