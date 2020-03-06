package template;

import processing.core.PApplet;
import template.Population;

public abstract class Simulator extends PApplet {
	public static int width;
	public static int height;
	
	protected Population population;
		
	public abstract void settings();
	
	public abstract void setup();
		
	public abstract void draw();
}
