package com.csye6225.spring2019.service;

import com.csye6225.spring2019.entity.Account;

public interface RegisterService {

    public abstract String getTime();

    boolean registerAccount(Account account);

    boolean checkAccount(Account account);


}
