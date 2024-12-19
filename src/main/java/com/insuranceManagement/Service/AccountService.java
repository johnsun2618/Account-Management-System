package com.insuranceManagement.Service;

import com.insuranceManagement.Entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AccountService {

    Account addNewAccount(Account account) throws Exception;

    void saveFetchAccounts(List<Account> accountData);

    Page<Account> fetchAllAccount(int page, int size);

    Account updateAccount(String id, Account account) throws Exception;

    void deleteAccount(String id) throws Exception;

}
