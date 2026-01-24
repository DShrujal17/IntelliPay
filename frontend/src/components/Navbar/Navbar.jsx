import { Link } from 'react-router-dom'
import Button from '../Button/Button'
import styles from './Navbar.module.css'

const Navbar = () => {
  return (
    <nav className={styles.navbar}>
      <div className={styles.container}>
        <Link to="/" className={styles.logo}>
          <span className={styles.logoText}>IntelliPay</span>
        </Link>
        <div className={styles.navLinks}>
          <Link to="/login">
            <Button variant="outline" size="medium">
              Login
            </Button>
          </Link>
          <Link to="/signup">
            <Button variant="primary" size="medium">
              Sign Up
            </Button>
          </Link>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
