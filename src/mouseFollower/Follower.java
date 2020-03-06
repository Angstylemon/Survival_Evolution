package mouseFollower;

import java.awt.Color;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import template.Agent;
import template.StationaryBlock;

public class Follower extends template.Agent {
	private static float ageing = (float)0.005;
	
	private DNA dna;

	public Follower(PApplet s, float x, float y) {
		super(s, x, y);
	}
	
	public Follower(PApplet s, float x, float y, DNA d) {
		super(s, x, y);
		dna = d;
		
		initialise();
	}
	
	private void initialise() {
		size = 20;
		fitness = 0;
		health = 1;
		status = "alive";
		
		float xVel = (float)(Math.random()*dna.maxSpeed()*2 - dna.maxSpeed());
		float yVel = (float)(Math.random()*dna.maxSpeed()*2 - dna.maxSpeed());
		PVector initialVelocity = new PVector(xVel, yVel);
		vel = initialVelocity;
	}
	
	private void initialiseVelocity() {
		
	}
	
	@Override
	public void move() {
		outOfBounds();
		
		vel.x += acc.x;
		vel.y += acc.y;
		vel.limit(dna.maxSpeed());
		
		pos.x += vel.x;
		pos.y += vel.y;
		
		acc.x *= 0;
		acc.y *= 0;
		
		health -= ageing;
		fitness++;
		
		if (health <= 0) {
			status = "dead";
		}
	}
	
	private void outOfBounds() {
		PVector desired = null;		
		int d = 0;
		
//		if (pos.x < d) {
//			desired = new PVector(dna.maxSpeed(), vel.y);
//		} else if (pos.x > Sim.width - d) {
//			desired = new PVector(-dna.maxSpeed(), vel.y);
//		}
//		
//		if (pos.y < d) {
//			desired = new PVector(vel.x, dna.maxSpeed());
//		} else if (pos.y > Sim.height - d) {
//			desired = new PVector(vel.x, -dna.maxSpeed());
//		}
//		
//		if (desired != null) {
//			desired.normalize();
//			desired.mult(dna.maxSpeed());
//			PVector steer = PVector.sub(desired, vel);
//			steer.limit(dna.maxForce());
//			applyForce(steer);
//		}
		
		
		if (pos.x + size < d) {
			pos.x = Sim.width + size;
		} else if (pos.x - size > Sim.width) {
			pos.x = 0 - size;
		}
		
		if (pos.y + size < d) {
			pos.y = Sim.height + size;
		} else if (pos.y - size > Sim.height) {
			pos.y = 0 - size;
		}
	}
	
	protected void seek(StationaryBlock b) {
		
		PVector desiredVel = PVector.sub(b.pos, pos);
		desiredVel.limit(dna.maxSpeed());
		
		PVector steer = PVector.sub(desiredVel, vel);
		
		steer.limit(dna.maxForce());
		
		float multiplier = 1;
		if (b instanceof Food) {
			multiplier = dna.greenAttraction();
		} else if (b instanceof Poison) {
			multiplier = dna.redAttraction(); 
		}
		
		steer.mult(multiplier);
		applyForce(steer);
	}

	@Override
	public float evaluate() {
		if (fitness < 0) {
			return 0;
		}
		
		if (status.equals("dead")) {
			return fitness/10;
		} else {
			return fitness;
		}
	}

	@Override
	public Agent reproduce(Agent b, double mutation_rate) {
		float ga = dna.greenAttraction();
		float ra = dna.redAttraction();
		float gv = dna.greenVisibility();
		float rv = dna.redVisibility();
		float ms = dna.maxSpeed();
		float mf = dna.maxForce();
		int[] c = dna.color();
		
		double mutate = Math.random();
		if (mutate < mutation_rate) {
			ga = (float)(Math.random()*2 - 1);
			c[1] = (int)(Math.random()*255);
		}
		
		mutate = Math.random();
		if (mutate < mutation_rate) {
			ra = (float)(Math.random()*2 - 1);
			c[0] = (int)(Math.random()*255);
		}
		
		mutate = Math.random();
		if (mutate < mutation_rate) {
			gv = (float)(Math.random()*200 + 5);
			c[1] = (int)(Math.random()*255);
		}
		
		mutate = Math.random();
		if (mutate < mutation_rate) {
			rv = (float)(Math.random()*200 + 5);
			c[0] = (int)(Math.random()*255);
		}
		
		mutate = Math.random();
		if (mutate < mutation_rate) {
			ms = (float)(Math.random()*10);
			mf = (float)(1.5/(ms));
			c[2] = (int)(Math.random()*255);
		}
		
		DNA newDNA = new DNA(ga, ra, gv, rv, ms, mf, c);
		
		int x = (int)(Math.random()*Sim.width);
		int y = (int)(Math.random()*Sim.height);
		
		return new Follower(parent, x, y, newDNA);
	}
	
	public void interact(List<? extends Object> list) {
		if (list.get(0) instanceof Food || list.get(0) instanceof Poison) {	
			float visibility;
			
			if (list.get(0) instanceof Food) {
				visibility = dna.greenVisibility();
			} else {
				visibility = dna.redVisibility();
			}
			
			float leastDist = 10000000;
			StationaryBlock closestItem = null;
		
			for (int i = 0; i < list.size(); i++) {

				StationaryBlock b = (StationaryBlock)list.get(i);
				float dist = b.pos.dist(pos);

				if (dist < leastDist && dist <= visibility) {
					closestItem = b;
					leastDist = dist;
				}
			}
			
			if (closestItem != null) {
				seek(closestItem);
			}
		}
	}
	
	protected void overlapHook(StationaryBlock b) {
		if (b instanceof Food || b instanceof Poison) {
			List<Float> values = (List<Float>)b.getProperty("healthChange");
			health += values.get(0);
			fitness += values.get(0)*20;
		}
	}
	
	@Override
	public void display() {
		parent.noStroke();
		parent.fill(dna.color()[0], dna.color()[1], dna.color()[2], 255*health);
		parent.circle(pos.x, pos.y, size);
//		
//		parent.stroke(0, 255, 0);
//		parent.line(pos.x, pos.y, pos.x + 20, pos.y-dna.greenAttraction()*100);
//		
//		parent.stroke(255, 0, 0);
//		parent.line(pos.x, pos.y, pos.x - 20, pos.y-dna.redAttraction()*100);
//		
//		parent.noFill();
//		parent.stroke(0, 255, 0);
//		parent.circle(pos.x, pos.y, dna.greenVisibility());
//		parent.stroke(255, 0, 0);
//		parent.circle(pos.x, pos.y, dna.redVisibility());
		
	}

}
