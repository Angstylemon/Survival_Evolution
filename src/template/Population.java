package template;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;

public abstract class Population extends PApplet {
	protected Simulator parent;
	
	protected int population_size;
	protected double mutation_rate;
	
	protected int generation_count;
	
	protected List<Agent> population = new ArrayList<Agent>();
	
	public Population(Simulator p, int s, double r) {
		parent = p;
		population_size = s;
		mutation_rate = r;
		generation_count = 0;
		createPopulation();
	}
	
	protected abstract void createPopulation();
	public abstract boolean execute();
	public abstract void reproduce();
	
	public void operation(String action){}
	
	protected Agent getRandomAgent() {
		List<Float> fitnessValues = new ArrayList<Float>();
		
		for (Agent a : population) {
			fitnessValues.add(a.evaluate());
		}
		float maxFitness = Collections.max(fitnessValues);
		
		List<Float> mappedFitnessValues = new ArrayList<Float>();
		float fitnessSum = 0;
		for (Float f : fitnessValues) {
			float fitness = map(f, 0, maxFitness, 0, 1);
			fitness = (float)(Math.pow(fitness, 1));
			mappedFitnessValues.add(fitness);
			fitnessSum += fitness;
		}
				
		float fitnessIndex = (float)(Math.random()*fitnessSum);
		int agentIndex = -1;
		
		while (fitnessIndex > 0) {
			agentIndex++;
			fitnessIndex -= mappedFitnessValues.get(agentIndex);
		}
		
		return population.get(agentIndex);
	}
	
	public void interact(List<? extends Object> l) {}
	
	public boolean overlap(StationaryBlock b) {
		for (Agent a : population) {
			if (a.overlap(b)) {
				return true;
			}
		}
		
		return false;
	}
}
