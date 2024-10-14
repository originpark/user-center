package com.origin.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDeleteRequest implements Serializable {
    private Long id;
}
