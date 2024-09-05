package com.phoenix.shortLink.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private String status;
    private String data;
    private String message;
}
