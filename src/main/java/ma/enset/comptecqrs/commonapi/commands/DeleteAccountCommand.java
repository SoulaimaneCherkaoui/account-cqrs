package ma.enset.comptecqrs.commonapi.commands;

public class DeleteAccountCommand extends BaseCommand<String> {

    private double amount;
    private String currency;

    public DeleteAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }



}
