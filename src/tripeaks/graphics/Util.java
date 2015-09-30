package tripeaks.graphics;

import java.awt.Dimension;

public abstract class Util {

	public final static Dimension REFERENCE_SCREEN = new Dimension(1920, 1080);
	private final static double ALLOWABLE_RANGE = 0.075;

	public static double getModified(double num) {
		return num * getModifiedRatio();
	}

	public static int getModified(int num) {
		double n = (double) num;
		n *= getModifiedRatio();
		return (int) n;
	}

	public static Dimension getModified(Dimension d) {
		d.width = getModified(d.width);
		d.height = getModified(d.height);
		return d;
	}

	public static double getModifiedRatio() {
		Dimension d = new Dimension(1280, 1024);// Toolkit.getDefaultToolkit().getScreenSize();
		double ratio = Math.min(
				(((double) d.width) / ((double) REFERENCE_SCREEN.width)),
				(((double) d.height) / ((double) REFERENCE_SCREEN.height)));
		if (ratio < 1.0 + ALLOWABLE_RANGE && ratio > 1.0 - ALLOWABLE_RANGE)
			return 1.0;
		return ratio;
	}

	public static boolean screenIsModified() {
		if (getModifiedRatio() == 1.0)
			return false;
		return true;
	}

}
