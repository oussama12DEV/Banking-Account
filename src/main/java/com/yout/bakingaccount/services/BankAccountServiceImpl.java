package com.yout.bakingaccount.services;

import com.yout.bakingaccount.Exception.BalanceNotSufficentExecption;
import com.yout.bakingaccount.Exception.BankAccountNotFoundExecption;
import com.yout.bakingaccount.Exception.CustomerNotFoundException;
import com.yout.bakingaccount.Exception.MountnullExecption;
import com.yout.bakingaccount.dtos.*;
import com.yout.bakingaccount.entities.*;
import com.yout.bakingaccount.enums.OpertionType;
import com.yout.bakingaccount.mappers.BankAccountMapperImpl;
import com.yout.bakingaccount.repositories.AccountOperationRepository;
import com.yout.bakingaccount.repositories.BankAccountRepository;
import com.yout.bakingaccount.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
//Log4j pour faire la jounalisation dans les projet Spring booot slf4j c'eqt un API
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;

    // Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public CustomerDTO SaveCoustomer(CustomerDTO customerdto) {
        log.info("saving new Customer");
        Customer customer = bankAccountMapper.fromCustomerDTO(customerdto);
        Customer customersave = customerRepository.save(customer);

        CustomerDTO SaveCustomer = bankAccountMapper.fromCustomer(customersave);
        return SaveCustomer ;
    }

    @Override
    public CurrentBankAccountDTO SaveCurrentBankAccount(double initilBalance, double overDraft, Long CustomerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(CustomerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer Not Found");

        CurrentAccount  bankAccount = new CurrentAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initilBalance);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setCustomer(customer);
        bankAccount.setOverDraft(overDraft);
        CurrentAccount SaveBankAccount = bankAccountRepository.save(bankAccount);


        return bankAccountMapper.fromCurrentBankAccount(SaveBankAccount );
    }

    @Override
    public SavingBankAccountDTO SaveSavingBankAccount(double initilBalance, double interestRate, Long CustomerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(CustomerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer Not Found");
        SavingAccount bankAccount = new SavingAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initilBalance);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setCustomer(customer);
        bankAccount.setInterestRate(interestRate);

        SavingAccount SaveBankAccount = bankAccountRepository.save(bankAccount);


        return bankAccountMapper.fromSavingBankAccount(SaveBankAccount);


    }


    @Override
    public List<CustomerDTO> CUSTOMER_LIST() {
        List<Customer> customers = customerRepository.findAll();
        //1ere methode
        List<CustomerDTO> CustomerDtosList = customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
        /* 2émé méthode
         List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer:customers)
        {
            CustomerDTO customerDTO = bankAccountMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);

        }*/
        return CustomerDtosList;

    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundExecption {

        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundExecption("Bank Not Found"));
        if(bankAccount instanceof CurrentAccount){
            CurrentAccount currentAccount  = (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentBankAccount(currentAccount);
        }
        else {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingBankAccount(savingAccount);
        }


    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundExecption, BalanceNotSufficentExecption, MountnullExecption {



            BankAccount bankAccount = bankAccountRepository.findById(accountId).
                    orElseThrow(()->new BankAccountNotFoundExecption("Bank Not Found"));
            if(bankAccount.getBalance()<amount ){
                throw new BalanceNotSufficentExecption("Solde not  Sufficent");
            }



           if (amount!=0){


        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setType(OpertionType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }
           else {
               throw new MountnullExecption("Mount Egale 0");
           }
    }




    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundExecption, MountnullExecption {


        BankAccount bankAccount = bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundExecption("Bank Not Found"));
        if(amount!=0){


        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setType(OpertionType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
        }
        else {
            throw new MountnullExecption("Mount Egale 0");
        }
    }



    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSufficentExecption, BankAccountNotFoundExecption, MountnullExecption {
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer From"+accountIdSource);


    }
    @Override
    public List<BankAccountDTO> bankAccountList(){

        List<BankAccount> accountList = bankAccountRepository.findAll();
        List<BankAccountDTO> listesaccounts = accountList.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentBankAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingBankAccount(savingAccount);
            }

        }).collect(Collectors.toList());
        return listesaccounts;
    }
    @Override
    public CustomerDTO getCustomerDto(long customerid) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerid).orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        return bankAccountMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO UpdateCoustomer(CustomerDTO customerdto) {
        log.info("saving new Customer");
        Customer customer = bankAccountMapper.fromCustomerDTO(customerdto);
        Customer customersave = customerRepository.save(customer);

        CustomerDTO SaveCustomer = bankAccountMapper.fromCustomer(customersave);
        return SaveCustomer ;
    }
    @Override
    public void DeletCustomer(Long customerid)
    {
        customerRepository.deleteById(customerid);
    }
    @Override
    public List<AccountOperationDTO> operationDTOList(String accountid) {
     List<AccountOperation> listOperation = accountOperationRepository.findByBankAccountId(accountid);
     return listOperation.stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());

     }

    @Override
    public AccountHistoriyDTO getaccounthistory(String accountid, int page,int size) throws BankAccountNotFoundExecption {
        BankAccount bankAccount = bankAccountRepository.findById(accountid).orElse(null);
        if(bankAccount==null){
            throw new BankAccountNotFoundExecption("not found");
        }
        Page<AccountOperation> accountOperations =accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountid, PageRequest.of(page,size));
        AccountHistoriyDTO accountHistoriyDTO = new AccountHistoriyDTO();
        List<AccountOperationDTO> account = accountOperations.getContent().stream().map(aOp ->bankAccountMapper.fromAccountOperation(aOp)).collect(Collectors.toList());
        accountHistoriyDTO.setAccountHistoriyDTOS(account);
        accountHistoriyDTO.setBalance(bankAccount.getBalance());
        accountHistoriyDTO.setAccountid(bankAccount.getId());
        accountHistoriyDTO.setTotalpage(accountOperations.getTotalPages());
        accountHistoriyDTO.setCurrentpage(page);
        accountHistoriyDTO.setSizepage(size);
        return accountHistoriyDTO;
    }


    @Override
    public List<CustomerDTO> searchcustomers(String keyword) {
        List<Customer> customerList = customerRepository.searchCustomerBy(keyword);
        List<CustomerDTO> list = customerList.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());

        return list;
    }




    }

