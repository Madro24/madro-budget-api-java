package madro.finances.budget.mappers;

import org.mapstruct.Mapper;
import madro.finances.budget.dto.TransactionCategoryDTO;
import madro.finances.budget.entities.TransactionCategory;

@Mapper(componentModel="spring") 
public interface TransactionCategoryMapper {

    TransactionCategoryDTO categoryToDto(TransactionCategory category);
}
