package mouseFollower;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Poison extends template.StationaryBlock {
	private float healthChange = (float)-0.5;
	
	public Poison(PApplet p, String objectType, PVector position, int objectSize) {
		super(p, objectType, position, objectSize);
	}
	
	public Poison(PApplet p, String objectType, PVector position, int objectSize, int[] c) {
		super(p, objectType, position, objectSize, c);
	}
	
	public List<? extends Object> getProperty(String s) {
		if (s.equals("healthChange")) {
			List<Float> returnList = new ArrayList<Float>();
			returnList.add(healthChange);
			return returnList;
		} else {
			return null;
		}
	}
}
