import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class drawComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	Random random = new Random();
	Point[] points;
	Circle[] circles;
	Square square;

	public drawComponent(Point[] p, Circle[] c, Square s) {
		this.points = p;
		this.circles = c;
		this.square = s;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.square.getSide(), this.square.getSide());

		for (int i = 0; i < this.circles.length; i++) {
			g.setColor(Color.BLACK);
			g.drawOval(circles[i].getCx() - circles[i].getRadius(), circles[i].getCy() - circles[i].getRadius(), circles[i].getRadius()*2, circles[i].getRadius()*2);
			g.setColor(Color.WHITE);
			g.fillOval(circles[i].getCx() - circles[i].getRadius(), circles[i].getCy() - circles[i].getRadius(), circles[i].getRadius()*2, circles[i].getRadius()*2);
		}

		for (int i = 0; i < this.points.length; i++) {
			g.setColor(Color.RED);
			g.fillOval(points[i].getX(), points[i].getY(), 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval(points[i].getX(), points[i].getY(), 10, 10);
		}
	}

}
