package com.insuranceManagement.Controller;

import com.insuranceManagement.Entity.Account;
import com.insuranceManagement.Service.AccountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${idms.api.base-url}")
    private String idmsBaseUrl;

    @Autowired
    private AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

//    create new account and save it to database
//    http://localhost:8080/api/account/create
//    Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
    @PostMapping("/create")
    public ResponseEntity<Account> createNewAccount(@RequestBody Account account) throws Exception{
        try{
            Account accountInfo = accountService.addNewAccount(account);
            return ResponseEntity.ok(accountInfo);
        }
        catch (Exception e) {
            logger.error("Error occurred while creating account: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

//    fetch the list of accounts from an external API and save them to the database
//    http://localhost:8080/api/account/GetAccountList?token=D3E8712A-2FE0-4581-8C98-6B4277F9638F
    @GetMapping("/GetAccountList")
    public ResponseEntity<?> fetchAccountListFromExternalAPI(@RequestParam String token) {
        String url = idmsBaseUrl + "/api/Account/GetAccountList";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("Token", token)
                .queryParam("PageNumber", 1)
                .queryParam("InstitutionID", "107007");
        try {
//            fetch data from the external API
            logger.info("Fetching account list from external API: {}", url);
            ResponseEntity<Map> response = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
//            fetch account data from the response
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> accountsData = (List<Map<String, Object>>) responseBody.get("Data");

//            transform the data into Account entities
            List<Account> accounts = accountsData.stream()
                    .map(this::fetchAndSaveFieldsFromExternalApiAndTransformToRequiredFields)
                    .collect(Collectors.toList());

//            save the fetched accounts to the database
            logger.info("Successfully fetched {} accounts", accounts.size());
            accountService.saveFetchAccounts(accounts);
//            return the response data
            return ResponseEntity.ok(responseBody);
        } catch (HttpClientErrorException e) {
            logger.error("Error fetching account list: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unexpected error occurred while fetching account list: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


//    get a paginated list of accounts from the database
//    http://localhost:8080/api/account/fetchAll?page=0&size=10
//    Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
    @GetMapping(path = "/fetchAll")
    public ResponseEntity<Page<Account>> fetchAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching accounts with pagination - Page: {}, Size: {}", page, size);
        Page<Account> accounts = accountService.fetchAllAccount(page, size);
        return ResponseEntity.ok(accounts);
    }

//    update an account in the database
//    http://localhost:8080/api/account/891188
//    Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
            @PathVariable String id,
            @Valid @RequestBody Account account) {
        try {
            logger.info("Updating account with ID: {}", id);
            Account updatedAccount = accountService.updateAccount(id, account);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            logger.error("Error updating account with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error updating account.", e);
        }
    }

//    delete an account from the database
//    http://localhost:8080/api/account/891188
//    Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) throws Exception {
        try {
            logger.info("Deleting account with ID: {}", id);
            accountService.deleteAccount(id);
            return ResponseEntity.ok("Account deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting account with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting account.");
        }
    }



    private Account fetchAndSaveFieldsFromExternalApiAndTransformToRequiredFields(Map<String, Object> rowData) {
        Account account = new Account();
        Map<String, Object> row = (Map<String, Object>) rowData.get("Row");
//        map AcctID
        account.setAcctID((String) row.get("Acct"));
        if (account.getAcctID() == null || account.getAcctID().isEmpty()) {
            logger.error("AcctID is missing or empty in row: {}", row);
            throw new IllegalArgumentException("AcctID cannot be null or empty");
        }
//        map BorrowerName
        String borrowerName = (String) row.get("BorrowerName");
        if (borrowerName != null) {
            String[] names = borrowerName.split(", ");
            account.setBorrower1LastName(names[0].trim());
            account.setBorrower1FirstName(names.length > 1 ? names[1].trim() : null);
        }
//        map CollateralStockNumber and CollateralDescription
        account.setCollateralStockNumber((String) row.get("Stk"));
//        Map CollateralDescription (Year, Make, Model)
        String collateralDescription = (String) row.get("CollateralDescription");
        if (collateralDescription != null) {
//            splits into year, make, model
            String[] parts = collateralDescription.split(" ", 3);
            account.setCollateralYearModel(parts.length > 0 ? parts[0].trim() : null);
            account.setCollateralMake(parts.length > 1 ? parts[1].trim() : null);
            account.setCollateralModel(parts.length > 2 ? parts[2].trim() : null);
        }
//        map Placeholder Fields (if available in row)
        account.setAcctType((String) row.get("AcctType"));
        account.setContractSalesPrice((String) row.get("ContractSalesPrice"));
        account.setSalesGroupPerson1ID((String) row.get("SalesGroupPerson1ID"));
//        map ContractDate
        String contractDate = (String) row.get("ContractDate");
        if (contractDate != null && !contractDate.isEmpty()) {
            account.setContractDate(contractDate.trim());
        }

        return account;
    }

}
