import java.util.concurrent.atomic.AtomicInteger;

public class Parallel {

	public void parallelCalculation(boolean[][] matrix, Square s, Circle[] circles, Point[] points, int numberCircles, int numberPoints) {
		System.out.println("Program is running in PARALLEL Mode");

		// Start timer for project
		long start = System.currentTimeMillis();

		// Atomic counter to avoid race conditions
		AtomicInteger counter = new AtomicInteger(0);

		//Object lock = new Object();

		// Number of threads, 4 cores
		int numThreads = 4;

		// Each thread works on a group of rows
		int rowsPerThread = (int) Math.ceil((double) numberCircles / numThreads);

		CalculationThread[] threads = new CalculationThread[numThreads];

		for (int t = 0; t < numThreads; t++) {
			// Calculate startRow and endRow for a thread
			int startRow = t * rowsPerThread;
			int endRow = Math.min(startRow + rowsPerThread, numberCircles);

			// Create and start CalculationThread
			threads[t] = new CalculationThread(matrix, circles, points, startRow, endRow);
			threads[t].start();
		}

		// Wait for all threads to finish
		for (CalculationThread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Could not parallelize
		for (Point point: points) {
			if (point.circlesContainingPoint(point, circles) >= 1) {
				counter.getAndIncrement();
			}
		}

		double area = ((double) counter.get() / numberPoints) * (s.getSide() * s.getSide());
			
		System.out.println("Number of threads used: " + numThreads);
		System.out.println("Number of points in circle is: " + counter.get());
		System.out.println("Area of your shape is: " + area);

		long end = System.currentTimeMillis();
		System.out.println("Starting time: " + start + " ms");
		System.out.println("Ending time: " + end + " ms");
		System.out.println("Time taken to complete execution: " + (end - start) + " ms");

	}

	class CalculationThread extends Thread {
		boolean[][] matrix;
		Circle[] circles;
		Point[] points;
		int startRow;
		int endRow;

		public CalculationThread(boolean[][] matrix, Circle[] circles, Point[] points, int startRow, int endRow) {
			this.matrix = matrix;
			this.circles = circles;
			this.points = points;
			this.startRow = startRow;
			this.endRow = endRow;
		}

		@Override
		public void run() {
			for (int i = startRow; i < endRow; i++) {
				for (int j = 0; j < points.length; j++) {
					if (points[j].isPointInCircle(points[j], circles[i])) {
						matrix[i][j] = true;
					}
				}
			}
		}
	}
}
