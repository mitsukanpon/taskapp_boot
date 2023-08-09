package com.example.springsecurity5.model;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.Builder
public class ResponseMessage {
    private String code;
    private String message;
}
