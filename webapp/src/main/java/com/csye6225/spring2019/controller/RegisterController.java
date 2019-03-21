package com.csye6225.spring2019.controller;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.filter.AccountValidator;
import com.csye6225.spring2019.filter.PasswordValidator;
import com.csye6225.spring2019.filter.Verifier;
import com.csye6225.spring2019.repository.UserRepository;

import com.csye6225.spring2019.service.RegisterService;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Log4j2
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserRepository userRepository;
    private static final StatsDClient statsDClient = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);

    @GetMapping("/")
    public Result<String> getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException{
        statsDClient.incrementCounter("endpoint.homepage.http.get");
        log.info("Get Data");
        Result result = new Result();
        String auth = httpServletRequest.getHeader("Authorization");
        Account account = Verifier.isVerified(auth);
        if(account == null){
            log.warn("Lacking user identity");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);

            httpServletResponse.sendError(SC_UNAUTHORIZED,"I am not logging because User's information is wrong");
            return result;
        }
        if(registerService.checkAccount(account)){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now  = LocalDateTime.now();
            result.setMessage(dateTimeFormatter.format(now));
            result.setStatusCode(200);
            result.setData(dateTimeFormatter.format(now));
            return result;
        }else {
            log.warn("Checking account failed");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"I am not logging because User's information is wrong ");
            return result;
        }

    }

    @GetMapping("/account")
    public List<Account> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("user/register")
    public Result<String> register(@RequestBody Account account){
        statsDClient.incrementCounter("endpoint.register.http.post");
        log.info("Begin to register");
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
                    result.setData("Account is your emailAddress");
                    result.setMessage("register successful");
                    result.setStatusCode(200);
                    return result;
                } else {
                    log.warn("Invalid password");
                    result.setMessage("Error: Password not strong enough, you need use at least three pattern of [0-9,a-z,A-z,#$%^]");
                    result.setStatusCode(601);
                    return result;
                }
            } else {
                log.warn("user existed : " +account.getEmailAddress());
                result.setMessage("Error: This user already existed");
                result.setStatusCode(602);
                return result;
            }
        }
        else{
            log.warn("Invalid user name");
            result.setMessage("Error: User name isn't email addresss");
            result.setStatusCode(603);
            return result;
        }

    }
}
