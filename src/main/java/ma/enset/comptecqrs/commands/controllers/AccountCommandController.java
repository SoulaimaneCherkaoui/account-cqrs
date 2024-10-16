package ma.enset.comptecqrs.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.comptecqrs.commonapi.commands.CreateAccountCommand;
import ma.enset.comptecqrs.commonapi.commands.CreditAccountCommand;
import ma.enset.comptecqrs.commonapi.commands.DebitAccountCommand;
import ma.enset.comptecqrs.commonapi.dtos.CreateAccountRequestDTO;
import ma.enset.comptecqrs.commonapi.dtos.CreditAccountRequestDTO;
import ma.enset.comptecqrs.commonapi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping(path="/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
CompletableFuture<String> commandresponse=commandGateway.send(new CreateAccountCommand(
        UUID.randomUUID().toString(),
        request.getIntialBalance(),
        request.getCurrency()
));
return commandresponse;
    }
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();

    }
    //envoyer une command(classe) depuis un dto
    @PutMapping(path="/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandresponse=commandGateway.send(new CreditAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandresponse;
    }
    @PutMapping(path="/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandresponse=commandGateway.send(new DebitAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandresponse;
    }
}
