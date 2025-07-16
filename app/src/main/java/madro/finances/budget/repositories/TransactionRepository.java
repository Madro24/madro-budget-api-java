package madro.finances.budget.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import madro.finances.budget.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<List<Transaction>> findByUserId(Integer userId);
    Optional<List<Transaction>> findByTransactionCategoryId(Integer transactionCategoryId);
    Optional<List<Transaction>> findByTransactionCategoryIdAndBudgetId(Integer transactionCategoryId, Integer budgetId);
    Optional<List<Transaction>> findByBudgetId(Integer budgetId); 
}
