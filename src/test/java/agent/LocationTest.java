package agent;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import Learning.Direction;
import Memory.Location;

public class LocationTest {
	
	@Test
	public void testHashCodeForStartingLocation() {
		//Arrange
		Location loc = new Location(0,0);
		int expectedHashCode = 20200;
		//Act
		int hashcode = loc.hashCode();
		
		//Assert
		assertEquals(expectedHashCode, hashcode);
	}
	
	@Test
	public void testHashCodeForTopLeftLocation() {
		//Arrange
		Location loc = new Location(-100,-100);
		int expectedHashCode = 0;
		//Act
		int hashcode = loc.hashCode();
		
		//Assert
		assertEquals(expectedHashCode, hashcode);
	}
	
	@Test
	public void testHashCodeForTopRightLocation() {
		//Arrange
		Location loc = new Location(100,-100);
		int expectedHashCode = 200;
		//Act
		int hashcode = loc.hashCode();
		
		//Assert
		assertEquals(expectedHashCode, hashcode);
	}
	
	@Test
	public void testHashCodeForBottomLeftLocation() {
		//Arrange
		Location loc = new Location(-100,100);
		int expectedHashCode = 40200;
		//Act
		int hashcode = loc.hashCode();
		
		//Assert
		assertEquals(expectedHashCode, hashcode);
	}
	
	@Test
	public void testHashCodeForBottomRightLocation() {
		//Arrange
		Location loc = new Location(100,100);
		int expectedHashCode = 40400;
		//Act
		int hashcode = loc.hashCode();
		
		//Assert
		assertEquals(expectedHashCode, hashcode);
	}
	
	@Test
	public void testUniqueHashCodeForAllLocations() {
		ArrayList<Integer> hashcodes = new ArrayList<Integer>();
		for (int i=0; i<=200; i++) {
			for (int j=0; j<=200; j++) {
				Location loc = new Location(i,j);
				int hashcode = loc.hashCode();
				if (hashcodes.contains(hashcode)) {
					System.out.println("size of hashcodes is " + hashcodes.size());
					System.out.println("Location is " + loc);
					fail("Already have a hashcode: " + hashcode);
					
				} else {
					hashcodes.add(hashcode);
				}
			}
		}
		assertEquals(40401, hashcodes.size());
	}
	
	
	@Test
	public void testGetSurroundingsUp() {
		//Arrange
		Set<Direction> directions = new HashSet<Direction>();
		directions.add(Direction.UP);
		
		Location loc = new Location(1,1);
		
		//Act
		Set<Location> locations = loc.getAdjacentLocationsForDirections(directions);
		assert(locations.contains(new Location(1,0)));
	}
	
	@Test
	public void testGetSurroundingsDown() {
		//Arrange
		Set<Direction> directions = new HashSet<Direction>();
		directions.add(Direction.DOWN);
		
		Location loc = new Location(1,1);
		
		//Act
		Set<Location> locations = loc.getAdjacentLocationsForDirections(directions);
		assert(locations.contains(new Location(1,2)));
	}
	
	@Test
	public void testGetSurroundingsRight() {
		//Arrange
		Set<Direction> directions = new HashSet<Direction>();
		directions.add(Direction.RIGHT);
		
		Location loc = new Location(1,1);
		
		//Act
		Set<Location> locations = loc.getAdjacentLocationsForDirections(directions);
		assert(locations.contains(new Location(2,1)));
	}
	
	@Test
	public void testGetSurroundingsLeft() {
		//Arrange
		Set<Direction> directions = new HashSet<Direction>();
		directions.add(Direction.LEFT);
		
		Location loc = new Location(1,1);
		
		//Act
		Set<Location> locations = loc.getAdjacentLocationsForDirections(directions);
		assert(locations.contains(new Location(0,1)));
	}
	
	@Test
	public void testGetDirectionLeft() {
		//Arrange
		Location startingLoc = new Location(1,1);
		Location nextLoc = new Location(0,1);
		
		//Act
		Direction d = startingLoc.getDirectionOfAdjacentLocation(nextLoc);
		assertEquals(Direction.LEFT, d);
	}
	
	@Test
	public void testGetDirectionRight() {
		//Arrange
		Location startingLoc = new Location(1,1);
		Location nextLoc = new Location(2,1);
		
		//Act
		Direction d = startingLoc.getDirectionOfAdjacentLocation(nextLoc);
		assertEquals(Direction.RIGHT, d);
	}
	
	@Test
	public void testGetDirectionUp() {
		//Arrange
		Location startingLoc = new Location(1,1);
		Location nextLoc = new Location(1,0);
		
		//Act
		Direction d = startingLoc.getDirectionOfAdjacentLocation(nextLoc);
		assertEquals(Direction.UP, d);
	}
	
	@Test
	public void testGetDirectionDown() {
		//Arrange
		Location startingLoc = new Location(1,1);
		Location nextLoc = new Location(1,2);
		
		//Act
		Direction d = startingLoc.getDirectionOfAdjacentLocation(nextLoc);
		assertEquals(Direction.DOWN, d);
	}
	
}
