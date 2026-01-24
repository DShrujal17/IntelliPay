import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Footer from '../../components/Footer/Footer'
import Button from '../../components/Button/Button'
import Card from '../../components/Card/Card'
import { authService } from '../../services/api'
import styles from './SignupPage.module.css'

const SignupPage = () => {
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    businessName: ''
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
    setError('')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      const signupData = {
        ...formData,
        role: 'BUSINESS' // Only businesses can sign up
      }

      const response = await authService.signup(signupData)

      // Signup successful - redirect to login with success message
      // Note: Backend doesn't return token on signup, user needs to login
      navigate('/login', {
        state: {
          message: 'Account created successfully! Please login to continue.',
          email: formData.email
        }
      })
    } catch (err) {
      setError(err.response?.data?.message || 'Signup failed. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className={styles.signupPage}>
      <Link to="/" className={styles.backButton}>
        <span>←</span> Back to Home
      </Link>
      <div className={styles.container}>
        <Card className={styles.signupCard}>
          <h1 className={styles.title}>Create Business Account</h1>
          <p className={styles.subtitle}>
            Sign up to start managing your SaaS billing
          </p>

          <div className={styles.businessNote}>
            <span className={styles.noteIcon}>ℹ️</span>
            <span>Only businesses can sign up. Customers are managed by their respective businesses.</span>
          </div>

          {error && (
            <div className={styles.errorMessage}>
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className={styles.form}>
            <div className={styles.formGroup}>
              <label htmlFor="businessName" className={styles.label}>
                Business Name *
              </label>
              <input
                type="text"
                id="businessName"
                name="businessName"
                value={formData.businessName}
                onChange={handleChange}
                className={styles.input}
                placeholder="Your Company Name"
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="username" className={styles.label}>
                Username *
              </label>
              <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
                className={styles.input}
                placeholder="Choose a username"
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="email" className={styles.label}>
                Email Address *
              </label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className={styles.input}
                placeholder="you@example.com"
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="password" className={styles.label}>
                Password *
              </label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className={styles.input}
                placeholder="Create a strong password"
                required
                minLength={6}
              />
            </div>

            <Button
              type="submit"
              variant="primary"
              size="large"
              className={styles.submitButton}
              disabled={loading}
            >
              {loading ? 'Creating Account...' : 'Create Account'}
            </Button>
          </form>

          <p className={styles.loginLink}>
            Already have an account?{' '}
            <Link to="/login" className={styles.link}>
              Sign in
            </Link>
          </p>
        </Card>
      </div>
      <Footer />
    </div>
  )
}

export default SignupPage
