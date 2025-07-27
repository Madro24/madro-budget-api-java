package madro.finances.budget.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import madro.finances.budget.dto.TransactionCategoryDTO;
import madro.finances.budget.entities.TransactionCategory;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TransactionCategoryMapper {

    @Mapping(target = "expenses", constant = "0.0")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "planned", source = "planned")
    TransactionCategoryDTO categoryToDto(TransactionCategory category);
}
