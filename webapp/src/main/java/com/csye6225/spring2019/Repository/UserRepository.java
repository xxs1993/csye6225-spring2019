package com.csye6225.spring2019.Repository;

import com.csye6225.spring2019.Entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Account,Integer> {



}
