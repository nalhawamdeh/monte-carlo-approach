import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) {
		StartMenu start = new StartMenu();
		start.setVisible(true);
	}
}

class StartMenu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String[] modes = new String[] {"Sequential", "Parallel", "Distributed"};

	public JPanel main =  new JPanel(new GridLayout(5, 1));

	public JTextField numberCircles = new JTextField(7);
	public JTextField numberPoints  = new JTextField(7);;
	public JComboBox<String> modeComboBox = new JComboBox<>(modes);
	public JButton startButton = new JButton("Start");

	public StartMenu() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 800);

		// Add elements to main panel
		main.add(createTitlePanel());
		main.add(createInputPanel("Number of circles: ", numberCircles));
		main.add(createInputPanel("Number of points: ", numberPoints));
		main.add(modeComboBox);
		main.add(startButton);

		// Action listener
		startButton.addActionListener(e -> startProject());

		add(main);
	}

	public JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Pi Project");
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 20));
		titlePanel.add(titleLabel);
		return titlePanel;
	}

	public JPanel createInputPanel(String labelText, JTextField textField) {
		JPanel inputPanel = new JPanel();
		JLabel label = new JLabel(labelText);
		label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));
		inputPanel.add(label);
		inputPanel.add(textField);
		return inputPanel;
	}


	public void startProject() {
		// Parse input values
		int c = Integer.parseInt(numberCircles.getText());
		int p = Integer.parseInt(numberPoints.getText());
		String m = modeComboBox.getSelectedItem().toString();

		// Define circles, points, and square for area calculation
		Square sq = new Square(800);
		Circle[] circles= new Circle[c];
		Point[] points= new Point[p];

		// Create canvas that has components on it
		JFrame window = new Canvas(points, circles, sq);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(800, 600);
		window.setVisible(true);

		// Initialize circles, points and matrix
		for (int i = 0; i < circles.length; i++) {
			circles[i] = new Circle();
		}

		for (int i = 0; i < points.length; i++) {
			points[i] = new Point();
		}

		boolean[][] matrix = new boolean[c][p];

		if (m.equalsIgnoreCase("Sequential")) {
			Sequential s = new Sequential();
			s.sequentialCalculation(matrix, sq, circles, points, c, p);
		} else if (m.equalsIgnoreCase("Parallel")) {
			Parallel pl = new Parallel();
			pl.parallelCalculation(matrix, sq, circles, points, c, p);
		} else if(m.equalsIgnoreCase("Distributed")) {
			System.exit(0);
		}else {
			System.exit(0);
		}
	}

}




