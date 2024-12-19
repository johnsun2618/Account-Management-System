package com.insuranceManagement.Repository;

import com.insuranceManagement.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a.acctID FROM Account a")
    List<String> findAllAcctIds();

}
