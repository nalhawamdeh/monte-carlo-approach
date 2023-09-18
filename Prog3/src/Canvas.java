import javax.swing.*;
import java.awt.*;

public class Canvas extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel mainPanel = new JPanel(new BorderLayout());

	public Canvas(Point[] points, Circle[] circles, Square s) {
		drawComponent circleComponent = new drawComponent(points, circles, s);
		this.getContentPane().add(mainPanel);		
		mainPanel.add(circleComponent, BorderLayout.CENTER);
	}

}
