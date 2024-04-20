package com.yout.bakingaccount.services;

import com.yout.bakingaccount.Exception.BalanceNotSufficentExecption;
import com.yout.bakingaccount.Exception.BankAccountNotFoundExecption;
import com.yout.bakingaccount.Exception.CustomerNotFoundException;
import com.yout.bakingaccount.Exception.MountnullExecption;
import com.yout.bakingaccount.dtos.*;
import com.yout.bakingaccount.entities.BankAccount;

import java.util.List;

public interface BankAccountService {
     CustomerDTO SaveCoustomer(CustomerDTO customerdto);
     CurrentBankAccountDTO SaveCurrentBankAccount(double initilBalance, double overDraft, Long CustomerId) throws CustomerNotFoundException;
    SavingBankAccountDTO SaveSavingBankAccount(double initilBalance, double interestRate, Long CustomerId) throws CustomerNotFoundException;
     //List<Customer> CUSTOMER_LIST();
    //cette methode apres le DTO
    List<CustomerDTO> CUSTOMER_LIST();
     BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundExecption;
     void debit(String accountId,double amount,String description) throws BankAccountNotFoundExecption, BalanceNotSufficentExecption, MountnullExecption;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundExecption, MountnullExecption;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BalanceNotSufficentExecption, BankAccountNotFoundExecption, MountnullExecption;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomerDto(long customerid) throws CustomerNotFoundException;

    CustomerDTO UpdateCoustomer(CustomerDTO customerdto);

    void DeletCustomer(Long customerid);

    List<AccountOperationDTO> operationDTOList(String accountid) ;
     AccountHistoriyDTO getaccounthistory(String accountid,int page,int size) throws BankAccountNotFoundExecption;


    List<CustomerDTO> searchcustomers(String keyword);

}
