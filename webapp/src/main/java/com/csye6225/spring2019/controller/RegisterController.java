package com.csye6225.spring2019.controller;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.filter.AccountValidator;
import com.csye6225.spring2019.filter.PasswordValidator;
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


    @GetMapping("/")

    public String getUser(@RequestParam("email") String email, @RequestParam("password") String password){
        //I will rewrite whole this part;
        Account account = userRepository.queryAccountByInfo(email, password);
        if(account == null){
           return "The user doesn't exsit or pwdString is wrong";
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
    public String register(String userName, String password){
        AccountValidator accountValidator = new AccountValidator();
        if(accountValidator.validate(userName)) {
            if (userRepository.findByEmailAddress(userName) == null) {
                PasswordValidator passwordValidator = new PasswordValidator();
                if (passwordValidator.validate(password)) {
                    Account user = new Account();
                    user.setEmailAddress(userName);
                    registerService.registerAccount(user);
                    userRepository.insertAccount(user);
                    return "SignUp Successful!";
                } else {
                    return "Password doesn't strong enough";
                }
            } else {
                return "error: This user already existed";
            }
        }
        else{
            return "userName illegal";
        }

    }
}
