package madro.finances.budget.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import madro.finances.budget.dto.TransactionCategoryDTO;
import madro.finances.budget.entities.TransactionCategory;

@Mapper(componentModel = "spring")
public interface TransactionCategoryMapper {

    @Mapping(target = "expenses", ignore = true)
    TransactionCategoryDTO categoryToDto(TransactionCategory category);
}
