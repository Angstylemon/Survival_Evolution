package template;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Agent extends PApplet {
	protected final PApplet parent;
	
	private static int a = 10;
			
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float size;
		
	protected float fitness;
	protected float health;
	
	protected String status;
	
	public Agent(PApplet p, float x, float y, float size) {
		parent = p;
		status = "alive";
		pos = new PVector(x, y);
		vel = new PVector(0, 0);
		acc = new PVector(0, 0);
	}
	
	public Agent(PApplet p, float x, float y) {
		this(p, x, y, 0);
	}
	
	protected static int a() {
		return a;
	}
	
	public float evaluate() {
		return fitness;
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
	
	public abstract Agent reproduce(Agent b, double mutation_rate);
	
	public void interact(List<? extends Object> l) {}
	
	public boolean overlap(StationaryBlock b) {
		if (pos.x > b.pos.x - b.size && pos.x < b.pos.x + b.size && pos.y > b.pos.y - b.size && pos.y < b.pos.y + b.size) {
			overlapHook(b);
			return true;
		}
		
		return false;
	}
	
	protected void overlapHook(StationaryBlock b) {}
	
	public String status() {
		return status;
	}
	
	public boolean check(String check) {return false;}
}
