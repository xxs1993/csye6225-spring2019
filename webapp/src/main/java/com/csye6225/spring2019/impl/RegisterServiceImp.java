package com.csye6225.spring2019.impl;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.repository.UserRepository;
import com.csye6225.spring2019.service.RegisterService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

        if(account == null || StringUtils.isEmpty(account.getEmailAddress()) || StringUtils.isEmpty(account.getPwdString()))
            return false;
        try {
            Account a = userRepository.findByEmailAddress(account.getEmailAddress());
            if(a!= null)
                return false;
            String p= account.getPwdString();
            //BCrypt pwdString hashing with salt
            String hp= BCrypt.hashpw(p,BCrypt.gensalt(12));
            account.setPwdString(hp);
            //TODO add into Database
        }catch (Exception e){
            log.info("UnExpected ERROR");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkAccount(Account account) {
        if(account == null || StringUtils.isEmpty(account.getEmailAddress()) || StringUtils.isEmpty(account.getPwdString()))
            return false;
        try{
            Account a=userRepository.findByEmailAddress(account.getEmailAddress());
            if (a==null)
                return false;
            String p= account.getPwdString();
            String hp=a.getPwdString();
            //check password
            if (BCrypt.checkpw(p,hp))
                log.info("Welcome!");
            else {
                log.info("Failed");
                return false;
            }

        }catch (Exception e){
            log.info("Please Check Your Account Number/Password");
            return false;
        }
        return true;
    }

}
