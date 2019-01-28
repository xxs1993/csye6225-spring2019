package com.csye6225.spring2019.impl;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.repository.UserRepository;
import com.csye6225.spring2019.service.RegisterService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired

    private UserRepository userRepository;
    @Override
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
        LocalDateTime now  = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    @Override
    public boolean registerAccount(Account account){
        //TODO
        if(account == null || StringUtils.isEmpty(account.getEmailAddress()) || StringUtils.isEmpty(account.getPassword()))
            return false;
        try {
            Account a = userRepository.findByEmailAddress(account.getEmailAddress());
            if(a!= null)
                return false;
            String p= account.getPassword();
            //p
            //add into Database
        }catch (Exception e){
            log.info("UnExpected ERROR");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkAccount(Account account) {

        return false;
    }

}
