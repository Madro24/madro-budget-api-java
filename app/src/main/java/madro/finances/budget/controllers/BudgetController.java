package madro.finances.budget.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import madro.finances.budget.dto.BudgetBalanceDTO;
import madro.finances.budget.entities.Budget;
import madro.finances.budget.entities.Transaction;
import madro.finances.budget.services.BudgetBalanceService;
import madro.finances.budget.services.BudgetService;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetBalanceService budgetBalanceService;

    @GetMapping
    public List<Budget> getBudget() {
        return budgetService.getBudgets();
    }
    
    @GetMapping("/start")
    public void startBudget() {
        budgetService.startBudget();
    }

    @GetMapping("/{budget}/transactions")
    public List<Transaction> getBudgetTransactions(@PathVariable Integer budget) {
        return budgetService.getTransactions(budget);
    }

    @GetMapping("/active")
    public BudgetBalanceDTO getBudgetActive() {
        return budgetBalanceService.getBalance();
    }

    @GetMapping("/{budget}")
    public BudgetBalanceDTO getBudget(@PathVariable Integer budget) {
        return budgetBalanceService.getBalance(budget);
    }



}
