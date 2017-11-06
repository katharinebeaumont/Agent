package Memory;

public class Experience {
	private double reward;
	
	public Experience(double reward) {
		this.reward = reward;
	}
	
	public void enhance(Experience newExperience) {
		modifyExperience(newExperience.getReward());
	}
	
	public double getReward() {
		return reward;
	}
	
	public void modifyExperience(double newReward) {
		reward += newReward;
	}
}
