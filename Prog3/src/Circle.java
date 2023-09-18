import java.io.Serializable;
import java.util.Random;

public class Circle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int Cx, Cy, radius;
	Random r = new Random();

	public Circle() {
		int x = r.nextInt(800);
		int y = r.nextInt(800);
		int rad = r.nextInt(Math.min(Math.min(x, y), Math.min(800 - x, 800 - y)));
		this.Cx = x;
		this.Cy = y;
		this.radius = rad;
	}

	public int getCx() {
		return Cx;
	}

	public void setCx(int cx) {
		Cx = cx;
	}

	public int getCy() {
		return Cy;
	}

	public void setCy(int cy) {
		Cy = cy;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public String circleCenterToString() {
		return "Circle center is: (" + this.Cx + "," + this.Cy + ") and Circle radius is: " + this.radius;
	}

}
