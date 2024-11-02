package main.game.map;

public class Point {
	private String id;
	private int positionX;
	private int positionY;
	private double weight;

	public Point(int x, int y) {
		this.setPositionX(x);
		this.setPositionY(y);
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Point p = (Point) obj;
		return (this.positionX == p.getPositionX() && this.positionY == p
				.getPositionY());
	}

	@Override
	public int hashCode() {
		int result = Integer.hashCode(positionX);
		result = 31 * result + Integer.hashCode(positionY);
		return result;
	}

	@Override
	public String toString() {
		return "Point{" + "positionX=" + positionX + ", positionY=" + positionY
				+ '}';
	}

	public double distance(Point other) {
		return Math.sqrt(Math.pow(this.positionX - other.positionX, 2)
				+ Math.pow(this.positionY - other.positionY, 2));
	}
}
