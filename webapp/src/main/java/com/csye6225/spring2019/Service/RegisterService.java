package com.csye6225.spring2019.Service;

import com.csye6225.spring2019.Entity.Account;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface RegisterService {

    public abstract String getTime();

    void registerAccount(Account account);

}
