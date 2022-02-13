package com.gmail.andersoninfonet.appsecurityclient.dto;

public record UserRequest(
    String firstName, String lastName, 
    String email, String password, 
    String matchingPassword) {}
