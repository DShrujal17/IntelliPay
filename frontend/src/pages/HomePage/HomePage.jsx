import { Link } from 'react-router-dom'
import Navbar from '../../components/Navbar/Navbar'
import Footer from '../../components/Footer/Footer'
import Button from '../../components/Button/Button'
import FeatureCard from '../../components/FeatureCard/FeatureCard'
import styles from './HomePage.module.css'

const HomePage = () => {
  const features = [
    {
      icon: '💳',
      title: 'Automated Billing',
      description: 'Streamline your billing process with automated invoice generation and payment tracking.'
    },
    {
      icon: '📊',
      title: 'Usage-Based Pricing',
      description: 'Implement flexible usage-based billing models with customizable metrics and pricing tiers.'
    },
    {
      icon: '🏢',
      title: 'Multi-Tenant Architecture',
      description: 'Secure, isolated data management for multiple businesses with role-based access control.'
    },
    {
      icon: '📈',
      title: 'Real-Time Analytics',
      description: 'Track usage, monitor subscriptions, and analyze revenue with comprehensive reporting.'
    },
    {
      icon: '🔒',
      title: 'Enterprise Security',
      description: 'Bank-level security with JWT authentication and encrypted data transmission.'
    },
    {
      icon: '⚡',
      title: 'Scalable Infrastructure',
      description: 'Built to handle growth with robust architecture that scales with your business needs.'
    }
  ]

  const benefits = [
    'No credit card required',
    '14-day free trial',
    'Cancel anytime',
    '24/7 support'
  ]

  return (
    <div className={styles.homePage}>
      {/* Hero Section */}
      <section className={styles.hero}>
        <div className={styles.heroContainer}>
          <div className={styles.heroContent}>
            <div className={styles.badge}>
              <span>✨ Trusted by SaaS companies worldwide</span>
            </div>
            <h1 className={styles.heroTitle}>
              Smart Billing Made
              <span className={styles.highlight}> Simple</span>
            </h1>
            <p className={styles.heroDescription}>
              IntelliPay is the all-in-one billing platform that helps SaaS businesses 
              manage subscriptions, track usage, and generate invoices automatically. 
              Focus on building your product while we handle the billing.
            </p>
            <div className={styles.heroButtons}>
              <Link to="/signup">
                <Button variant="primary" size="large">
                  Sign Up
                </Button>
              </Link>
              <Link to="/login">
                <Button variant="outline" size="large">
                  Login
                </Button>
              </Link>
            </div>
            <div className={styles.benefits}>
              {benefits.map((benefit, index) => (
                <div key={index} className={styles.benefitItem}>
                  <span className={styles.checkmark}>✓</span>
                  <span>{benefit}</span>
                </div>
              ))}
            </div>
            <div className={styles.signupNote}>
              <span className={styles.noteIcon}>ℹ️</span>
              <span>Note: Only businesses can sign up. Customers are managed by their respective businesses.</span>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className={styles.features}>
        <div className={styles.container}>
          <div className={styles.sectionHeader}>
            <h2 className={styles.sectionTitle}>Everything You Need</h2>
            <p className={styles.sectionDescription}>
              Powerful features designed to simplify your billing workflow and 
              help you scale your SaaS business effortlessly.
            </p>
          </div>
          <div className={styles.featuresGrid}>
            {features.map((feature, index) => (
              <FeatureCard
                key={index}
                icon={feature.icon}
                title={feature.title}
                description={feature.description}
              />
            ))}
          </div>
        </div>
      </section>

      {/* Workflow Section (Innovative Replacement) */}
      <section className={styles.workflow}>
        <div className={styles.container}>
          <div className={styles.sectionHeader}>
            <h2 className={styles.sectionTitle}>How It Works</h2>
            <p className={styles.sectionDescription}>
              Get up and running in minutes with our streamlined process.
            </p>
          </div>
          <div className={styles.workflowGrid}>
            <div className={styles.workflowItem}>
              <div className={styles.workflowIcon}>1</div>
              <h3 className={styles.workflowTitle}>Define Plans</h3>
              <p className={styles.workflowDesc}>Create flexible subscription plans and usage metrics.</p>
            </div>
            <div className={styles.workflowConnector}>→</div>
            <div className={styles.workflowItem}>
              <div className={styles.workflowIcon}>2</div>
              <h3 className={styles.workflowTitle}>Track Usage</h3>
              <p className={styles.workflowDesc}>Automatically record measuring API calls, storage, or seats.</p>
            </div>
            <div className={styles.workflowConnector}>→</div>
            <div className={styles.workflowItem}>
              <div className={styles.workflowIcon}>3</div>
              <h3 className={styles.workflowTitle}>Get Paid</h3>
              <p className={styles.workflowDesc}>We generate invoices and handle collections for you.</p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className={styles.cta}>
        <div className={styles.container}>
          <div className={styles.ctaContent}>
            <h2 className={styles.ctaTitle}>Ready to Get Started?</h2>
            <p className={styles.ctaDescription}>
              Join hundreds of businesses that trust IntelliPay for their billing needs. 
              Start your free trial today—no credit card required.
            </p>
            <div className={styles.ctaButtons}>
              <Link to="/signup">
                <Button variant="primary" size="large">
                  Get Started Free
                </Button>
              </Link>
              <Link to="/login">
                <Button variant="outline" size="large">
                  Sign In to Dashboard
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </section>

      <Footer />
    </div>
  )
}

export default HomePage
