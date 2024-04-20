package com.yout.bakingaccount;

import com.yout.bakingaccount.Exception.BalanceNotSufficentExecption;
import com.yout.bakingaccount.Exception.BankAccountNotFoundExecption;
import com.yout.bakingaccount.Exception.CustomerNotFoundException;
import com.yout.bakingaccount.Exception.MountnullExecption;
import com.yout.bakingaccount.dtos.BankAccountDTO;
import com.yout.bakingaccount.dtos.CurrentBankAccountDTO;
import com.yout.bakingaccount.dtos.CustomerDTO;
import com.yout.bakingaccount.dtos.SavingBankAccountDTO;
import com.yout.bakingaccount.entities.*;
import com.yout.bakingaccount.enums.AccountStatus;
import com.yout.bakingaccount.enums.OpertionType;
import com.yout.bakingaccount.mappers.BankAccountMapperImpl;
import com.yout.bakingaccount.repositories.AccountOperationRepository;
import com.yout.bakingaccount.repositories.BankAccountRepository;
import com.yout.bakingaccount.repositories.CustomerRepository;
import com.yout.bakingaccount.services.BankAccountService;
import com.yout.bakingaccount.services.BankService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.rmi.server.UID;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication

public class BakingAccountApplication {

    public static void main(String[] args)

    {
        SpringApplication.run(BakingAccountApplication.class, args);
    }



    //@Bean
    CommandLineRunner  commandLineRunner(CustomerRepository customerRepository,
                                         AccountOperationRepository accountOperationRepository,
                                         BankAccountRepository bankAccountRepository)
    {
        return args -> {
            Stream.of("Oussama","Yassine","Anass","Omaima").forEach(
                    name->{
                        Customer customer = new Customer();
                        customer.setNom(name);
                        customer.setGmail(name+"gmail.com");
                        customerRepository.save(customer);
                    }
            );
            customerRepository.findAll().forEach(cust -> {

                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setBalance(Math.random()*1000);
                currentAccount.setOverDraft(9000);
                currentAccount.setAccountStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setId(UUID.randomUUID().toString());
                bankAccountRepository.save(currentAccount);



                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setBalance(Math.random()*1000);
                savingAccount.setInterestRate(5.5);
                savingAccount.setAccountStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(bankAccount ->
            {
               for(int i=0;i<10;i++){
                   AccountOperation accountOperation = new AccountOperation();
                   accountOperation.setBankAccount(bankAccount);
                   accountOperation.setOperationDate(new Date());
                   accountOperation.setType(Math.random()>0.5? OpertionType.CREDIT:OpertionType.DEBIT);
                   accountOperation.setAmount(Math.random()*12000);
                   accountOperationRepository.save(accountOperation);
               }


            });


        };
    }


    //en fait dans la couche service

    //@Bean
    CommandLineRunner start(BankService bankService){
        return args -> {
           /* BankAccount bankAccount = bankAccountRepository.findById("2e2906da-73ab-414c-8568-04c16f38c016").orElse(null);
            if(bankAccount==null){
                System.out.println("Account not found");
            }
            System.out.println(bankAccount.getCustomer().getId());
            System.out.println(bankAccount.getCustomer().getNom());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getAccountStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getClass().getSimpleName());

            if (bankAccount instanceof CurrentAccount){
                System.out.println(((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println(((SavingAccount) bankAccount).getInterestRate());

            }
            bankAccount.getAccountOperations().forEach(accountOp ->
            {
                System.out.println("*********************************");
                System.out.println(accountOp.getType()+"\t"+accountOp.getOperationDate()+"\t"+accountOp.getAmount());

            });

        };*/
            bankService.Consulter();
        };

}
    @Bean
    CommandLineRunner start(BankAccountService bankAccountService,
                            BankAccountMapperImpl bankAccountMapper){
        return args -> {
            Stream.of("oussama","Hassana","Anass").forEach(s ->
            {
                CustomerDTO customer = new CustomerDTO();
                customer.setNom(s);
                customer.setGmail(s+"@gmail.com");
                bankAccountService.SaveCoustomer(customer);
            });
           bankAccountService.CUSTOMER_LIST().forEach(customer ->
           {

               try {
                   bankAccountService.SaveCurrentBankAccount(Math.random() * 12222, 9000, customer.getId());

                   bankAccountService.SaveSavingBankAccount(Math.random() * 10000, 5.3, customer.getId());

               }
               catch (CustomerNotFoundException e) {
                   throw new RuntimeException(e);
               }

           });
           try {
               List<BankAccountDTO> bankAccountList = bankAccountService.bankAccountList();
               for (BankAccountDTO bankAccount : bankAccountList) {
                   for (int i = 0; i < 10; i++) {

                       if (bankAccount instanceof CurrentBankAccountDTO) {
                           CurrentBankAccountDTO currentBankAccountDTO = (CurrentBankAccountDTO) bankAccount;
                           BankAccount currentAccount1 = bankAccountMapper.fromCurrentBankAcountDTO(currentBankAccountDTO);
                           bankAccountService.credit(currentAccount1.getId(), 10000 + Math.random() * 12000, "CREDIT");
                           bankAccountService.debit(currentAccount1.getId(), 1000 + Math.random() * 9000, "DEBIT");
                       } else {
                           SavingBankAccountDTO savingBankAccountDTO = (SavingBankAccountDTO) bankAccount;
                           BankAccount savingAccount1 = bankAccountMapper.fromSavingBankAcountDTO(savingBankAccountDTO);
                           bankAccountService.credit(savingAccount1.getId(), 10000 + Math.random() * 12000, "CREDIT");
                           bankAccountService.debit(savingAccount1.getId(), 1000 + Math.random() * 9000, "DEBIT");
                       }


                   }

               }
           } catch (BankAccountNotFoundExecption e) {
               throw new RuntimeException(e);

           }
           catch (MountnullExecption e) {
               throw new RuntimeException(e);

           }
           catch (BalanceNotSufficentExecption e) {
               throw new RuntimeException(e);
           }

        };

    }}

