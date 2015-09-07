package tripeaks.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

public final class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND = new Color(30, 130, 70);
	private static final String TITLE = "TriPeaks  |  by Adam J Seidman";
	private static final Dimension SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private Container pane;

	public Frame() {
		super(TITLE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(SIZE);
		this.pane = this.getContentPane();
		this.pane.setBackground(BACKGROUND);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	public void resetBackground() {
		Graphics g = this.getGraphics();
		g.setColor(BACKGROUND);
		Dimension size = this.getSize();
		g.fillRect(-20, -20, size.width + 40, size.height + 40);
	}

}
