package agent;

import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Learning.Direction;
import Learning.LearningParameters;
import Memory.Experience;
import Memory.Location;
import Memory.SpatialExperienceMemory;

public class Controller {

	private final Logger logger = LoggerFactory.getLogger(Controller.class);
	private Agent agent;
	
	public void init(double learningRate, double gamma, double epsilon) {
		LearningParameters params = new LearningParameters(learningRate, gamma, epsilon);
		agent = new Agent(params);
		logger.info("Starting new agent " + agent + " with " + params.toString());
	}
	
	public Location getLocation() {
		return agent.whereAmI();
	}

	public Direction getNextAction(Set<Direction> directions) {
		return agent.selectNextLocation(directions);
	}
	
	public void takeNextAction(int reward) {
		agent.takeNextAction(new Experience(reward));
	}
	
	public void resetLocation() {
		agent.resetLocation();
	}
	
	public SpatialExperienceMemory getMemory() {
		return agent.getSpatialExperienceMemory();
	}
	
	public ArrayList<Location> getOptimalPath() {
		return agent.getOptimalPath();
	}
	
	public boolean isGoalReached() {
		return agent.goalReached();
	}
}
