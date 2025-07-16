package madro.finances.budget.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import madro.finances.budget.dto.CategoryDetailsDTO;
import madro.finances.budget.dto.TransactionCategoryDTO;
import madro.finances.budget.dto.TransactionDTO;
import madro.finances.budget.entities.Budget;
import madro.finances.budget.entities.Transaction;
import madro.finances.budget.entities.TransactionCategory;
import madro.finances.budget.mappers.TransactionCategoryMapper;
import madro.finances.budget.mappers.TransactionMapper;
import madro.finances.budget.repositories.TransactionCategoryRepository;
import madro.finances.budget.repositories.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository txRepository;
    private final TransactionCategoryRepository txCategoryRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionCategoryMapper categoryMapper;
    private final BudgetService budgetService;

    public List<Transaction> getTransactionsByUser(Integer user) {
        return txRepository.findByUserId(user)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public CategoryDetailsDTO getTransactionsByCategory(Integer categoryId) {
        Budget currentBudget = budgetService.getActive();

        TransactionCategoryDTO category = 
            categoryMapper.categoryToDto(
                txCategoryRepository.findById(categoryId)
                                .orElseThrow(() -> new RuntimeException("Category not found"))
            );

        List<TransactionDTO> transactionList = txRepository.findByTransactionCategoryIdAndBudgetId(categoryId, currentBudget.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"))
                .stream()
                    .map(transactionMapper::transactionToDto)
                    .toList();
        
        return CategoryDetailsDTO.builder()
                                 .category(category)
                                 .transactions(transactionList)
                                 .build();
        
    }

    public List<Transaction> getTransactionsByBudget(Integer budget) {
        return txRepository.findByBudgetId(budget)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction createTransaction(TransactionDTO transaction) {
        Transaction tx = transactionMapper.dtoToTransaction(transaction);
        tx.setCreatedAt(new Date());
        tx.setTransactionCategory(
            TransactionCategory.builder()
                .id(transaction.getCategoryId())
                .build()
            );
        tx.setBudget(budgetService.getActive());
        return txRepository.save(tx);
    }
}
