package ma.enset.comptecqrs.commonapi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor

public class GetAccountByIdQuery {
    private String id;

}
