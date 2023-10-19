package com.cyes.webserver.api.dto.common;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonResponse<T> {
    String message;
    T body;

}
