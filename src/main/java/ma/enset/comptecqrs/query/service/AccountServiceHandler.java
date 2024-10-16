package ma.enset.comptecqrs.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.comptecqrs.commonapi.dtos.OperationType;
import ma.enset.comptecqrs.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrs.commonapi.events.AccountCreatedEvent;
import ma.enset.comptecqrs.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrs.commonapi.events.AccountDebitedEvent;
import ma.enset.comptecqrs.commonapi.queries.GetAccountByIdQuery;
import ma.enset.comptecqrs.commonapi.queries.GetAllAccountsQuery;
import ma.enset.comptecqrs.query.entities.Account;
import ma.enset.comptecqrs.query.entities.Operation;
import ma.enset.comptecqrs.query.repositories.AccountRepository;
import ma.enset.comptecqrs.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j

public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    //recuperer les informations du compte du event createdevent
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent received");
        accountRepository.save(new Account(
                event.getId(),
                event.getInitialBalance(),
                event.getStatus(),
                event.getCurrency(),
                null


        ));

    }
    //le status existe dans un autre event
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("AccountAdditional info received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("AccountDebitedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);


    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreditedEvent received");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());


    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }


}
