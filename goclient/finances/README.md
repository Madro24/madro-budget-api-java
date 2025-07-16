# Initialize a Go Module
```
go mod init finances 
go mod tidy
 ```

# Ensure you have the necessary plugins installed:
```
go install google.golang.org/protobuf/cmd/protoc-gen-go@latest

go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest
```

# Generate protobuf Go files
```
protoc --go-grpc_out=. --go_out=. --proto_path=../app/src/main/proto budget.proto
```


# Run Go grpc client
```
go run budget-client.go 
```