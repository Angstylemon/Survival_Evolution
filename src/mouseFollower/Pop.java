package mouseFollower;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;
import template.Agent;
import template.Population;

public class Pop extends Population {
	
	private static int population_size = 30;
	private static float mutation_rate = (float)0.02;
	
	private double reproduction_rate = 0.005;
	
	private int lifetime = 100;
	private int frameCount;
	
	private int survivalTime = 0;
	
	private static double maxVisibility = 300.0;
	private static double maxAttraction = 2.0;
	
	public Pop(template.Simulator s) {	
		super(s, population_size, mutation_rate);	
	}
	
	@Override
	protected void createPopulation() {
		for (int i = 0; i < population_size; i++) {
			int x = (int)(Math.random()*Sim.width);
			int y = (int)(Math.random()*Sim.height);
			
			float greenAttraction = (float)(Math.random()*maxAttraction - 1);
			float redAttraction = (float)(Math.random()*maxAttraction - 1);
			float greenVisibility = (float)(Math.random()*maxVisibility);
			float redVisibility = (float)(Math.random()*maxVisibility);
			
			float maxSpeed = (float)(Math.random()*10);
			float maxForce = (float)(1.5/(maxSpeed));
			
			int[] color = {(int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)};
			
			DNA newDNA = new DNA(greenAttraction, redAttraction, greenVisibility, redVisibility, maxSpeed, maxForce, color);
			
			population.add(new Follower(parent, x, y, newDNA));
			
		}		
	}

	@Override
	public boolean execute() {
		frameCount++;
		survivalTime++;
		
		parent.fill(255, 255, 255);
		parent.textAlign(LEFT);
		parent.textSize(40);
		parent.text("Generation: " + generation_count, 20, 40); 
		
		parent.textAlign(RIGHT);
		parent.text("Lifetime: " + (int)survivalTime/60, Sim.width-20, 40);
		
		int aliveCount = 0;
		
		for (int i = population.size()-1; i >= 0; i--) {
			if (!population.get(i).status().equals("dead")) {
				population.get(i).move();
				population.get(i).display();
				
//				double randomReproduce = (Math.random());
//				if (randomReproduce < reproduction_rate && population.size() < population_size) {
//					reproduce();
//				}
				
				aliveCount++;
			}
		}
		
		if (frameCount >= lifetime) {
			frameCount = 0;
			if (aliveCount < population_size/2) {
				reproduce();
				generation_count++;
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void reproduce() {
		List<Agent> newPopulation = new ArrayList<Agent>();
		
		for (int i = 0; i < population_size; i++) {
			Agent parent = getRandomAgent();
			newPopulation.add(parent.reproduce(parent, mutation_rate));
		}
		
		population = newPopulation;
		survivalTime = 0;
	}
	
	@Override
	public void interact(List<? extends Object> l) {
		if (l.get(0) instanceof Food || l.get(0) instanceof Poison) {
			for (Agent a : population) {
				a.interact(l);
			}
		}
	}
}
