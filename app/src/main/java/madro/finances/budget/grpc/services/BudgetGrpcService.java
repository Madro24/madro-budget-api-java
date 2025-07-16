package madro.finances.budget.grpc.services;

import madro.finances.budget.entities.Budget;
import madro.finances.budget.grpc.*;
import madro.finances.budget.services.BudgetService;
import net.devh.boot.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;

@GrpcService
public class BudgetGrpcService extends BudgetServiceGrpc.BudgetServiceImplBase {

    private final BudgetService budgetService;

    public BudgetGrpcService(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Override
    public void getBudget(BudgetRequest request, StreamObserver<BudgetResponse> responseObserver) {
        try {
            Budget budget = budgetService.getBudget(request.getId());
            if (budget == null) {
                responseObserver.onError(new RuntimeException("Budget not found"));
                return;
            }
            BudgetResponse.Builder responseBuilder = BudgetResponse.newBuilder();

            BudgetResponse response = responseBuilder
                    .setId(budget.getId())
                    .setAmount(budget.getExpenses())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }
    }

}
