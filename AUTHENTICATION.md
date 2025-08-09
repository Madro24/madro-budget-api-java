# Authentication API Documentation

## Overview
The application now provides session-based authentication endpoints that create JSESSIONID cookies for maintaining user sessions. This complements the existing Basic Authentication support.

## Authentication Endpoints

### POST `/api/auth/login`
Authenticates a user and creates a session with JSESSIONID cookie.

**Request Body:**
```json
{
    "username": "admin",
    "password": "secure123"
}
```

**Success Response (200 OK):**
```json
{
    "success": true,
    "message": "Authentication successful",
    "sessionId": "A1B2C3D4E5F6G7H8I9J0",
    "username": "admin",
    "authorities": [
        {
            "authority": "ROLE_USER"
        },
        {
            "authority": "ROLE_ADMIN"
        }
    ]
}
```

**Error Response (401 Unauthorized):**
```json
{
    "success": false,
    "message": "Invalid username or password"
}
```

**Cookie Set:**
```
Set-Cookie: JSESSIONID=A1B2C3D4E5F6G7H8I9J0; Path=/; HttpOnly; SameSite=Lax; Max-Age=3600
```

### POST `/api/auth/logout`
Invalidates the current session and clears the JSESSIONID cookie.

**Success Response (200 OK):**
```json
{
    "success": true,
    "message": "Logout successful"
}
```

**Cookie Cleared:**
```
Set-Cookie: JSESSIONID=; Path=/; HttpOnly; SameSite=Lax; Max-Age=0
```

### GET `/api/auth/status`
Returns the current authentication status and session information.

**Response for Authenticated User (200 OK):**
```json
{
    "authenticated": true,
    "username": "admin",
    "authorities": [
        {
            "authority": "ROLE_USER"
        },
        {
            "authority": "ROLE_ADMIN"
        }
    ],
    "sessionId": "A1B2C3D4E5F6G7H8I9J0",
    "sessionValid": true
}
```

**Response for Unauthenticated User (200 OK):**
```json
{
    "authenticated": false,
    "message": "User not authenticated"
}
```

## Usage Examples

### Using curl

#### 1. Login and get session cookie
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"secure123"}' \
  -c cookies.txt
```

#### 2. Use session cookie for subsequent requests
```bash
curl -X GET http://localhost:8080/api/some-protected-endpoint \
  -b cookies.txt
```

#### 3. Check authentication status
```bash
curl -X GET http://localhost:8080/api/auth/status \
  -b cookies.txt
```

#### 4. Logout
```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -b cookies.txt \
  -c cookies.txt
```

### Using JavaScript (Frontend)

#### 1. Login
```javascript
fetch('/api/auth/login', {
    method: 'POST',
    credentials: 'include', // Important: includes cookies
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({
        username: 'admin',
        password: 'secure123'
    })
})
.then(response => response.json())
.then(data => {
    if (data.success) {
        console.log('Login successful:', data);
        // JSESSIONID cookie is automatically stored by browser
    } else {
        console.error('Login failed:', data.message);
    }
});
```

#### 2. Make authenticated requests
```javascript
fetch('/api/some-protected-endpoint', {
    method: 'GET',
    credentials: 'include' // Automatically sends JSESSIONID cookie
})
.then(response => response.json())
.then(data => console.log(data));
```

#### 3. Check authentication status
```javascript
fetch('/api/auth/status', {
    method: 'GET',
    credentials: 'include'
})
.then(response => response.json())
.then(data => {
    if (data.authenticated) {
        console.log('User is authenticated:', data.username);
    } else {
        console.log('User is not authenticated');
    }
});
```

#### 4. Logout
```javascript
fetch('/api/auth/logout', {
    method: 'POST',
    credentials: 'include'
})
.then(response => response.json())
.then(data => {
    if (data.success) {
        console.log('Logout successful');
        // JSESSIONID cookie is automatically cleared
    }
});
```

### Using Postman

1. **Login:**
   - Method: POST
   - URL: `http://localhost:8080/api/auth/login`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON): `{"username":"admin","password":"secure123"}`
   - Postman will automatically store the JSESSIONID cookie

2. **Subsequent Requests:**
   - Postman automatically includes stored cookies
   - No additional configuration needed

## Security Features

- **HttpOnly Cookie**: JSESSIONID cookie cannot be accessed via JavaScript (XSS protection)
- **SameSite=Lax**: Protection against CSRF attacks
- **Session Timeout**: Sessions expire after 1 hour (3600 seconds)
- **Single Session**: Only one active session per user (configurable)
- **Secure Context**: Session management integrated with Spring Security

## Authentication Methods Supported

1. **Basic Authentication**: Username/password in Authorization header
2. **Session Authentication**: JSESSIONID cookie after login
3. **Mixed Mode**: Both methods work simultaneously

## Configuration

### Security Credentials
Set environment variables for credentials:
```bash
export SECURITY_USERNAME=admin
export SECURITY_PASSWORD=secure123
```

### CORS Configuration
Configure allowed origins for cross-origin requests:
```bash
export CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080
export CORS_ALLOWED_METHODS=GET,POST,PUT,DELETE,OPTIONS
export CORS_ALLOWED_HEADERS=*
export CORS_ALLOW_CREDENTIALS=true
```

### Default Values
If environment variables are not set, these defaults will be used:

**Security:**
- Username: `admin`
- Password: `admin`

**CORS:**
- Allowed Origins: `http://localhost:3000,http://localhost:8080,http://127.0.0.1:3000,http://127.0.0.1:8080`
- Allowed Methods: `GET,POST,PUT,DELETE,OPTIONS`
- Allowed Headers: `*`
- Allow Credentials: `true`
