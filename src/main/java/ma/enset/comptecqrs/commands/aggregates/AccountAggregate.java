package ma.enset.comptecqrs.commands.aggregates;

import ma.enset.comptecqrs.commonapi.commands.CreateAccountCommand;
import ma.enset.comptecqrs.commonapi.commands.CreditAccountCommand;
import ma.enset.comptecqrs.commonapi.commands.DebitAccountCommand;
import ma.enset.comptecqrs.commonapi.enums.AccountStatus;
import ma.enset.comptecqrs.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrs.commonapi.events.AccountCreatedEvent;
import ma.enset.comptecqrs.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrs.commonapi.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
if(createAccountCommand.getIntialBalance()<0) throw new RuntimeException("impossible");
//envoyé un event
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getIntialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }
    //changer l'etat d'evenement
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId=event.getId();
        this.balance=event.getInitialBalance();
        this.currency=event.getCurrency();
        this.status=AccountStatus.CREATED;
        //une fois l'etat est changé on va commencer une autre event:
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        this.status=event.getStatus();
    }
    //prendre la commande, cree un evenement(classe) et persister l'evenement dans un eventstore
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if(command.getAmount()<0) throw new RuntimeException("impossible");
        AggregateLifecycle.apply(new AccountCreditedEvent(
command.getId(),
command.getAmount(),
                command.getCurrency()
        ));
    }
    //changer l'etat d'application
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance += event.getAmount();
    }
    //on teste l'etat actuel de l'application (les regles metiers) puis on change l'etat dans eventsourcing
    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if(command.getAmount()<0) throw new RuntimeException("impossible");
        if(this.balance<command.getAmount()) throw new RuntimeException("Balance not sufficient exception =>" + balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    //changer l'etat d'application
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance -= event.getAmount();
    }

}
