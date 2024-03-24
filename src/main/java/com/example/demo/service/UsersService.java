package com.example.demo.service;

import com.example.demo.bean.RequestObject;
import com.example.demo.bean.UsersBean;
import com.example.demo.entity.Users;
import com.example.demo.statusCode.StatusCode;
import com.example.demo.exception.ExceptionResolver;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Encode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public List<Users> getAllusers() {
        return userRepository.findAll();
    }

    public String getToken(String username,String password){
        Users user =  userRepository.findByUsernameAndPassword
                (username,password);
        if(null != user && null != user.getToken()) {
            return user.getToken();
        }else {
            return "not found";
        }
    }

    public StatusCode authenticateToken(String token,String gameId){
        String keyValue="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsInByb3BYIjoiMXQydmtzNzhoMHBpaTFqNHMzdGFwIn0.eyJpc3MiOiJEaW5vQ2hpZXNhLmdpdGh1Yi5pbyIsInN1YiI6ImFsbWEiLCJhdWQiOiJhbnRvbmlvIiwiaWF0IjoxNzExMzAwOTAxLCJleHAiOjE3MTEzMDE1MDF9.jX3WUq2o1qS65trKOznvaFc5ILdlTEgZsIfnChtImUIluizomAzFFTnXaZBva0WeQ0XkxG4uGArwm4Tuo-N3lF2emRDk4r7CxSX-hg45uZ4GhbJ1Z_aT9iuno-gdDCY5oc9QK7PK8WuNf8dy4KAaO0X2r62pM3lSAFAFjewKtiYW-KgrtgELWMtmCq9rVMJyQre9e9-cwp5RKigghL5-2HCVYJ-rG6CYbqtWP8TPC-gHfbSmNtEBqD7Ms08W1yTnyj_udmWi_VX1JSzU6M0re3xKwkuhqeujkhXyk2a7LofYDy4G9paxMs5m1jMrsR28NWaULEEX94NXxr-DDtKl4A";
        Users tokenResult = userRepository.findByToken(token);
        StatusCode status = new StatusCode();
        Encode encode = new Encode();
        try {
            if (tokenResult.getToken().equalsIgnoreCase(token)) {
                HttpHeaders acceptHeaders = new HttpHeaders();
                acceptHeaders.set(HttpHeaders.ACCEPT,APPLICATION_JSON_VALUE);
                acceptHeaders.setContentType(APPLICATION_JSON);
                acceptHeaders.setBasicAuth("apiuser","apipassword");

                RequestObject requestObject = new RequestObject();
                UsersBean usersBean = new UsersBean();
                usersBean.setToken(token);
                usersBean.setGameid(gameId);
                requestObject.setSignature(encode.encode(token + "|" +gameId));
                requestObject.setParams(usersBean);
                HttpEntity<RequestObject> entity = new HttpEntity<>(requestObject,acceptHeaders);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                String url = "http://ec2-13-40-193-201.eu-west-2.compute.amazonaws.com:8081/provider1/gamelaunch";
                ResponseEntity<String> response = restTemplate.postForEntity(url,entity,String.class);
                if(null != response && response.hasBody() && response.getStatusCode().value()==200){
                    status.setCode(response.getStatusCode().toString());
                    status.setMessage(response.getBody().toString());
                }
            } else {
                status.setCode("401");
                status.setMessage("Unauthorized");
            }
        }catch(HttpClientErrorException e){
            throw e;
        }catch(Exception e){
            throw new ExceptionResolver();
        }
        return status;
    }

    public StatusCode provider2(String token, String gameId){
        StatusCode status = new StatusCode();
        try {

            ResponseEntity<String> response = null;
            HttpHeaders acceptHeaders = new HttpHeaders();
            acceptHeaders.set(HttpHeaders.ACCEPT,APPLICATION_JSON_VALUE);
            acceptHeaders.setContentType(APPLICATION_JSON);
            acceptHeaders.setBasicAuth("apiuser","apipassword");
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            RequestObject requestObject = new RequestObject();
            UsersBean usersBean = new UsersBean();
            usersBean.setToken(token);
            usersBean.setGameid(gameId);
            requestObject.setParams(usersBean);

            HttpEntity<RequestObject> entity = new HttpEntity<>(requestObject,acceptHeaders);
            String url = "http://ec2-13-40-193-201.eu-west-2.compute.amazonaws.com:8081/provider2/gamelaunch";
            response  = restTemplate.postForEntity(url,entity,String.class);
            if(null != response && response.hasBody() && response.getStatusCode().value()==200){
                status.setCode(response.getStatusCode().toString());
                status.setMessage(response.getBody().toString());
            }
        }catch(HttpClientErrorException e){
            throw e;
        }catch(Exception e){
            throw new ExceptionResolver();
        }
        return status;
    }


}
