package madro.finances.budget.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDetailsDTO {
    private TransactionCategoryDTO category;
    private List<TransactionDTO> transactions;
}