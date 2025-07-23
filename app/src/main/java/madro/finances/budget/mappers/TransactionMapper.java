package madro.finances.budget.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import madro.finances.budget.dto.TransactionDTO;
import madro.finances.budget.entities.Transaction;

@Mapper(componentModel="spring")
public interface TransactionMapper {

    @Mapping(target = "date", source = "createdAt", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "categoryId", source = "transactionCategory.id")
    TransactionDTO transactionToDto(Transaction transaction);

    @Mapping(target = "createdAt", source = "date", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "transactionCategory", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "budget", ignore = true)
    Transaction dtoToTransaction(TransactionDTO transactionDTO);
}