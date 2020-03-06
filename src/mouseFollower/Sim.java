package mouseFollower;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Sim extends template.Simulator {
	public static int width = 1280;
	public static int height = 700;
	
	private List<Food> food = new ArrayList<Food>();
	private int foodNum = 150;
	private int minFood = 50;
	private int foodSize = 10;
	
	private List<Poison> poison = new ArrayList<Poison>();
	private int poisonNum = 60;
	private int poisonSize = 10;
		
	public static void main(String[] args) {
		PApplet.main("mouseFollower.Sim");
	}
	
	public void settings() {
		size(width, height);
	}
	
	public void setup() {
		frameRate(60);
		population = new Pop(this);
		createFood();
		createPoison();
	}
	
	public void draw() {
		background(20);
		
		boolean newPop = population.execute(); 
		if (newPop) {
			createFood();
		}
		
		if (food.size() > 0) {
			population.interact(food);
		}
		if (poison.size() > 0) {
			population.interact(poison);
		}
		
		for (int f = food.size()-1; f >= 0; f--) {
			food.get(f).display();
			if (population.overlap(food.get(f))) {
				food.remove(f);
				
				double addFood = Math.random()*100;
				
				if (food.size() < minFood || addFood > 10) {
					addFood();
				}
			}
		}
		
		for (int p = poison.size()-1; p >= 0; p--) {
			poison.get(p).display();
			if (population.overlap(poison.get(p))) {
				poison.remove(p);
			}
		}
		
		createPoison();
	}
	
	public void keyPressed() {
		if (keyCode == 87) {
			population = new Pop(this);
			food.clear();
			createFood();
			poison.clear();
			createPoison();
		}
	}
	
	public void createFood() {
		while (food.size() != foodNum) {
			int x = (int)(Math.random()*width);
			int y = (int)(Math.random()*height);
			PVector pos = new PVector(x, y);
			
			int[] color = {0, 255, 0};
			
			food.add(new Food(this, "circle", pos, foodSize, color));
		}
	}
	
	private void addFood() {
		int x = (int)(Math.random()*width);
		int y = (int)(Math.random()*height);
		PVector pos = new PVector(x, y);
		
		int[] color = {0, 255, 0};
		
		food.add(new Food(this, "circle", pos, foodSize, color));
	}
	
	private void createPoison() {
		while (poison.size() != poisonNum) {
			int x = (int)(Math.random()*width);
			int y = (int)(Math.random()*height);
			PVector pos = new PVector(x, y);
			
			int[] color = {255, 0, 0};
			
			poison.add(new Poison(this, "circle", pos, poisonSize, color));
		}
	}
}
