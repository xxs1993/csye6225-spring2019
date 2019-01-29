package com.csye6225.spring2019.repository;

import com.csye6225.spring2019.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<Account,Integer> {

    Account findByEmailAddress(String email);


    @Query(value="select * from user where binary email_address = ?1 and  binary password = ?2",nativeQuery = true)
    @Transactional
    Account queryAccountByInfo(String emailAddress, String password);

}
