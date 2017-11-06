package Memory;

import java.util.HashMap;

/**
 * Wrapper class
 * 	What happens when I move from a state (location) into the next
 *	 state (location) [which is an action]?
 *  This represents a Q table.
 *  which maps the rewards for taking an action, a
 *  from state, s
 *  so:
 *               action (a)
 *             ___________
 *            |           |
 *  state (s) |  reward   |
 *             ___________
 */
public class SpatialExperienceMemory {
	
	private HashMap<Location, HashMap<Location, Experience>> memory;
	
	public SpatialExperienceMemory() {
		memory = new HashMap<Location, HashMap<Location, Experience>>();
	}

	public void put(Location state, Location action, double newQValue) {
		HashMap<Location, Experience> actionsForState = memory.get(state);
		if (actionsForState == null) {
			actionsForState = new HashMap<Location, Experience>();
		}
		
		if (actionsForState.containsKey(action)) {
			Experience exp = actionsForState.get(action);
			exp.modifyExperience(newQValue);
		} else {
			Experience exp = new Experience(newQValue);
			actionsForState.put(action, exp);
		}
		memory.put(state, actionsForState);
	}

	public HashMap<Location, Experience> getRewardsForLocation(Location location) {
		if (memory.containsKey(location)) {
			return memory.get(location);
		} 
		return new HashMap<Location, Experience>();
	}

	public void move(Location newLocation) {
		if (!memory.containsKey(newLocation)) {
			memory.put(newLocation, new HashMap<Location, Experience>());
		}
	}
	
	public HashMap<Location, HashMap<Location, Experience>> getMemory() {
		return memory;
	}
	
	public int size() {
		return memory.size();
	}
}
