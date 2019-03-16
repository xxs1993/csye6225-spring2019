package com.csye6225.spring2019.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class Account {

    @Id
    @Column(name = "id",columnDefinition = " INT  AUTO_INCREMENT")
    private int id;

    @Column(name="email_address",columnDefinition = " VARCHAR(255)")
    private String emailAddress;

    @Column(name="pwd_string",columnDefinition = " VARCHAR(255)")
    private String pwdString;


}
