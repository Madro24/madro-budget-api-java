package madro.finances.budget.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import madro.finances.budget.entities.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findAll();
    Budget findFirstByStatus(Budget.Status status); // Add this method to find the active budget
}
