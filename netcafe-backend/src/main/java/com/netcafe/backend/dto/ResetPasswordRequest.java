package com.netcafe.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequest {
    @NotBlank
    private String idCard;
    @NotBlank
    private String newPassword;

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
