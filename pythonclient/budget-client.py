import grpc
import proto.budget_pb2
import proto.budget_pb2_grpc

def run():
    # Create a channel to connect to the server
    channel = grpc.insecure_channel('localhost:9090')  # Replace with your server address
    stub = proto.budget_pb2_grpc.BudgetServiceStub(channel)

    # Create a request
    request = proto.budget_pb2.BudgetRequest(id=1)  # Replace with a valid ID

    # Call the GetBudget method
    response = stub.GetBudget(request)
    print(f"Budget ID: {response.id}, Amount: {response.amount}")

if __name__ == '__main__':
    run()