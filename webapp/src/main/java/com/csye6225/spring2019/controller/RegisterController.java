package com.csye6225.spring2019.controller;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.repository.UserRepository;
import com.csye6225.spring2019.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{email}&{password}")
    public String getUser(@PathVariable("email") String email, @PathVariable("password") String password){
        Account account = userRepository.queryAccountByInfo(email, password);
        if(account == null){
           return "The user doesn't exsit or password is wrong";
        }
        else {
            String time = registerService.getTime();
            System.out.println(time);
            return time;
        }
    }

    @GetMapping("/account")
    public List<Account> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/user/register")
    public Account register(@RequestBody Account account){
        //registerService.registerAccount(account);
        userRepository.save(account);
        return account;
    }
}
