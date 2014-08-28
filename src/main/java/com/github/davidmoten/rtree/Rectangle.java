package com.github.davidmoten.rtree;

import com.google.common.base.Preconditions;

public class Rectangle {
	private final double x1, y1, x2, y2;
	private final double area;

	public Rectangle(double x1, double y1, double x2, double y2) {
		Preconditions.checkArgument(x2 >= x1);
		Preconditions.checkArgument(y2 >= y1);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.area = Math.abs((x1 - x2) * (y1 - y2));
	}

	public double x1() {
		return x1;
	}

	public double y1() {
		return y1;
	}

	public double x2() {
		return x2;
	}

	public double y2() {
		return y2;
	}

	public double area() {
		return area;
	}

	public double width() {
		return x2 - x1;
	}

	public Rectangle add(Rectangle r) {
		return new Rectangle(Math.min(x1, r.x1), Math.min(y1, r.y1), Math.max(
				x2, r.x2), Math.max(y2, r.y2));
	}

	public static Rectangle create(double x1, double y1, double x2, double y2) {
		return new Rectangle(x1, y1, x2, y2);
	}

	public boolean in(double x, double y) {
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}

	private boolean overlapsOnce(Rectangle r) {
		return r.in(x1, y1) || r.in(x2, y2);
	}

	public boolean overlaps(Rectangle r) {
		return overlapsOnce(r) || r.overlapsOnce(this);
	}

	public double distance(Rectangle r) {
		if (overlaps(r))
			return 0;
		else {
			Rectangle mostLeft = x1 < r.x1 ? this : r;
			Rectangle mostRight = x1 > r.x1 ? this : r;
			double xDifference = Math.max(0, mostLeft.x1 == mostRight.x1 ? 0
					: mostRight.x1 - mostLeft.x2);

			Rectangle upper = y1 < r.y1 ? this : r;
			Rectangle lower = y1 > r.y1 ? this : r;

			double yDifference = Math.max(0, upper.y1 == lower.y1 ? 0
					: lower.y1 - upper.y2);

			return Math.sqrt(xDifference * xDifference + yDifference
					* yDifference);
		}
	}

	@Override
	public String toString() {
		return "Rectangle [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2="
				+ y2 + "]";
	}

}
