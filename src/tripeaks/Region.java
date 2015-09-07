package tripeaks;

import java.awt.Dimension;
import java.util.ArrayList;

public final class Region {

	private ArrayList<Region> exclusions;
	private int x, y, width, height;
	private int id;

	public Region(int x, int y, Dimension d, int id) {
		this.create(x, y, d.width, d.height);
		this.id = id;
	}

	public Region(int x, int y, int width, int height, int id) {
		this.create(x, y, width, height);
		this.id = id;
	}

	public Region(int x, int y, int width, int height, int id,
			ArrayList<Region> exc) {
		this.create(x, y, width, height);
		this.exclusions = exc;
		this.id = id;
	}

	public Region(int x, int y, Dimension d, int id, ArrayList<Region> exc) {
		this.create(x, y, d.width, d.height);
		this.exclusions = exc;
		this.id = id;
	}

	private void create(int x, int y, int width, int height) {
		if (width < 0) {
			width *= -1;
			x -= width;
		}
		if (height < 0) {
			height *= -1;
			y -= height;
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return this.height;
	}

	public int getID() {
		return this.id;
	}

	public int getWidth() {
		return this.width;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isContained(int x, int y) {
		if (exclusions != null)
			for (Region i : this.exclusions)
				if (i.isContained(x, y))
					return false;
		if (x >= this.x && x <= this.x + this.width && y >= this.y
				&& y <= this.y + this.height)
			return true;
		return false;
	}

	public String toString() {
		return "Region " + Integer.toString(this.id);
	}

}
