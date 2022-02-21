package com.gmail.andersoninfonet.appsecurityclient.controller;

import java.security.Principal;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class HelloController {
	
	private final WebClient webClient;
	
    public HelloController(WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("/api/hello")
    public String hello(Principal principal) {
        return "Hello "+principal.getName();
    }
    
    @GetMapping("/api/users")
    public String[] getUsers(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client) {
    	return this.webClient
    					.get()
    					.uri("http://localhost:8087/resource/api/users")
    					.attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
    					.retrieve()
    					.bodyToMono(String[].class)
    					.block();
    }
}
