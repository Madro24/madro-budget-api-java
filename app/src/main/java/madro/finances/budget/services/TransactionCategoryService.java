package madro.finances.budget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import madro.finances.budget.entities.TransactionCategory;
import madro.finances.budget.repositories.TransactionCategoryRepository;

import java.util.List;

@Service
public class TransactionCategoryService {

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    public List<TransactionCategory> getAllCategories() {
        return transactionCategoryRepository.findAll();
    }

    public TransactionCategory getCategoryByName(String category) {
        return transactionCategoryRepository.findByName(category);
    }

    public TransactionCategory createCategory(TransactionCategory category) {
        // TODO Auto-generated method stub
        return transactionCategoryRepository.save(category);
    }
}