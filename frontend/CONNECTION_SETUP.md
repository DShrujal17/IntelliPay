# Frontend-Backend Connection Setup

## ✅ Connection Status: CONFIGURED

The frontend and backend are now properly connected with CORS enabled.

## Configuration Details

### Backend (Spring Boot)
- **Port**: `8080`
- **CORS**: Enabled for `http://localhost:3000`
- **Endpoints**: All `/auth/**` endpoints are public (no authentication required)
- **Security**: JWT-based authentication for protected endpoints

### Frontend (React + Vite)
- **Port**: `3000`
- **API Base URL**: `http://localhost:8080` (default)
- **Configuration**: Set in `src/services/api.js`

## API Endpoints

### Authentication Endpoints
- `POST /auth/login` - User login
- `POST /auth/signup` - Business signup
- `PUT /auth/update` - Update user (requires auth)
- `DELETE /auth/delete` - Delete user (requires auth)

### Response Structure

**Login Response:**
```json
{
  "token": "jwt_token_here",
  "user": {
    "id": "uuid",
    "username": "string",
    "email": "string",
    "role": "BUSINESS|ADMIN|CUSTOMER"
  }
}
```

**Signup Response:**
```json
{
  "message": "User saved successfully",
  "user": {
    "id": "uuid",
    "username": "string",
    "email": "string",
    "role": "BUSINESS"
  }
}
```

## Testing the Connection

1. **Start Backend:**
   ```bash
   cd billing-service
   mvn spring-boot:run
   ```
   Backend should run on `http://localhost:8080`

2. **Start Frontend:**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
   Frontend should run on `http://localhost:3000`

3. **Test Signup:**
   - Navigate to `http://localhost:3000/signup`
   - Fill in business details
   - Submit form
   - Should redirect to login page with success message

4. **Test Login:**
   - Navigate to `http://localhost:3000/login`
   - Enter credentials
   - Should redirect to appropriate dashboard based on role

## Troubleshooting

### CORS Errors
If you see CORS errors in browser console:
- Verify backend is running on port 8080
- Check that `CorsConfig.java` is properly configured
- Restart backend after CORS changes

### Connection Refused
- Ensure backend is running: `http://localhost:8080`
- Check backend logs for errors
- Verify database connection in `application.properties`

### 401 Unauthorized
- Token might be expired or invalid
- Clear localStorage and login again
- Check JWT secret key matches in backend

### 404 Not Found
- Verify endpoint paths match exactly
- Check backend controller `@RequestMapping` paths
- Ensure backend is running and accessible

## Environment Variables

Create `.env` file in frontend directory (optional):
```
VITE_API_BASE_URL=http://localhost:8080
```

If not set, defaults to `http://localhost:8080`
