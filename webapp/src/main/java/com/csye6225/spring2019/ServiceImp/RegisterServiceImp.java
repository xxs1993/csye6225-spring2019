package com.csye6225.spring2019.ServiceImp;

import com.csye6225.spring2019.Entity.Account;
import com.csye6225.spring2019.Service.RegisterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RegisterServiceImp implements RegisterService {

    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
        LocalDateTime now  = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public void registerAccount(Account account){
        //TODO
    }

}
