package template;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class MovingBlock {
	private static int[] DEFAULT_COLOR = {255, 255, 255};
	
	private PApplet parent;
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	public float size;
	private String type;
	private int[] color;
	
	public MovingBlock(PApplet p, PVector position) {
		this(p, position, 0, DEFAULT_COLOR);
	}
	
	public MovingBlock(PApplet p, PVector position, int[] c) {
		this(p, position, 0, c);
	}
	
	public MovingBlock(PApplet p, PVector position, float objectSize) {
		this(p, position, objectSize, DEFAULT_COLOR);
	}
	
	public MovingBlock(PApplet p, PVector position, float objectSize, int[] c) {
		parent = p;
		pos = position;
		size = objectSize;
		color = c;
	}
	
	public void move() {
		vel.x += acc.x;
		vel.y += acc.y;
		pos.x += vel.x;
		pos.y += vel.y;
		
		acc.x *= 0;
		acc.y *= 0;
		
		moveHook();
	}
	
	protected void moveHook() {}
	
	public void applyForce(PVector p) {
		acc.add(p);
	}
	
	public abstract void display();
}
