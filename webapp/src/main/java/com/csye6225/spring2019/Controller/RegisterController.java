package com.csye6225.spring2019.Controller;

import com.csye6225.spring2019.Entity.Account;
import com.csye6225.spring2019.Repository.UserRepository;
import com.csye6225.spring2019.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String getUser(@RequestParam("email") String email, @RequestParam("password") String password){
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
    public String register(String userName, String password){
        if(userRepository.findByEmailAddress(userName) == null){
            Account user = new Account();
            user.setEmailAddress(userName);
            //user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
            userRepository.save(user);
            return "SignUp Successful!";
        }
        else {
            return "error: This user already existed";
        }
    }
}
