package com.insuranceManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class AuthService {

    @Value("${idms.api.base-url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    private String authToken;

    public String fetchAuthToken() {
        String url = baseUrl + "/api/authenticate/GetUserAuthorizationToken";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("username", "testerAPI@drivesoft.tech")
                .queryParam("password", "HeoVIaCST3st@@Main")
                .queryParam("InstitutionID", "107007");

        ResponseEntity<Map> response = restTemplate.getForEntity(builder.toUriString(), Map.class);
        return (String) response.getBody().get("Token");
    }


}