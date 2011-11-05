package rewards.internal.account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Finds account objects using the JPA API.
 */
@Repository
public class JpaAccountRepository implements AccountRepository {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Account findByCreditCard(String creditCardNumber) {
		return (Account) entityManager
			.createQuery("select a from Account a  where a.creditCardNumber = ?")
			.setParameter(1, creditCardNumber)
			.getSingleResult();
	}
}
