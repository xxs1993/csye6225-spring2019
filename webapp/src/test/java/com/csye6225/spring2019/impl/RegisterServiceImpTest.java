package com.csye6225.spring2019.impl;

import com.csye6225.spring2019.entity.Account;
import com.csye6225.spring2019.repository.UserRepository;
import com.csye6225.spring2019.service.RegisterService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * RegisterServiceImp Tester.
 *
 * @author <Authors name>
 * @since <pre>Jan 28, 2019</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterServiceImpTest {

    @Autowired
    private RegisterService registerService;
    private UserRepository userRepository;
    /**
     *
     * Method: registerAccount(Account account)
     *
     */
    @Test
    public void testRegisterAccount() throws Exception {
//TODO: Test goes here...

        assertFalse(registerService.registerAccount(null));
        Account account = new Account();
        account.setEmailAddress("test");
        account.setPwdString("right");
        userRepository.insertAccount(account);
        assertFalse(registerService.registerAccount(account));

    }

    /**
     *
     * Method: checkAccount(Account account)
     *
     */
    @Test
    public void testCheckAccount() throws Exception {
//TODO: Test goes here... 
    }


} 
