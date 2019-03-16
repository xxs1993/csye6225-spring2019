package com.csye6225.spring2019.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="note")
@Entity
public class Note  {
    @Id
    @Column(name = "id",columnDefinition = "varchar(36) NOT NULL")
    private String id;

    @Column(name = "user_id",columnDefinition = "int(11) DEFAULT '0'")
    private int userId;

    @Column(name = "title",columnDefinition = "varchar(255) DEFAULT ''")
    private String title;
    @Column(name = "content",columnDefinition = "varchar(255) DEFAULT ''")
    private String content;
    @Column(name = "create_time",columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;
    @Column(name = "update_time",columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updateTime;

    @Transient
    private List<Attachment> attachments;

}
