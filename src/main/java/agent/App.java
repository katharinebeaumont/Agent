package agent;

import static spark.Spark.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Learning.Direction;
import Learning.LearningParameters;
import Memory.Experience;
import Memory.Location;
/**
 * Hello world!
 *
 */
public class App 
{
	private static final Controller agentController = new Controller();;
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	//Config
	private static final String NAME = "Ginny";
	private static final int PORT = 1234;
	private static final double LEARNING_RATE = 0.5;
	private static final double GAMMA = 0.5;	
	private static final double EPSILON = 0.1;
	
    public static void main( String[] args )
    {
    	
    		port(PORT);
    		staticFiles.location("/public"); 
    		
    		get("/init", (req, res) -> {
    			logger.info("Received initialisation request from " + req.host());
    			agentController.init(LEARNING_RATE, GAMMA, EPSILON);
	    		return "Let's do this!";
    		});
    		
    		get("/location", (req, res) -> {
    			Location loc = agentController.getLocation();
    			Gson gson = new Gson();
    			String json = gson.toJson(loc);
    			logger.debug("My location is: " + json);
    			return json;
    		});
    		
    		post("/chooseAction", (req, res) -> {
    			String request = req.body();
    			Gson gson = new Gson();
    			logger.debug("Options are: " + request);
    			Set<String> directionsStr = gson.fromJson(request, Set.class);
    			Set<Direction> directions = new HashSet<Direction>();
    			for (String s: directionsStr) {
    				directions.add(Direction.valueOf(s));
    			}
    			Direction nextAction = agentController.getNextAction(directions);
    			
    			String json = gson.toJson(nextAction);
    			logger.debug("I'm picking...: " + json);
    			return json;
    		});
    		
    		post("/takeAction", (req, res) -> {
    			String request = req.body();
    			Gson gson = new Gson();
    			logger.debug("Reward is: " + request);
    			int reward = gson.fromJson(request, Integer.class);
    			agentController.takeNextAction(reward);
    			return "I've moved";
    		});

    		get("/isGoalReached", (req, res) -> {
    			logger.debug("Being asked if I've reached my goal.");
    			if (agentController.isGoalReached()) {
    				logger.debug("I've reached my goal! Resetting.");
    				agentController.resetLocation();
    				return "Yes";
    			}
    			logger.debug("Haven't reached my goal.");
    			return "No";
    		});
    		
    		get("/memory", (req, res) -> {
    			
    			Type typeOfHashMap = new TypeToken<Map<Location, Map<Location, Experience>>>() { }.getType();
    			HashMap<Location, HashMap<Location, Experience>> memory = agentController.getMemory().getMemory();
    			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
    			
    			String jsonResponse = gson.toJson(memory, typeOfHashMap);
    			
    			logger.debug("Sending my memory: \n" + jsonResponse);
    			return jsonResponse;
    		});
    		
    		get("/optimalPath", (req, res) -> {
    			
    			Type typeOfSet = new TypeToken<ArrayList<Location>>() { }.getType();
    			ArrayList<Location> optimalPath = agentController.getOptimalPath();
    			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
    			
    			String jsonResponse = gson.toJson(optimalPath, typeOfSet);
    			logger.debug("Sending my optimal path: \n" + jsonResponse);
    			return jsonResponse;
    		});
    		
    		get("/hello", (req, res) -> NAME);
    }
}
