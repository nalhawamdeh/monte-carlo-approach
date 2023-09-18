
public class Sequential {
	
	public void sequentialCalculation(boolean[][] matrix, Square s, Circle[] circles, Point[] points, int numberCircles, int numberPoints) {
		System.out.println("Program is running in SEQUENTIAL Mode");
		
		// Start timer for project
		long start = System.currentTimeMillis();
		int counter = 0;

		for (int i = 0; i < circles.length; i++) {
			for (int j = 0; j < points.length; j++) {
				if (points[j].isPointInCircle(points[j], circles[i])) {
					matrix[i][j] = true;
				}
			}
		}

		for (Point point: points) {
			if (point.circlesContainingPoint(point, circles) >= 1) {
				counter++;
			}
		}

		double area = ((double) counter / numberPoints) * (s.getSide() * s.getSide());
		
		System.out.println("Number of points in circle is: " + counter);
		System.out.println("Area of your shape is: " + area);

		long end = System.currentTimeMillis();
		System.out.println("Starting time: " + start + " ms");
		System.out.println("Ending time: " + end + " ms");
		System.out.println("Time taken to complete execution: " + (end - start) + " ms");

	}

}
