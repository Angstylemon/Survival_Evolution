package mouseFollower;

public class DNA {
	private static int[] DEFAULT_COLOR = {255, 255, 255};
	
	private final float greenAttraction;
	private final float redAttraction;
	private final float greenVisibility;
	private final float redVisibility;
	private final float maxSpeed;
	private final float maxForce;
	private int[] color;
	
	public DNA(float gA, float rA, float gV, float rV, float mS, float mF) {
		this(gA, rA, gV, rV, mS, mF, DEFAULT_COLOR);
	}
	
	public DNA(float gA, float rA, float gV, float rV, float mS, float mF, int[] c) {
		greenAttraction = gA;
		redAttraction = rA;
		greenVisibility = gV;
		redVisibility = rV;
		maxSpeed = mS;
		maxForce = mF;
		
		color = c;
	}
	
	public float greenAttraction() {
		return greenAttraction;
	}
	
	public float redAttraction() {
		return redAttraction;
	}
	
	public float redVisibility() {
		return redVisibility;
	}
	
	public float greenVisibility() {
		return greenVisibility;
	}
	
	public float maxSpeed() {
		return maxSpeed;
	}
	
	public float maxForce() {
		return maxForce;
	}
	
	public int[] color() {
		int[] colorCopy = new int[color.length];
		for (int i = 0; i < color.length; i++) {
			colorCopy[i] = color[i];
		}
		
		return colorCopy;
	}
}
