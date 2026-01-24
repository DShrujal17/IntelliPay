import Card from '../Card/Card'
import styles from './FeatureCard.module.css'

const FeatureCard = ({ icon, title, description }) => {
  return (
    <Card className={styles.featureCard}>
      <div className={styles.icon}>{icon}</div>
      <h3 className={styles.title}>{title}</h3>
      <p className={styles.description}>{description}</p>
    </Card>
  )
}

export default FeatureCard
