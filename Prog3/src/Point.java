import java.io.Serializable;
import java.util.Random;

public class Point implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x, y;
	Random r = new Random();

	public Point() {
		int xc = r.nextInt(800);
		int yc = r.nextInt(800);
		this.x = xc;
		this.y = yc;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Point p1, Point p2) {
		if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "Point coordinates are: (" + this.x + "," + this.y + ")";
	}

	// Checks whether or not a point is in a circle
	public boolean isPointInCircle(Point p, Circle c) {
		double distance = Math.pow((p.getX() - c.getCx()), 2) + Math.pow((p.getY() - c.getCy()), 2);		
		return (distance <= Math.pow(c.getRadius(), 2) && distance >= 0);
	}
	
	// Counts how many circles a specific point falls in
	public int circlesContainingPoint(Point p, Circle[] circles) {
		int count = 0;
		for (Circle c : circles) {
			double distance = Math.pow((p.getX() - c.getCx()), 2) + Math.pow((p.getY() - c.getCy()), 2);
			
			if (distance < Math.pow(c.getRadius(), 2)) {
				count++;
			}
		}
		return count;
	}

}
