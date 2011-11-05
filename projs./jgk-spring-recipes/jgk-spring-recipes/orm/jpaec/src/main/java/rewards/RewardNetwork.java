package rewards;

public interface RewardNetwork {

	/**
	 * Reward an account for dining.
	 * 
	 * For a dining to be eligible for reward: - It must have been paid for by a registered credit card of a valid
	 * member account in the network. - It must have taken place at a restaurant participating in the network.
	 * 
	 * @param dining a charge made to a credit card for dining at a restaurant
	 * @return confirmation of the reward
	 */
	public RewardConfirmation rewardAccountFor(Dining dining);
}