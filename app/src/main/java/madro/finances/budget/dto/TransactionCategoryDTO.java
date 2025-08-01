package madro.finances.budget.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionCategoryDTO {
    private String name;
    private Integer id;
    private Double planned;
    private Double expenses;

}
