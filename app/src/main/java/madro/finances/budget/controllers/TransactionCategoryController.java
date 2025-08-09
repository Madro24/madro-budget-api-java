package madro.finances.budget.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import madro.finances.budget.entities.TransactionCategory;
import madro.finances.budget.services.TransactionCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TransactionCategoryController {

    @Autowired
    private TransactionCategoryService txCategoryService;

    @GetMapping
    public List<TransactionCategory> getAllCategories() {
        return txCategoryService.getAllCategories();
    }

    @GetMapping("/{category}")
    public TransactionCategory getCategoryByName(@PathVariable("category") String category) {
        return txCategoryService.getCategoryByName(category);
    }
    
    @PostMapping
    public TransactionCategory createCategory(@RequestBody TransactionCategory category) {
        return txCategoryService.createCategory(category);
    }
}

