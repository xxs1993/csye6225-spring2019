package com.csye6225.spring2019.Controller;

import com.csye6225.spring2019.Entity.Account;
import com.csye6225.spring2019.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/")
    public String getAccount(){
        String time = registerService.getTime();
        System.out.println(time);
        return time;
    }

    @PostMapping("/user/register")
    public void register(@RequestBody Account account){
        registerService.registerAccount(account);
    }
}
