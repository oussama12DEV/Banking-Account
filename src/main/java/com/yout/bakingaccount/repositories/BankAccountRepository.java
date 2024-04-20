package com.yout.bakingaccount.repositories;

import com.yout.bakingaccount.dtos.BankAccountDTO;
import com.yout.bakingaccount.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

}
