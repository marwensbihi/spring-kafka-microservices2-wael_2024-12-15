package com.angMetal.orders.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class Response {
    protected String timestamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String error;
    protected String exception;
    protected Map<?, ?> data;
}
