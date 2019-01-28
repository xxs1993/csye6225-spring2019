package com.csye6225.spring2019.service;

import com.csye6225.spring2019.entity.Account;

public interface RegisterService {

    public abstract String getTime();

    void registerAccount(Account account);

}
