package com.yulintu.thematic.example.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "SecurityObject")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "Name")
    private String name;

    @Column(name = "[Group]")
    private String groupID;

    @Column(name = "Properties")
    private String properties;

    @Column(name = "CreateTime")
    private Date createTime;

    public User() {
        id = UUID.randomUUID().toString();
        groupID = "028E17D4-586E-46A9-8345-12EB3C56DD02";
        createTime = new Date();
    }

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "Group", insertable = false, updatable = false)
//    private Group group;
}
