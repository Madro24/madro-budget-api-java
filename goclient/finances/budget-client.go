package main

import (
	"context"
	"log"
	"time"

	"google.golang.org/grpc"

	protoBudget "finances/proto/budget"
)

const (
	address = "localhost:9090" // Replace with your server address
)

func main() {
	// Set up a connection to the server.
	conn, err := grpc.Dial(address, grpc.WithInsecure(), grpc.WithBlock())
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	client := protoBudget.NewBudgetServiceClient(conn)

	// Contact the server and print out its response.
	id := int32(1) // Replace with a valid ID
	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()

	req := &protoBudget.BudgetRequest{Id: id}
	res, err := client.GetBudget(ctx, req)
	if err != nil {
		log.Fatalf("could not get budget: %v", err)
	}
	log.Printf("Budget ID: %d, Amount: %f", res.GetId(), res.GetAmount())
}
