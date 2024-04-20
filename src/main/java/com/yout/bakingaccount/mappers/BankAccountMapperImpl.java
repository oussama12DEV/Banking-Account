package com.yout.bakingaccount.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yout.bakingaccount.dtos.AccountOperationDTO;
import com.yout.bakingaccount.dtos.CurrentBankAccountDTO;
import com.yout.bakingaccount.dtos.CustomerDTO;
import com.yout.bakingaccount.dtos.SavingBankAccountDTO;
import com.yout.bakingaccount.entities.AccountOperation;
import com.yout.bakingaccount.entities.CurrentAccount;
import com.yout.bakingaccount.entities.Customer;
import com.yout.bakingaccount.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//il y'a un fremwork qui fait le transfer des donnes d'un objet vers un autre objet c'es le MapStruct
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);

        //customerDTO.setId(customer.getId());
        //customerDTO.setNom(customer.getNom());
        //customerDTO.setGmail(customer.getGmail());
        return customerDTO;



    };
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        //customer.setId(customerDTO.getId());
        //customer.setNom(customerDTO.getNom());
        //customer.setGmail(customerDTO.getGmail());
        return customer;
    };
    public SavingBankAccountDTO  fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO  savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());


        //customerDTO.setId(customer.getId());
        //customerDTO.setNom(customer.getNom());
        //customerDTO.setGmail(customer.getGmail());
        return savingBankAccountDTO;



    };
    public SavingAccount fromSavingBankAcountDTO(SavingBankAccountDTO  savingBankAccountDTO){
        SavingAccount  savingAccount  = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));

        //customer.setId(customerDTO.getId());
        //customer.setNom(customerDTO.getNom());
        //customer.setGmail(customerDTO.getGmail());
        return savingAccount;
    };
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO  currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

        //customerDTO.setId(customer.getId());
        //customerDTO.setNom(customer.getNom());
        //customerDTO.setGmail(customer.getGmail());
        return currentBankAccountDTO;



    };
    public CurrentAccount fromCurrentBankAcountDTO(CurrentBankAccountDTO  currentBankAccountDTO){
        CurrentAccount  currentAccount  = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));

        //customer.setId(customerDTO.getId());
        //customer.setNom(customerDTO.getNom());
        //customer.setGmail(customerDTO.getGmail());
        return currentAccount;
    };
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperation1 = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperation1);
        return accountOperation1;
    }
}
