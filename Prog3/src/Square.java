import java.io.Serializable;

public class Square implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int side;

	public Square(int side) {
		this.side = side;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}
	
}
