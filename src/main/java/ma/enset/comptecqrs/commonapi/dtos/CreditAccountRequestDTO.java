package ma.enset.comptecqrs.commonapi.dtos;

import lombok.Data;

@Data
public class CreditAccountRequestDTO {
    private String id;
    private double amount;
    private String currency;
}
