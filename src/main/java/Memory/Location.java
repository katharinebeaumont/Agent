package Memory;

import java.util.HashSet;
import java.util.Set;

import Learning.Direction;

/**
 * 
 * Agent's concept of a location: agent's internal map of the world
 * 
 * Assumptions:
 *  - Environment situated in is a) a Grid world
 *                               b) no bigger than 100x100 
 * 
 * Agent always starts at: 0,0
 * 
 * Constraint:
 *  - Bounded by range [-100, 100] in each direction. [why? For hashcode]
 *    If this constraint is changed, hashcode must be changed.
 * 
 * 
 *  Example grid:
 *                   col (x)
 *           -2      -1      0       1     2  
 *      -2  -2,-2   -1,-2   0,-2   1,-2   2,-2
 *  row -1  -2,-1   -1,-1   0,-1   1,-1   2,-1
 *  (y)  0  -2, 0   -1, 0   0, 0   1, 0   2, 0
 *       1  -2, 1   -1, 1   0, 1   1, 1   2, 1
 *       2  -2, 2   -1, 2   0, 2   1, 2   2, 2
 *       
 * 
 */
public class Location implements Comparable<Location> {
	
	//TODO: change to one int. Don't need 2 numbers.
	private int col;
	private int row;
	
	public Location(int col, int row) {
		//Like x,y pair
		this.col = col;
		this.row = row;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location loc = (Location)o;
			return loc.row == row && loc.col == col;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "col (x): " + col + ", row (y): " + row;
	}
	
	public Location getAdjacentLocationFromDirection(Direction direction) {
		int new_row = row;
		int new_col = col;
		switch(direction) {
			case UP:
				new_row--;
				break;
			case DOWN:
				new_row++;
				break;
			case LEFT:
				new_col--;
				break;
			case RIGHT:
				new_col++;
				break;
			default:
				//Do nothing
				break;
		}
		return new Location(new_col, new_row);
	}
	
	public Set<Location> getAdjacentLocationsForDirections(Set<Direction> directions) {
		HashSet<Location> surroundings = new HashSet<Location>();
		for (Direction d: directions) {
			surroundings.add(getAdjacentLocationFromDirection(d));
		}
			
		return surroundings;
	}
	
	public Direction getDirectionOfAdjacentLocation(Location nextLocation) {
		//Row is y axis
		int nextRow = nextLocation.getRow();
		//Col is x axis
		int nextCol = nextLocation.getCol();
		
		//Same row, so left or right
		if (row == nextRow) {
			if (col < nextCol) {
				return Direction.RIGHT;
			}
			if (col > nextCol) {
				return Direction.LEFT;
			}
		} 
		//Same column, so up or down
		if (col == nextCol) {
			if (row < nextRow) {
				return Direction.DOWN;
			}
			if (row > nextRow) {
				return Direction.UP;
			}
		}
		
		//Something has gone wrong
		return null;
		
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	/*
	 * DANGER - assumes agent is situated in a 100x100 grid world.
	 * 
	 * Ensure that a unique hashcode is generated for each coordinate in 
	 * a 201 x 201 grid, based on the coordinate, so if a new object is created with
	 * the same coordinate as an existing object then
	 * it will be treated as the same key in a hashmap.
	 * 
	 * Concept: 
	 * Each cell is numbered, from 1 to 201x201, left to right, down the grid.
	 * There are 40,401 cells.
	 * The upper left cell of grid world (-100,-100) is 0.
	 * So the top right hand cell of the world is 200.
	 * The bottom left cell of the world is 40,200
	 * The bottom right cell (end of) the world is 40,400
	 */
	@Override
	public int hashCode() {
		//If top left is 0,0 then it would be row (x) x 201, + column
		//But we need to shift everything by 100 first.
		return (201*(row+100)) + (col+100);
	}

	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
