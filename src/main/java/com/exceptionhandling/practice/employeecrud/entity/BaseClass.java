package com.exceptionhandling.practice.employeecrud.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BaseClass {
    private String createUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;
    private String lastUpdateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTimestamp;

}
