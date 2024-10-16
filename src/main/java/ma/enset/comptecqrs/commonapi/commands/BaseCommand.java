package ma.enset.comptecqrs.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.annotations.Target;
@Getter
public abstract class BaseCommand<T> {

    @TargetAggregateIdentifier
    private T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
