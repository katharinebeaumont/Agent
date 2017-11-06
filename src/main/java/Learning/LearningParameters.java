package Learning;

public class LearningParameters {
	
	private double learningRate;
    private double gamma;
    private double epsilon;
    
	public LearningParameters(double learningRate, double gamma, double epsilon) {
		this.learningRate = learningRate;
		this.gamma = gamma;
		this.epsilon = epsilon;
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	public double getGamma() {
		return gamma;
	}
	public double getEpsilon() {
		return epsilon;
	}
	
	public String toString() {
		return "Params contain Alpha: " + learningRate + ", Gamma: " 
				+ gamma + ", Epsilon: " + epsilon;
	}
}
