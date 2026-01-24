import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// Create axios instance
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor for error handling
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Unauthorized - clear token and redirect to login
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// Auth Service
export const authService = {
  login: async (credentials) => {
    const response = await apiClient.post('/auth/login', credentials)
    return response
  },

  signup: async (userData) => {
    const response = await apiClient.post('/auth/signup', userData)
    return response
  },

  updateUser: async (userData) => {
    const response = await apiClient.put('/auth/update', userData)
    return response
  },

  deleteUser: async () => {
    const response = await apiClient.delete('/auth/delete')
    return response
  },
}

// Business Service
export const businessService = {
  getProfile: async () => {
    const response = await apiClient.get('/business/profile')
    return response
  },

  createCustomer: async (customerData) => {
    const response = await apiClient.post('/business/customers', customerData)
    return response
  },

  getAllCustomers: async () => {
    const response = await apiClient.get('/business/customers')
    return response
  },

  createPlan: async (planData) => {
    const response = await apiClient.post('/business/plans', planData)
    return response
  },

  subscribeCustomer: async (customerId, subscriptionData) => {
    const response = await apiClient.post(
      `/business/customers/${customerId}/subscribe`,
      subscriptionData
    )
    return response
  },

  createUsageMetric: async (metricData) => {
    const response = await apiClient.post('/business/usage-metrics', metricData)
    return response
  },

  assignMetricToPlan: async (planId, metricData) => {
    const response = await apiClient.post(
      `/business/plans/${planId}/usage-metrics`,
      metricData
    )
    return response
  },
}

// Customer Service
export const customerService = {
  getProfile: async () => {
    const response = await apiClient.get('/customer/profile')
    return response
  },

  getInvoices: async () => {
    const response = await apiClient.get('/customer/invoices')
    return response
  },
}

// Admin Service
export const adminService = {
  getAllBusinesses: async () => {
    const response = await apiClient.get('/admin/businesses')
    return response
  },

  approveBusiness: async (businessId) => {
    const response = await apiClient.put(`/admin/businesses/${businessId}/approve`)
    return response
  },

  blockBusiness: async (businessId) => {
    const response = await apiClient.put(`/admin/businesses/${businessId}/block`)
    return response
  },
}

// Usage Service
export const usageService = {
  recordUsage: async (usageData) => {
    const response = await apiClient.post('/usage/record', usageData)
    return response
  },
}

// Invoice Service
export const invoiceService = {
  generateInvoice: async (invoiceData) => {
    const response = await apiClient.post('/invoices/generate', invoiceData)
    return response
  },

  getInvoices: async () => {
    const response = await apiClient.get('/business/invoices')
    return response
  },
}

export default apiClient
