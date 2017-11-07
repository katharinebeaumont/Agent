package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Learning.DecisionEngine;
import Learning.Direction;
import Learning.Goal;
import Learning.LearningParameters;
import Memory.Experience;
import Memory.Location;
import Memory.Memory;
import Memory.SpatialExperienceMemory;

/**
 * Memory:
 * Q(S(t), A(t)) ← Q(S(t), A(t)) + α [ R(t+1) + γ max Q(S(t+1), a) − Q(S(t), A(t)) ].
 * 
 * 
 * @author katharine
 * I know about:
 *  - My memory of learned rewards and possible actions
 *  - My learning parameters
 *  
 * I can ask the environment about:
 *  - The surrounding open rooms.
 *  - If there is a reward in this room.
 *   and use them to make decisions about which room to go in next.
 * 
 * I control:
 *  - My movements 
 *  - What actions I take
 * 
 * My constraints:
 *  - I need to start from the same starting point each time (in the environment) for training,
 *   otherwise nothing makes sense. My memory of steps is all relative to where I started.
 *  
 */
public class Agent {
    
	private static final Logger logger = LoggerFactory.getLogger(Agent.class);
	
    private final Memory memory;
    private final DecisionEngine decisionEngine;
    private boolean goalReached = false;
    
    public Agent(LearningParameters learningParameters) {
        this.memory = new Memory();
        this.decisionEngine = new DecisionEngine(learningParameters, new Goal());
    }
    
    public HashMap<Location, Experience> getMemoryOfLocation(Location location) {
    		return memory.getActionRewardsForLocation(location);
    }
    
    public Location whereAmI() {
    		return memory.whereAmINow();
    }
    
    /*
     * For epsilon percent of the time, explore,
     * otherwise pick the move that will maximise rewards
     */
    public Direction selectNextLocation(Set<Direction> directions) {
    		//TODO implement me!
    		throw new RuntimeException("IMPLEMENT ME");
    }
    
    /*
     * Q(S(t), A(t)) ← Q(S(t), A(t)) + α [ R(t+1) + γ max Q(S(t+1), a) − Q(S(t), A(t)) ].
     * 
     * Experience experience: This is the reward I will get from the Environment for taking A(t), R(t+1)
     */
    public void takeNextAction(Experience experience) {
    	
    		//I need to:
    	    // 1. Get the current state S(t) and the next direction chosen A(t)
    	    // 2. Get R(t+1) (the reward given by the environment)
    	    // 3. Remember the maximum reward I *could* get, if I 
    	    //     - went into state A(t) (so S(t+1))
   	    //     - then took another step.
    		//  In other words, once I take A(t), what's the reward from 
    	    //  the best step (a) I could take after that? 
        //  This is max Q(S(t+1), a)
    	    // 4. Use these to implement decisionEngine.calculateRewardIncrement(), which 
        //  will work out
    	    //    rewardIncrement = α [ R(t+1) + γ max Q(S(t+1), a) − Q(S(t), A(t)) ]
    	    // 5. Update memory, which will do
    	    //    Q(S(t), A(t)) ← Q(S(t), A(t)) + rewardIncrement.
    	    // 6. Check if I've reached my goal, and update goalReached
    	
    		Direction nextStep = memory.getNextDirectionChosen();
    		// S(t)
    		// A(t)
    		// R(t+1)
    		
    		// I need to know what the existing memory is of moving from where I am now, 
    		//  to the next location
    		// Q(S(t), A(t))
    		
    		// What is the next best reward I could get, after I take the action? 
    		//  This is to get max Q(S(t+1), a)
    		
    		// This is 
    		// + α [ R(t+1) + γ max Q(S(t+1), a) − Q(S(t), A(t)) ].
    	
    		// Adds:
    		//  Q(S(t), A(t)) ← Q(S(t), A(t)) + rewardIncrement.
    		
    		// Move
    		
    		// Did I reach my goal state?
    		throw new RuntimeException("IMPLEMENT ME");
    }
    
    public void resetLocation() {
    		goalReached = false;
    		memory.flush();
    }
    
    public boolean goalReached() {
    		return goalReached;
    }

	public SpatialExperienceMemory getSpatialExperienceMemory() {
		return memory.getSpatialMemory();
	}

	public ArrayList<Location> getOptimalPath() {
		ArrayList<Location> optimalPath = new ArrayList<Location>();
		
		Location currentLocation = new Location(0,0);
		optimalPath.add(currentLocation);
		
		double reward = 0;
		boolean failed = false;
		while (!decisionEngine.haveReachedGoal(reward) || !failed) {
		
			HashMap<Location, Experience> nextLocations = memory.getActionRewardsForLocation(currentLocation);
			logger.debug("Moved to " + currentLocation);
			logger.debug("Possible other locations: " + nextLocations.size());
			if (nextLocations.isEmpty() || optimalPath.size() >= decisionEngine.STEP_LIMIT) {
				logger.error("I can't continue! Taken " +  optimalPath.size() + " steps.");
				failed = true;
				break;
			}
			
			Location nextAction = null;  //TODO: this needs doing!!!!!!
			
			optimalPath.add(nextAction);
			reward = nextLocations.get(nextAction).getReward();
			logger.debug("Reward from next action is " + reward);
			currentLocation = nextAction;
			throw new RuntimeException("IMPLEMENT ME");
			
		} 
		
		return optimalPath;
	}
    
}
