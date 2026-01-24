# IntelliPay Frontend

React + Vite frontend for the IntelliPay SaaS Billing Platform.

## Getting Started

### Prerequisites
- Node.js 18+ and npm
- Backend API running on `http://localhost:8080` (or configure in `.env`)

### Installation

1. Install dependencies:
```bash
npm install
```

2. (Optional) Configure API URL:
   - Copy `.env.example` to `.env`
   - Update `VITE_API_BASE_URL` if your backend runs on a different port

3. Start development server:
```bash
npm run dev
```

The app will be available at `http://localhost:3000`

4. Build for production:
```bash
npm run build
```

## Project Structure

```
frontend/
├── src/
│   ├── components/        # Reusable UI components
│   │   ├── Button/       # Button component
│   │   ├── Card/         # Card component
│   │   ├── FeatureCard/  # Feature card component
│   │   ├── Navbar/       # Navigation bar
│   │   └── Footer/       # Footer component
│   ├── pages/            # Page components
│   │   ├── HomePage/     # Landing page
│   │   ├── LoginPage/    # Login page
│   │   └── SignupPage/   # Business signup page
│   ├── services/         # API service layer
│   │   └── api.js        # Axios client and API methods
│   ├── utils/            # Utility functions
│   │   ├── constants.js  # App constants
│   │   └── helpers.js    # Helper functions
│   └── styles/           # Global styles
│       ├── index.css     # Base styles
│       └── App.css       # App styles
├── public/               # Public assets
└── package.json
```

## Features

- ✅ Modern React 18 with Vite
- ✅ Modular component architecture
- ✅ Responsive design with CSS Modules
- ✅ React Router for navigation
- ✅ Axios for API calls with interceptors
- ✅ JWT token management
- ✅ Professional UI/UX
- ✅ Business-only signup flow

## Pages

### Home Page (`/`)
- Hero section with value proposition
- Features showcase (6 key features)
- Call-to-action buttons
- Note about business-only signup

### Login Page (`/login`)
- Email and password authentication
- Success message display (after signup)
- Redirects to appropriate dashboard based on role

### Signup Page (`/signup`)
- Business account creation form
- Includes business name, username, email, password
- Note: Only businesses can sign up
- Redirects to login after successful signup

## API Integration

The frontend is configured to work with the IntelliPay backend API. All API calls are handled through the `services/api.js` file with automatic token injection and error handling.

### Available Services:
- `authService` - Authentication (login, signup, update, delete)
- `businessService` - Business operations
- `customerService` - Customer operations
- `adminService` - Admin operations
- `usageService` - Usage recording
- `invoiceService` - Invoice management

## Development

- Uses CSS Modules for scoped styling
- ESLint configured for code quality
- Hot module replacement enabled in development
- Proxy configured for API calls (see `vite.config.js`)

## Notes

- Only businesses can sign up through the frontend
- Customers are created by businesses through the business dashboard
- Admin accounts are created separately (not through signup)
- JWT tokens are stored in localStorage
- Automatic redirect to login on 401 errors
