package com.yulintu.thematic.data.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "SecurityGroup")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Group {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "Name")
    private String name;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<User> users;

}
