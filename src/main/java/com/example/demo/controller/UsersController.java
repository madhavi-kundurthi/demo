package com.example.demo.controller;

import com.example.demo.bean.RequestObject;
import com.example.demo.bean.UsersBean;
import com.example.demo.entity.Users;
//import com.example.demo.service.TokenService;
import com.example.demo.service.UsersService;
import com.example.demo.statusCode.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

//    private final TokenService tokenService;
//
//    public UsersController(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }

    @Autowired
    UsersService usersService;

    @GetMapping("/getId")
    public List<Users> getAllUsers( ){
        return  usersService.getAllusers();
    }

    @PostMapping("/login")
    public String getToken(@RequestBody UsersBean users){
        return usersService.getToken( users.getUser(),users.getPassword());
    }

    @PostMapping("/provider1")
    public ResponseEntity<String> authenticateToken(@RequestBody UsersBean usersBean){

        StatusCode result = usersService.authenticateToken(usersBean.getToken(),usersBean.getGameid());
        return ResponseEntity.ok(result.getCode());

    }


    @PostMapping("/provider2")
    public ResponseEntity<String> provider2(@RequestBody RequestObject usersBean){

        StatusCode result = usersService.provider2(usersBean.getParams().getToken(),usersBean.getParams().getGameid());
        return ResponseEntity.ok(result.getCode());

    }

//    @PostMapping("/token")
//    public String token(Authentication authentication) {
//        String token = tokenService.generateToken(authentication);
//        return token;
//    }
}
