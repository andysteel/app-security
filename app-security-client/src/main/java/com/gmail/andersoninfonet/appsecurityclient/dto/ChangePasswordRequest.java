package com.gmail.andersoninfonet.appsecurityclient.dto;

public record ChangePasswordRequest(String email, String oldPassword, String newPassword) {}
