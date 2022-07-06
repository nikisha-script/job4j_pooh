package ru.job4j.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SimpleHttpApi {

    GET_METHOD("GET"),
    POST_METHOD("POST"),
    RESPONSE_STATUS_200("200"),
    RESPONSE_STATUS_500("500"),
    REQUEST_STATUS_400("400"),
    ERROR("ERROR");

    private final String api;

}
