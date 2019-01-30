package com.csye6225.spring2019.repository;

import com.csye6225.spring2019.entity.Account;
import org.junit.*;
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





/*
@Test
public void testFindAll() throws Exception {
//TODO: Test goes here...
    System.out.println("testFindAll()");
    List<Account> accounts = userRepository.findAll();
    System.out.println(Arrays.toString(accounts.toArray()));

} 
*/






/*
@Test
public void testFindByEmailAddress() throws Exception {
//TODO: Test goes here...
    Account account1 = new Account();
    account1.setEmailAddress("test");
    account1.setPwdString("right");
    int i = userRepository.insertAccount(account1);
    Account account = userRepository.findByEmailAddress("test");
    System.out.println(account.getEmailAddress() + account.getPwdString());
    Assert.assertTrue(account.getEmailAddress().equals("test"));
    Assert.assertTrue(account.getPwdString().equals("right"));
}
*/





/** 
* 
* Method: checkAccount(Account account) 
* 
*/



/*
@Test
public void testQueryAccountByInfo() throws Exception {
    Account account1 = new Account();
    account1.setEmailAddress("test1");
    account1.setPwdString("right1");
    int i = userRepository.insertAccount(account1);
    Account account = userRepository.queryAccountByInfo("test1","right1");
    //System.out.println(account.getEmailAddress() + account.getPwdString());
    Assert.assertTrue(account.getEmailAddress().equals("test1"));
    Assert.assertTrue(account.getPwdString().equals("right1"));
}
*/




@Test
public void testInsertAccount() throws Exception{
    Account account = new Account();
    account.setEmailAddress("999");
    account.setPwdString("999");
    int i = userRepository.insertAccount(account);
    //System.out.println(i);
    Assert.assertTrue(i == 1);
}


} 
