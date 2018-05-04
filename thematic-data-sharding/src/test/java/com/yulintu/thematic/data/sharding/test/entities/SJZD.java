package com.yulintu.thematic.data.sharding.test.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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