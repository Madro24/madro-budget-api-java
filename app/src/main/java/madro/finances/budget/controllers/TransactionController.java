package madro.finances.budget.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import madro.finances.budget.dto.CategoryDetailsDTO;
import madro.finances.budget.dto.TransactionDTO;
import madro.finances.budget.entities.Transaction;
import madro.finances.budget.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user/{username}")
    public List<Transaction> getTransactionsByUser(@PathVariable("username") Integer user) {
        return transactionService.getTransactionsByUser(user);
    }
    
    @GetMapping("/category/{category}")
    public CategoryDetailsDTO getTransactionsByCategory(@PathVariable("category") Integer category) {
        return transactionService.getTransactionsByCategory(category);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionDTO transaction) {
        return transactionService.createTransaction(transaction);
    }

}

