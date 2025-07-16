package madro.finances.budget.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetBalanceDTO {
    Integer id;
    Double expenses;
    Double planned;
    Date startDate;
    List<TransactionCategoryDTO> categories;

}
