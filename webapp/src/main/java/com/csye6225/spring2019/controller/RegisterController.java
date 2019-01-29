package com.csye6225.spring2019.controller;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.filter.AccountValidator;
import com.csye6225.spring2019.filter.PasswordValidator;
import com.csye6225.spring2019.filter.Verifier;
import com.csye6225.spring2019.repository.UserRepository;

import com.csye6225.spring2019.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //I will rewrite whole this part;
        /*Account account = userRepository.queryAccountByInfo(email, password);
        if(account == null){
           return "The user doesn't exsit or password is wrong";
        }
        else {
            String time = registerService.getTime();
            System.out.println(time);
            return time;*/
        //String authorization = httpServletRequest.getHeader("Authorization");
       // Account account = Verifier.isVerified(authorization);
        //if(account == null){
           //httpServletResponse.setStatus(SC_UNAUTHORIZED);
            //httpServletResponse.sendError(SC_UNAUTHORIZED);
        //}
        return "";
    }

    @GetMapping("/account")
    public List<Account> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/user/register")
    public String register(@RequestBody Account account){
        AccountValidator accountValidator = new AccountValidator();
        String userName = account.getEmailAddress();
        String password = account.getPassword();
        if(accountValidator.validate(userName)) {
            if (userRepository.findByEmailAddress(userName) == null) {
                PasswordValidator passwordValidator = new PasswordValidator();
                if (passwordValidator.validate(password)) {
                    Account user = new Account();
                    user.setEmailAddress(userName);
                    user.setPassword(password);
                    registerService.registerAccount(user);
                    userRepository.save(user);
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
