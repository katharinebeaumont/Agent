package Learning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Memory.Experience;

/**
 * 
 * @author you
 *
 */
public class DecisionEngine {
	
	public static final int STEP_LIMIT = 1000;
	
	private Logger log = LoggerFactory.getLogger(DecisionEngine.class);
	private LearningParameters learningParameters;
    private final Goal goal;
	
	public DecisionEngine(LearningParameters learningParameters, Goal goal) {
		this.learningParameters = learningParameters;
		this.goal = goal;
	}
	
	
	public boolean haveReachedGoal(Experience e) {
		 return haveReachedGoal(e.getReward());
	}
	
	public boolean haveReachedGoal(double d) {
		return goal.isGoalReached(d);
	}
}
