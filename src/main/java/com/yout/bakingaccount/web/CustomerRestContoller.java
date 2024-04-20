package com.yout.bakingaccount.web;

import com.yout.bakingaccount.Exception.BankAccountNotFoundExecption;
import com.yout.bakingaccount.Exception.CustomerNotFoundException;
import com.yout.bakingaccount.dtos.AccountOperationDTO;
import com.yout.bakingaccount.dtos.CustomerDTO;
import com.yout.bakingaccount.entities.Customer;
import com.yout.bakingaccount.mappers.BankAccountMapperImpl;
import com.yout.bakingaccount.repositories.CustomerRepository;
import com.yout.bakingaccount.services.BankAccountService;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")

public class CustomerRestContoller {

    private BankAccountService bankAccountService;
                private CustomerRepository customerRepository;
    private BankAccountMapperImpl bankAccountMapper;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> customers(){
      return bankAccountService.CUSTOMER_LIST();
    };
    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO getCustomerDto(@PathVariable(name = "id") long customerid) throws CustomerNotFoundException {
        return bankAccountService.getCustomerDto(customerid);
    }
    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO SaveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO customerdto = bankAccountService.SaveCoustomer(customerDTO);
        return  customerdto;

    }
    @PutMapping ("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO Updatecustomer(@PathVariable() long id ,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(id);
        return bankAccountService.UpdateCoustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deletCustomres(@PathVariable long id){
        bankAccountService.DeletCustomer(id);
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
  return bankAccountService.searchcustomers("%"+keyword+"%");

    }


}
