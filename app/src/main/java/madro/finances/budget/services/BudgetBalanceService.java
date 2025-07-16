package madro.finances.budget.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import madro.finances.budget.dto.BudgetBalanceDTO;
import madro.finances.budget.dto.TransactionCategoryDTO;
import madro.finances.budget.entities.Budget;
import madro.finances.budget.entities.Transaction;
import madro.finances.budget.entities.TransactionCategory;
import madro.finances.budget.mappers.TransactionCategoryMapper;
import madro.finances.budget.repositories.BudgetRepository;
import madro.finances.budget.repositories.TransactionCategoryRepository;
import madro.finances.budget.repositories.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetBalanceService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final TransactionCategoryMapper transactionCategoryMapper;

    public BudgetBalanceDTO getBalance(Integer budgetId) {

        Budget budget = budgetRepository.findById(budgetId)
                                        .orElseThrow(() -> new RuntimeException("Budget not found"));

        List<TransactionCategory> all = transactionCategoryRepository.findByTransactionTypeId(1);
        List<TransactionCategoryDTO> categories = 
            all.stream()
                                                .map(transactionCategoryMapper::categoryToDto)
                                                .collect(Collectors.toList());


        // update expenses for each category based on transactions
        List<Transaction> transactions = 
            transactionRepository.findByBudgetId(budgetId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        categories.forEach(category -> {
            category.setExpenses(
                transactions.stream()
                            .filter(transaction -> transaction.getTransactionCategory().getId() == category.getId())
                            .mapToDouble(Transaction::getAmount)
                            .sum()
            );
        });

        //TODO calculate planned based on category budget
        

        return BudgetBalanceDTO.builder()
                                .id(budgetId)
                                .categories(categories)
                                .planned(categories.stream().mapToDouble(TransactionCategoryDTO::getPlanned).sum())
                                .expenses(categories.stream().mapToDouble(TransactionCategoryDTO::getExpenses).sum())
                                .startDate(budget.getStart())
                                .build();
        
    }

    public BudgetBalanceDTO getBalance() {
        Budget budget = budgetRepository.findFirstByStatus(Budget.Status.ACTIVE);
        return getBalance(budget.getId());
    }
}