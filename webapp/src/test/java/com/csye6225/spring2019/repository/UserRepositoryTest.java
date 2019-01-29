package com.csye6225.spring2019.repository;

import com.csye6225.spring2019.entity.Account;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTime() 
* 
*/ 
@Test
public void testGetTime() throws Exception { 
//TODO: Test goes here...
    List<Account> accounts = userRepository.findAll();
    System.out.println(Arrays.toString(accounts.toArray()));

} 

/** 
* 
* Method: registerAccount(Account account) 
* 
*/ 
@Test
public void testRegisterAccount() throws Exception { 
//TODO: Test goes here... 
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
