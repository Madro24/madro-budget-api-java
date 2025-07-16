package madro.finances.budget.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import madro.finances.budget.entities.TransactionCategory;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Integer> {
    List<TransactionCategory> findAll();
    List<TransactionCategory> findByTransactionTypeId(Integer transactionTypeId);
    TransactionCategory findByName(String name);
}
