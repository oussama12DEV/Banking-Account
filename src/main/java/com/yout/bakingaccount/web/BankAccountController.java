package com.yout.bakingaccount.web;

import com.yout.bakingaccount.Exception.BalanceNotSufficentExecption;
import com.yout.bakingaccount.Exception.BankAccountNotFoundExecption;
import com.yout.bakingaccount.Exception.MountnullExecption;
import com.yout.bakingaccount.dtos.*;
import com.yout.bakingaccount.entities.BankAccount;
import com.yout.bakingaccount.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class BankAccountController {
    private BankAccountService bankAccountService;


   @GetMapping("/accounts/{accountid}")
  public BankAccountDTO getBankAccount(@PathVariable(name = "accountid") String accountid) throws BankAccountNotFoundExecption {
        return bankAccountService.getBankAccount(accountid);

  }
  @GetMapping("/accounts")
  public List<BankAccountDTO> ListAccount(){
        return bankAccountService.bankAccountList();
  }
    @GetMapping("/accounts/{accountId}/operation")
    public List<AccountOperationDTO> getHistorique(@PathVariable String accountId)  {
        return bankAccountService.operationDTOList(accountId);
    }
    @GetMapping("/accounts/{accountid}/PageOperation")
    public AccountHistoriyDTO getAccountHistory(@PathVariable String accountid,
                                                @RequestParam(name = "page",defaultValue = "0") int page,
                                                @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundExecption {
        return bankAccountService.getaccounthistory(accountid,page,size);
    }
    @PostMapping("/accounts/credit")
    public CreditDTO CreitOpertaion(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundExecption, MountnullExecption {
       bankAccountService.credit(creditDTO.getAccccountId(),creditDTO.getMount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BalanceNotSufficentExecption, BankAccountNotFoundExecption, MountnullExecption {
                this.bankAccountService.debit(debitDTO.getAcountid(),debitDTO.getMount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/transfer")
    public TransferDTO Transfer(@RequestBody TransferDTO transferDTO) throws BalanceNotSufficentExecption, BankAccountNotFoundExecption, MountnullExecption {
         bankAccountService.transfer(transferDTO.getAcountIdSource()
                ,transferDTO.getAcountIdDestination(),
                transferDTO.getMount());
         return transferDTO;
    }
   /* @GetMapping ("/accounts/{id}/")
    public BankAccount getlist(@PathVariable(name = "id") String id) throws BalanceNotSufficentExecption, BankAccountNotFoundExecption, MountnullExecption {


        return (BankAccount) bankAccountService.getlistacounts(id);
    }*/







}
