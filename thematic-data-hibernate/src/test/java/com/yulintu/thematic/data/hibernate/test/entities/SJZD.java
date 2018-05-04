package com.yulintu.thematic.data.hibernate.test.entities;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "SJZD")
public class SJZD {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MC")
    private String mc;

    @Column(name = "BM")
    private String bm;

    @Column(name = "FZM")
    private String fzm;
}