package ma.enset.comptecqrs.query.repositories;

import ma.enset.comptecqrs.query.entities.Account;
import ma.enset.comptecqrs.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
