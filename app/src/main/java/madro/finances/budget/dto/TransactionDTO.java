package madro.finances.budget.dto;

import lombok.Builder;
import lombok.Data;
import madro.finances.budget.enums.TransactionStatus;

@Data
@Builder
public class TransactionDTO {
    private Integer categoryId;
    private String description;
    private Double amount;
    private String date;
    private TransactionStatus status;
}
