package com.insuranceManagement.Service.Implementation;

import com.insuranceManagement.Entity.Account;
import com.insuranceManagement.Exception.ResourceNotFoundException;
import com.insuranceManagement.Repository.AccountRepository;
import com.insuranceManagement.Service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Override
    public Account addNewAccount(Account account) throws Exception {
        try {
            return accountRepository.save(account);
        }
        catch (Exception e) {
            logger.error("Error occurred while saving the new account: {}", e.getMessage(), e);
            throw new Exception("Something went wrong please check then try again", e);
        }
    }

    //    Before inserting data into the database, check if the record with the given AcctID already exists.
//    If it exists, skip the insertion; otherwise, insert the new record.
    @Override
    public void saveFetchAccounts(List<Account> accounts) {
        logger.info("Starting to save accounts. Total accounts: {}", accounts.size());
        // Get all existing AcctIDs in one query
        List<String> existingAcctIds = accountRepository.findAllAcctIds();
        // Filter out accounts that already exist
        List<Account> newAccounts = accounts.stream()
                .filter(account -> !existingAcctIds.contains(account.getAcctID()))
                .collect(Collectors.toList());
        // Save only new accounts
        if (!newAccounts.isEmpty()) {
            logger.info("Saving {} new accounts to the database.", newAccounts.size());
            accountRepository.saveAll(newAccounts);
        } else {
            logger.info("No new accounts to save. All accounts already exist.");
        }
    }

//    fetch all accounts from the database with pagination
    @Override
    public Page<Account> fetchAllAccount(int page, int size) {
        logger.info("Fetching accounts with pagination - Page: {}, Size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAll(pageable);
    }

//    update an existing account based on the provided account data and by ID
    @Override
    public Account updateAccount(String id, Account account) throws Exception {
        try {
            logger.info("Attempting to update account with ID: {}", id);

//            retrieve existing account from the database
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
//           update fields with the new data
        existingAccount.setAcctType(account.getAcctType());
        existingAccount.setBorrower1FirstName(account.getBorrower1FirstName());
        existingAccount.setCollateralMake(account.getCollateralMake());
        existingAccount.setCollateralModel(account.getCollateralModel());
        existingAccount.setCollateralStockNumber(account.getCollateralStockNumber());
        existingAccount.setBorrower1LastName(account.getBorrower1LastName());
        existingAccount.setContractSalesPrice(account.getContractSalesPrice());
        existingAccount.setSalesGroupPerson1ID(account.getSalesGroupPerson1ID());
        existingAccount.setContractDate(account.getContractDate());
        existingAccount.setCollateralYearModel(account.getCollateralYearModel());

//              Save the updated account
            Account updatedAccount = accountRepository.save(existingAccount);
            logger.info("Account with ID: {} successfully updated.", id);
            return updatedAccount;

        } catch (ResourceNotFoundException e) {
            logger.error("Error updating account with ID: {}. Account not found.", id);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating account with ID: {}: {}", id, e.getMessage(), e);
            throw new Exception("An error occurred while updating the account.", e);
        }
    }

//    delete an account from the database
    @Override
    public void deleteAccount(String id) throws Exception {
        try {
            logger.info("Attempting to delete account with ID: {}", id);

//            check if the account exist or not
            Optional<Account> account = accountRepository.findById(id);
            if(account.isEmpty()){
                logger.error("Account with ID: {} not found for deletion.", id);
                throw new Exception("Account not found");
            } else {
//                delte the account
                accountRepository.deleteById(id);
                logger.info("Account with ID: {} successfully deleted.", id);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("Invalid account with ID: {} during delete operation .", id);
            throw new Exception("Invalid id: " + id, e);
        } catch (Exception e){
            logger.error("Unexpected error occurred while deleting account with ID: {}: {}", id, e.getMessage(), e);
            throw new Exception("An error occurred while deleting the account.", e);
        }
    }

}
