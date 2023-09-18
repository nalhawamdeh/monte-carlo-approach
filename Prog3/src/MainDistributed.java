import java.util.Random;
import java.util.stream.IntStream;
import mpi.MPI;

public class MainDistributed {
	//1 C:\mpj-v0_44\mpj-v0_44\\onf\mpjexpress.conf niodev
	public static int rank;
	public static int size;
	public static int side;
	public static int numberPoints;
	public static int numberCircles;
	public static double[] circles;
	public static double[] points;
	
	public static void main(String[] args) throws Exception {
		// Start time 
		long start = System.currentTimeMillis();

		MPI.Init(args);

		rank = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		
		numberCircles = 100;
		side = 800;
		numberPoints = 100000; 

		// Triplets of (x, y, radius)
		circles = new double[3 * numberCircles];
		
		//  Pairs of (x, y)
		points = new double[2 * numberPoints];

		Random random = new Random();
		if (rank == 0) {
			for (int i = 0; i < numberCircles; i++) {
				double x = random.nextInt(side);
				double y = random.nextInt(side);
				double radius = random.nextInt() * side / 2;
				circles[3 * i] = x;
				circles[3 * i + 1] = y;
				circles[3 * i + 2] = radius;
			}

			for (int i = 0; i < numberPoints; i++) {
				double x = random.nextInt() * side;
				double y = random.nextInt() * side;
				points[2 * i] = x;
				points[2 * i + 1] = y;
			}
		}

		MPI.COMM_WORLD.Bcast(circles, 0, circles.length, MPI.DOUBLE, 0);

		// Groups of points per process
		double[] processPoints = new double[2 * numberPoints / size];

		if (rank == 0) {
			// Distribute groups of points to processes
			for (int i = 0; i < size; i++) {
				if(i != 0) {
					MPI.COMM_WORLD.Send(points, i * processPoints.length, processPoints.length, MPI.DOUBLE, i, 0);
				} else {
					System.arraycopy(points, 0, processPoints, 0, processPoints.length);
				}
			}
		} else {
			// Each process receives its points from process 0
			MPI.COMM_WORLD.Recv(processPoints, 0, processPoints.length, MPI.DOUBLE, 0, 0);
		}

		// Each process calculates if its points are inside the circles
		int processCount = 0;

		for (int i = 0; i < processPoints.length / 2; i++) {
			double x = processPoints[2 * i];
			double y = processPoints[2 * i + 1];
			for (int j = 0; j < numberCircles; j++) {
				double cx = circles[3 * j];
				double cy = circles[3 * j + 1];
				double radius = circles[3 * j + 2];
				if (pointInsideCircle(x, cx, y, cy, radius)) {
					processCount++;
					break;
				}
			}
		}

		// Gather the results from all processes
		int[] pointsInside = new int[size];
		MPI.COMM_WORLD.Gather(new int[]{processCount}, 0, 1, MPI.INT, pointsInside, 0, 1, MPI.INT, 0);

		int sumPoints;

		// Process 0 calculates the final result
		if (rank == 0) {
			sumPoints = IntStream.of(pointsInside).sum();
			double area = ((double) sumPoints) / numberPoints * side * side;
			System.out.println("Number of processes: " + size);
			System.out.println("Number of circles: " + numberCircles);
			System.out.println("Number of points:" + numberPoints);
			System.out.println("Number of points inside the shape: " + sumPoints);
			System.out.println("Estimated area: " + area);

			// Calculate running time
			long end = System.currentTimeMillis();
			System.out.println("Start time: " + start);
			System.out.println("End time: " + end);
			System.out.println("Running time: " + (end - start));
		}

		MPI.Finalize();
	}

	// Calculates if a point falls inside a circle
	public static boolean pointInsideCircle(double x, double cx, double y, double cy, double radius) {
		double distance = Math.pow(x - cx, 2) + Math.pow(y - cy, 2);
		
		if (distance <= Math.pow(radius, 2)) {
			return true;
		}
		return false;
	}
}



