// Code generated by protoc-gen-go-grpc. DO NOT EDIT.
// versions:
// - protoc-gen-go-grpc v1.2.0
// - protoc             v5.29.3
// source: budget.proto

package budget

import (
	context "context"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.32.0 or later.
const _ = grpc.SupportPackageIsVersion7

// BudgetServiceClient is the client API for BudgetService service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type BudgetServiceClient interface {
	// Sends a greeting
	GetBudget(ctx context.Context, in *BudgetRequest, opts ...grpc.CallOption) (*BudgetResponse, error)
}

type budgetServiceClient struct {
	cc grpc.ClientConnInterface
}

func NewBudgetServiceClient(cc grpc.ClientConnInterface) BudgetServiceClient {
	return &budgetServiceClient{cc}
}

func (c *budgetServiceClient) GetBudget(ctx context.Context, in *BudgetRequest, opts ...grpc.CallOption) (*BudgetResponse, error) {
	out := new(BudgetResponse)
	err := c.cc.Invoke(ctx, "/budget.BudgetService/GetBudget", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// BudgetServiceServer is the server API for BudgetService service.
// All implementations must embed UnimplementedBudgetServiceServer
// for forward compatibility
type BudgetServiceServer interface {
	// Sends a greeting
	GetBudget(context.Context, *BudgetRequest) (*BudgetResponse, error)
	mustEmbedUnimplementedBudgetServiceServer()
}

// UnimplementedBudgetServiceServer must be embedded to have forward compatible implementations.
type UnimplementedBudgetServiceServer struct {
}

func (UnimplementedBudgetServiceServer) GetBudget(context.Context, *BudgetRequest) (*BudgetResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetBudget not implemented")
}
func (UnimplementedBudgetServiceServer) mustEmbedUnimplementedBudgetServiceServer() {}

// UnsafeBudgetServiceServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to BudgetServiceServer will
// result in compilation errors.
type UnsafeBudgetServiceServer interface {
	mustEmbedUnimplementedBudgetServiceServer()
}

func RegisterBudgetServiceServer(s grpc.ServiceRegistrar, srv BudgetServiceServer) {
	s.RegisterService(&BudgetService_ServiceDesc, srv)
}

func _BudgetService_GetBudget_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(BudgetRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(BudgetServiceServer).GetBudget(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/budget.BudgetService/GetBudget",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(BudgetServiceServer).GetBudget(ctx, req.(*BudgetRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// BudgetService_ServiceDesc is the grpc.ServiceDesc for BudgetService service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var BudgetService_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "budget.BudgetService",
	HandlerType: (*BudgetServiceServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "GetBudget",
			Handler:    _BudgetService_GetBudget_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "budget.proto",
}
