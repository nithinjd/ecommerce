package com.example.OrderService.External.decoder;

import com.example.OrderService.exception.CustomException;
import com.example.OrderService.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);
    @Override
    public Exception decode(String s, Response response) {
        log.info("Request URL : {}",response.request().url());
        log.info("Request header: {}",response.request().headers());

        try{
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getMessage(), errorResponse.getErrorCode(), response.status());
        }catch(IOException e){
            return  new CustomException("Internal server error","INTERNAL_SERVER_ERROR",500);
        }
    }
}
