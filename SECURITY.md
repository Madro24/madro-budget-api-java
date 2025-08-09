# Basic Authentication Configuration

## Overview
The application is now protected with Spring Security Basic Authentication. All web endpoints (except health checks and Swagger UI) require authentication.

## Environment Variables

Set the following environment variables to configure authentication credentials:

- `SECURITY_USERNAME` - The username for basic authentication (default: "admin")
- `SECURITY_PASSWORD` - The password for basic authentication (default: "admin")

## Example Usage

### Setting Environment Variables

#### Option 1: Export in shell
```bash
export SECURITY_USERNAME=myuser
export SECURITY_PASSWORD=mypassword
```

#### Option 2: Create a .env file
Create a `.env` file in the project root:
```properties
SECURITY_USERNAME=myuser
SECURITY_PASSWORD=mypassword
```

#### Option 3: Set in IDE/VS Code launch configuration
Update your `.vscode/launch.json`:
```json
{
    "type": "java",
    "name": "Launch App",
    "request": "launch",
    "mainClass": "madro.finances.budget.App",
    "env": {
        "SECURITY_USERNAME": "myuser",
        "SECURITY_PASSWORD": "mypassword"
    }
}
```

### Testing Authentication

#### Using curl
```bash
# This will fail with 401 Unauthorized
curl http://localhost:8080/api/endpoint

# This will succeed with proper credentials
curl -u myuser:mypassword http://localhost:8080/api/endpoint
```

#### Using Postman
1. Set the Authorization type to "Basic Auth"
2. Enter your username and password
3. Make requests to protected endpoints

### Public Endpoints (No Authentication Required)

The following endpoints are accessible without authentication:
- `/actuator/health` - Health check endpoint
- `/actuator/info` - Application info
- `/swagger-ui/**` - Swagger UI interface
- `/v3/api-docs/**` - OpenAPI documentation

### Protected Endpoints

All other endpoints require Basic Authentication:
- All REST API endpoints
- All business logic endpoints
- Admin endpoints

## Security Features

- **Password Encoding**: Passwords are stored using BCrypt hashing
- **HTTP Basic Authentication**: Standard authentication method
- **CSRF Protection**: Disabled for API usage
- **Security Headers**: 
  - X-Frame-Options: DENY
  - Strict-Transport-Security with 1-year max-age
- **Role-based Access**: Users have both USER and ADMIN roles

## Development vs Production

- **Development**: Use default credentials (admin/admin) or set via environment variables
- **Production**: Always set strong credentials using environment variables
  ```bash
  export SECURITY_USERNAME=prod_user
  export SECURITY_PASSWORD=strong_random_password_123!
  ```
