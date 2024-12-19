package com.insuranceManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account_info")
public class Account {

    @Id
    private String acctID; // Maps to "Acct"

    private String borrower1FirstName;
    private String borrower1LastName;
    private String collateralStockNumber;
    private String collateralYearModel;
    private String collateralMake;
    private String collateralModel;
    private String acctType;
    private String contractSalesPrice;
    private String salesGroupPerson1ID;
    private String contractDate;

    public Account() {
    }

    public Account(String acctID, String borrower1FirstName, String borrower1LastName, String collateralStockNumber, String collateralYearModel, String collateralMake, String collateralModel, String acctType, String contractSalesPrice, String salesGroupPerson1ID, String contractDate) {
        this.acctID = acctID;
        this.borrower1FirstName = borrower1FirstName;
        this.borrower1LastName = borrower1LastName;
        this.collateralStockNumber = collateralStockNumber;
        this.collateralYearModel = collateralYearModel;
        this.collateralMake = collateralMake;
        this.collateralModel = collateralModel;
        this.acctType = acctType;
        this.contractSalesPrice = contractSalesPrice;
        this.salesGroupPerson1ID = salesGroupPerson1ID;
        this.contractDate = contractDate;
    }

    public String getAcctID() {
        return acctID;
    }

    public void setAcctID(String acctID) {
        this.acctID = acctID;
    }

    public String getBorrower1FirstName() {
        return borrower1FirstName;
    }

    public void setBorrower1FirstName(String borrower1FirstName) {
        this.borrower1FirstName = borrower1FirstName;
    }

    public String getBorrower1LastName() {
        return borrower1LastName;
    }

    public void setBorrower1LastName(String borrower1LastName) {
        this.borrower1LastName = borrower1LastName;
    }

    public String getCollateralStockNumber() {
        return collateralStockNumber;
    }

    public void setCollateralStockNumber(String collateralStockNumber) {
        this.collateralStockNumber = collateralStockNumber;
    }

    public String getCollateralYearModel() {
        return collateralYearModel;
    }

    public void setCollateralYearModel(String collateralYearModel) {
        this.collateralYearModel = collateralYearModel;
    }

    public String getCollateralMake() {
        return collateralMake;
    }

    public void setCollateralMake(String collateralMake) {
        this.collateralMake = collateralMake;
    }

    public String getCollateralModel() {
        return collateralModel;
    }

    public void setCollateralModel(String collateralModel) {
        this.collateralModel = collateralModel;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getContractSalesPrice() {
        return contractSalesPrice;
    }

    public void setContractSalesPrice(String contractSalesPrice) {
        this.contractSalesPrice = contractSalesPrice;
    }

    public String getSalesGroupPerson1ID() {
        return salesGroupPerson1ID;
    }

    public void setSalesGroupPerson1ID(String salesGroupPerson1ID) {
        this.salesGroupPerson1ID = salesGroupPerson1ID;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }
}
