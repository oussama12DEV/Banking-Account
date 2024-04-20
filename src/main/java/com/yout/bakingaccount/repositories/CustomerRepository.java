package com.yout.bakingaccount.repositories;

import com.yout.bakingaccount.entities.BankAccount;
import com.yout.bakingaccount.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select c from Customer c where c.nom like :ky")
    List<Customer> searchCustomerBy(@Param("ky") String keyword);

   /* @Query("DELETE  FROM Customer c WHERE c.id = :customerId")
    void deleteCustomersById(@Param("customerId") Long customerId);*/



}
