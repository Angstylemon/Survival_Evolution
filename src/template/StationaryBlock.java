package template;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class StationaryBlock extends PApplet {
	private static int[] DEFAULT_COLOR = {255, 255, 255};
	
	private PApplet parent;
	public PVector pos;
	public int size;
	private String type;
	private int[] color;
	
	public StationaryBlock(PApplet p, String objectType, PVector position, int objectSize) {
		this(p, objectType, position, objectSize, DEFAULT_COLOR);
	}
	
	public StationaryBlock(PApplet p, String objectType, PVector position, int objectSize, int[] c) {
		parent = p;
		type = objectType;
		pos = position;
		size = objectSize;
		color = c;
	}
	
	public void display() {
		parent.stroke(0);
		parent.fill(color[0], color[1], color[2]);

		if (type.equals("circle")) {
			parent.circle(pos.x, pos.y, size);
			
		} else if (type.equals("square")) {
			parent.square(pos.x, pos.y, size);
			
		}
	}
	
	public abstract List<? extends Object> getProperty(String s);
}
