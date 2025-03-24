package com.payment.common;

import lombok.Getter;

@Getter
public class APIResponse<T> {
    private T data;

    public APIResponse(T data) {
        this.data = data;
    }   
}
