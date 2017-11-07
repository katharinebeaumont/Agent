package Memory;

import java.util.HashMap;

import Learning.Direction;

/**
 * 
 * 1. Short temporal term memory: Where I am now, where have I been at each discrete time step
 * 
 * 2. Long term spatial/experiential memory: What happens when I move from one location (state) into 
 *  another (the action).
 *  
 */
public class Memory {

	//Where am I at each discrete time unit?
	private HashMap<Integer, Location> temporal_memory;
	
	//What happens when I move from a state (location) into the next
	// state (location) [which is an action]?
	private SpatialExperienceMemory spatial_experience_memory;
	
	private Direction nextDirectionChosen;
	
	public Memory() {
		initialiseTemporalMemory();
		spatial_experience_memory = new SpatialExperienceMemory(); 
	}
	
	public Location whereAmINow() {
		if (temporal_memory.isEmpty()) {
			return null;
		} else {
			int size = temporal_memory.size();
			return temporal_memory.get((size-1));
		}
	}
	
	public void move(Location newLocation) {
		int size = temporal_memory.size();
		temporal_memory.put(size, newLocation);
		spatial_experience_memory.move(newLocation);
	}
	
	public HashMap<Location, Experience> getNextActionRewards() {
		Location currentLocation = whereAmINow();
		return getActionRewardsForLocation(currentLocation);
	}
	
	public  HashMap<Location, Experience> getActionRewardsForLocation(Location location) {
		return spatial_experience_memory.getRewardsForLocation(location);
	}

	public void update(Location state, Location action, double newQValue) {
		spatial_experience_memory.put(state, action, newQValue);
	}
	
	/*
	 * New episode: add movement details from temporal to episodic memory
	 * Wipe temporal memory (what steps did I take to get here)
	 * Retain spatial experience memory (what rooms have what)
	 */
	public void flush() {
		initialiseTemporalMemory();
	}
	
	private void initialiseTemporalMemory() {
		temporal_memory = new HashMap<Integer, Location>();
		temporal_memory.put(0, new Location(0,0));
	}
	/*
	 * Getters
	 */
	public HashMap<Integer, Location> getTemporalMemory() {
		return temporal_memory;
	}

	public SpatialExperienceMemory getSpatialMemory() {
		return spatial_experience_memory;
	}
	
	public Direction getNextDirectionChosen() {
		return nextDirectionChosen;
	}

	/*
	 * Setter
	 */
	public void setNextDirectionChosen(Direction nextDirectionChosen) {
		this.nextDirectionChosen = nextDirectionChosen;
	}


}
