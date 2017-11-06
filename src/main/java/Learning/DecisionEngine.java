package Learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Memory.Experience;
import Memory.Location;

/**
 * Needs access to learning parameters
 * TODO: write comments for methods
 * TODO: write tests
 * 
 * @author katharine
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
	
	public boolean shouldExplore() {
		double useMemory = Math.random();
        if (useMemory < learningParameters.getEpsilon()) {
        		return true;
        }
        return false;
	}
	
	public Location pickRandomAction(Set<Location> possibleActions) {
		//Possibly the wrong way to randomly select, as Set makes no
		// guarantees as to order anyway. But would be good to ensure
		// 'more' 'randomness'
		ArrayList<Location> locations = new ArrayList<Location>();
		locations.addAll(possibleActions);
        int options = possibleActions.size();
        int choice = (int)(Math.random() * options);
        return locations.get(choice);
	}
	
	public Location pickBestActionOrRandom(HashMap<Location, Experience> nextActionRewardsFromMemory, 
			Set<Location> possibleActionsFromEnvironment) {
        
		log.debug("Picking my next action.");
		//Have I done this before?
		if (nextActionRewardsFromMemory.isEmpty()) {
			//Nope. So look to environment 
			log.debug("I've never been here before, so picking a random direction from the environment");
			return pickRandomAction(possibleActionsFromEnvironment);
		}
		
		//What do I remember about the next possible actions? 
		Set<Location> actions = nextActionRewardsFromMemory.keySet();
		
		//There might be more than one best action
		Set<Location> bestActions = new HashSet<Location>();
		double highestReward = 0;
        for (Location action: actions) {
        	
        		//We want to know what the reward is for moving from the current location
        		// to the state
        		double rewardMemory = nextActionRewardsFromMemory.get(action).getReward();
          
            if (rewardMemory > highestReward) {
                //Clear out any previous candidates for best action
                highestReward = rewardMemory;
                bestActions.clear();
                log.debug("This is the best action, so thinking of moving to " + action);
                bestActions.add(action);
            }
            if (rewardMemory == highestReward) {
                //This means that bestActions could just contain zero reward actions,
                // in which case we're behaving the same as pickRandomAction().
                bestActions.add(action);
            }
        }
        return pickRandomAction(bestActions);
    }

    /*
     * New Q Value is:
     *    Q(S(t), A(t)) ← Q(S(t), A(t)) + α [ R(t+1) + γ max Q(S(t+1), a) − Q(S(t), A(t)) ].
     * 
     * This is returning:
     *    + α [ R(t+1) + γ max Q(S(t+1), a) − Q(S(t), A(t)) ]
     */
	public double calculateRewardIncrement(double currentMemoryOfReward, double rewardFromEnvironment, 
			double maxFutureRewardFromMemory) {
		double alpha = learningParameters.getLearningRate();
		double gamma = learningParameters.getGamma();
		
		double discountedMaxFutureReward = (gamma*(maxFutureRewardFromMemory));
		double memoryChange = (alpha*(rewardFromEnvironment +  discountedMaxFutureReward - currentMemoryOfReward));
		double newRewardIncrement = memoryChange;
		return newRewardIncrement;
	}
	
	public boolean haveReachedGoal(Experience e) {
		 return haveReachedGoal(e.getReward());
	}
	
	public boolean haveReachedGoal(double d) {
		return goal.isGoalReached(d);
	}
}
