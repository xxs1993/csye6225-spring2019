package com.csye6225.spring2019.service;

import com.csye6225.spring2019.entity.Account;

public interface RegisterService {

    boolean registerAccount(Account account);

    boolean checkAccount(Account account);

    Account searchAccountByEmailAddress(String emailAddress);


}
