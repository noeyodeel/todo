package com.sparta.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CommonResponse<T> {

    private Integer statusCode;
    private String msg;
    private T data;

    public CommonResponse(Integer statusCode, String msg, T data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public CommonResponse(Integer statusCode, String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }



}