package com.yulintu.thematic.web;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ResponseBodyData {
    private int status;
    private String message;
    private Object data;
}
