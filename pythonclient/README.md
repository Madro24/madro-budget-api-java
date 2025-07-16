# Ensure Python 3.13 is Installed
```
python3.13 --version
```

# Create a Virtual Environment
```
python3.13 -m venv venv
```
## Activate Virtual Environment
```
source venv/bin/activate
```

# Upgrade pip in the Virtual Environment
```
pip install --upgrade pip
```

# Install Required Packages
Install the necessary gRPC packages using pip:
```
pip install grpcio grpcio-tools
```

# Generate Python Code from .proto File
Use the grpc_tools.protoc command to generate the Python code from your .proto file:
```
python -m grpc_tools.protoc -I../app/src/main/proto --python_out=./proto --grpc_python_out=./proto ../app/src/main/proto/budget.proto
```

# Run the Python Client
```
python budget_client.py
```