package com.angMetal.orders.payloads.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;



@Getter
public class UpdatePasswordDTO {
    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}
