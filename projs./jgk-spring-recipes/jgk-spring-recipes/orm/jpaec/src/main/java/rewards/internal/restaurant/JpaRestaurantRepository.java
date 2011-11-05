package rewards.internal.restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Finds restaurants using the JPA API.
 */
@Repository
public class JpaRestaurantRepository implements RestaurantRepository {

	private EntityManager entityManager;
	
	@PersistenceContext
	void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Restaurant findByMerchantNumber(String merchantNumber) {
		return (Restaurant) entityManager.createQuery(
				"select r from Restaurant r where r.number = :merchantNumber")
				.setParameter("merchantNumber", merchantNumber)
				.getSingleResult();
	}
}
