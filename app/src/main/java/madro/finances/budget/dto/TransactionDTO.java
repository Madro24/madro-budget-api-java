package madro.finances.budget.dto;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import madro.finances.budget.enums.TransactionStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer categoryId;
    private String description;
    private Double amount;
    @Nullable
    private String date;
    private TransactionStatus status;
}
