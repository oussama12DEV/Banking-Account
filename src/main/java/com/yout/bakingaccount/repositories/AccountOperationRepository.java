package com.yout.bakingaccount.repositories;

import com.yout.bakingaccount.entities.AccountOperation;
import com.yout.bakingaccount.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String AccountId);
    Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountid, Pageable pageable);
}
