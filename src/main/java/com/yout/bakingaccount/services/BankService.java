package com.yout.bakingaccount.services;
import com.yout.bakingaccount.entities.*;
import com.yout.bakingaccount.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    public void Consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("2e2906da-73ab-414c-8568-04c16f38c016").orElse(null);
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

    }
}
