package rewards.internal.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import rewards.Dining;
import rewards.internal.account.Account;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * A restaurant establishment in the network. Like AppleBee's.
 * 
 * Restaurants calculate how much benefit may be awarded to an account for dining based on a availability policy and a
 * benefit percentage.
 */
@Entity
@Table(name="T_RESTAURANT")
public class Restaurant {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long entityId;
	
	@Column(name="MERCHANT_NUMBER")
	private String number;

	@Column(name="NAME")
	private String name;

	@Column(name="BENEFIT_PERCENTAGE")
	@Type(type="common.money.PercentageUserType")
	private Percentage benefitPercentage;

	@Column(name="BENEFIT_AVAILABILITY_POLICY")
	@Type(type="rewards.internal.restaurant.BenefitAvailabilityPolicyUserType")
	private BenefitAvailabilityPolicy benefitAvailabilityPolicy;

	@SuppressWarnings("unused")
	private Restaurant() {
	}

	/**
	 * Creates a new restaurant.
	 * @param number the restaurant's merchant number
	 * @param name the name of the restaurant
	 */
	public Restaurant(String number, String name) {
		this.number = number;
		this.name = name;
	}

	/**
	 * Sets the percentage benefit to be awarded for eligible dining transactions.
	 * @param benefitPercentage the benefit percentage
	 */
	public void setBenefitPercentage(Percentage benefitPercentage) {
		this.benefitPercentage = benefitPercentage;
	}

	/**
	 * Sets the policy that determines if a dining by an account at this restaurant is eligible for benefit.
	 * @param benefitAvailabilityPolicy the benefit availability policy
	 */
	public void setBenefitAvailabilityPolicy(BenefitAvailabilityPolicy benefitAvailabilityPolicy) {
		this.benefitAvailabilityPolicy = benefitAvailabilityPolicy;
	}

	/**
	 * Returns the name of this restaurant.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the merchant number of this restaurant.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Returns this restaurant's benefit percentage.
	 */
	public Percentage getBenefitPercentage() {
		return benefitPercentage;
	}

	/**
	 * Returns this restaurant's benefit availability policy.
	 */
	public BenefitAvailabilityPolicy getBenefitAvailabilityPolicy() {
		return benefitAvailabilityPolicy;
	}
	
	/** 
	 * Returns the id for this restaurant.
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Calculate the benefit eligible to this account for dining at this restaurant.
	 * @param account the account that dined at this restaurant
	 * @param dining a dining event that occurred
	 * @return the benefit amount eligible for reward
	 */
	public MonetaryAmount calculateBenefitFor(Account account, Dining dining) {
		if (benefitAvailabilityPolicy.isBenefitAvailableFor(account, dining)) {
			return dining.getAmount().multiplyBy(benefitPercentage);
		} else {
			return MonetaryAmount.zero();
		}
	}

	public String toString() {
		return "Number = '" + number + "', name = '" + name + "', benefitPercentage = " + benefitPercentage
				+ ", benefitAvailabilityPolicy = " + benefitAvailabilityPolicy;
	}
}