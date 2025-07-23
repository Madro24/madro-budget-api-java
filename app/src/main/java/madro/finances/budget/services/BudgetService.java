package madro.finances.budget.services;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import madro.finances.budget.entities.Budget;
import madro.finances.budget.entities.Transaction;
import madro.finances.budget.repositories.BudgetRepository;
import madro.finances.budget.repositories.TransactionRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public List<Budget> getBudgets() {
        return budgetRepository.findAll();
    }

    @Transactional
    public void startBudget() {
        // Get the current active budget
        Budget currentBudget = budgetRepository.findFirstByStatus(Budget.Status.ACTIVE);

        if (currentBudget != null) {
            // Calculate total expenses for current budget
            List<Transaction> transactions = 
                    transactionRepository.findByBudgetId(currentBudget.getId())
                        .orElseThrow(() -> new RuntimeException("Transaction not found"));
            float totalExpenses = transactions.stream()
                                              .map(Transaction::getAmount)
                                              .reduce(0f, Float::sum);

            // Update current budget's expenses and balance
            currentBudget.setExpenses(totalExpenses);
            float balance = currentBudget.getPlanned() - totalExpenses;
            currentBudget.setPreBalance(balance);
            currentBudget.setStatus(Budget.Status.CLOSED);
            budgetRepository.save(currentBudget);

            // Create new budget
            Budget newBudget = new Budget();
            newBudget.setStart(new Date()); // Set the appropriate start date
            newBudget.setStatus(Budget.Status.ACTIVE);
            newBudget.setPlanned(0f);
            newBudget.setPreBalance(balance);
            newBudget.setUpdatedAt(new Date());
            budgetRepository.save(newBudget);
        } else {
            // Create new budget
            Budget newBudget = new Budget();
            newBudget.setStart(new Date()); // Set the appropriate start date
            newBudget.setStatus(Budget.Status.ACTIVE);
            newBudget.setPlanned(0f);
            newBudget.setPreBalance(0f);
            newBudget.setUpdatedAt(new Date());
            budgetRepository.save(newBudget);
        }
    }

    public Budget getBudget(Integer budget) {
        return budgetRepository.findById(budget)
                    .orElseThrow(() -> new RuntimeException("Budget not found"));
        // List<Transaction> transactions = transactionRepository.findByBudgetId(budget);
    }

    public List<Transaction> getTransactions(Integer budget) {
        return transactionRepository.findByBudgetId(budget)
                    .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Budget getActive(){
        return budgetRepository.findFirstByStatus(Budget.Status.ACTIVE);
    }
}