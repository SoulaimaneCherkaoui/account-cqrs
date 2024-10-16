package ma.enset.comptecqrs.query.repositories;

import ma.enset.comptecqrs.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
