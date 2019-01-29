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
        /*String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null){
            httpServletResponse.sendError();
            return
        }
        if(registerService.checkAccount(account)){

        }else {

        }*/
        return "";


    }

    @GetMapping("/account")
    public List<Account> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/user/register")
    public Result<String> register(@RequestBody Account account){
        Result<String> result = new Result<>();
        AccountValidator accountValidator = new AccountValidator();
        String userName = account.getEmailAddress();
        String password = account.getPwdString();
        if(accountValidator.validate(userName)) {
            if (userRepository.findByEmailAddress(userName) == null) {
                PasswordValidator passwordValidator = new PasswordValidator();
                if (passwordValidator.validate(password)) {
                    Account user = new Account();
                    user.setEmailAddress(userName);
                    user.setPwdString(password);
                    registerService.registerAccount(user);
                    result.setMessage("register successful");
                    result.setStatusCode(200);
                    return result;
                } else {
                    result.setMessage("Error: Password not strong enough ");
                    result.setStatusCode(601);
                    return result;
                }
            } else {
                result.setMessage("Error: This user already existed");
                result.setStatusCode(602);
                return result;
            }
        }
        else{
            result.setMessage("Error: User name isn't email addresss");
            result.setStatusCode(603);
            return result;
        }

    }
}
