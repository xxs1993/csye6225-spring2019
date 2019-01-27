package com.csye6225.spring2019.Repository;

import com.csye6225.spring2019.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<Account,Integer> {

    @Query(value="select * from user where email_address = ?1 and password = ?1",nativeQuery = true)
    @Transactional
    Account queryAccountByInfo(String emailAddress, String password);

}
