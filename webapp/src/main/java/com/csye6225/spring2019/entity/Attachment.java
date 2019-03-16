package com.csye6225.spring2019.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="attachment")
public class Attachment {
    @Id
    @Column(name="id",columnDefinition = "varchar(36) NOT NULL")
    private String id;

    @Column(name="note_id",columnDefinition = "varchar(36) NOT NULL")
    private String noteId;

    @Column(name = "url",columnDefinition = "varchar(255) NOT NULL")
    private String url;

    @Column(name = "file_name",columnDefinition = "varchar(255) NOT NULL")
    private String fileName;

    @Column(name = "file_size",columnDefinition = "bigint(32) default 0")
    private double fileSize;

    @Column(name = "file_type",columnDefinition = "char(8) not null")
    private String fileType;

    @Column(name="create_time",columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    @Column(name = "update_time",columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updateTime;

}
