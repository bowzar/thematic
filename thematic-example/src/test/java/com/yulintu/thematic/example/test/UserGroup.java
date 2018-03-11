package com.yulintu.thematic.example.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserGroup {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "Name")
    private String name;

    @Column(name = "GroupName")
    private String groupName;
}
