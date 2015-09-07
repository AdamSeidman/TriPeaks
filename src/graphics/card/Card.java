package graphics.card;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public final class Card {

	private Suit s;
	private int n;

	public Card(Suit s, int n) {
		this.s = s;
		this.n = n;
	}

	public void draw(Graphics g, int x, int y) {
		if (g == null) {
			System.err.println("There was a graphics error.");
			return;
		}
		if (!drawBackground(g, x, y, (this.n >= 1 && this.n <= 13)))
			return;
		if (n == 1 || n == 3 || n == 5 || n == 9)
			drawSymbol(g, x + 56, y + 100);
		if (n > 10)
			drawSymbol(g, x + 56, y + 138);
		if (n == 7)
			drawSymbol(g, x + 56, y + 65);
		if (n == 9 || n == 10) {
			drawSymbol(g, x + 15, y + 80);
			drawSymbol(g, x + 93, y + 80);
			drawSymbol(g, x + 93, y + 120);
			drawSymbol(g, x + 15, y + 120);
		}
		if (n >= 4 && n <= 10) {
			drawSymbol(g, x + 15, y + 35);
			drawSymbol(g, x + 15, y + 157);
			drawSymbol(g, x + 93, y + 35);
			drawSymbol(g, x + 93, y + 157);
		}
		if (n == 2 || n == 3) {
			drawSymbol(g, x + 56, y + 40);
			drawSymbol(g, x + 56, y + 160);
		}
		if (n == 8 || n == 10) {
			drawSymbol(g, x + 56, y + 70);
			drawSymbol(g, x + 56, y + 130);
		}
		if (n >= 6 && n <= 8) {
			drawSymbol(g, x + 14, y + 100);
			drawSymbol(g, x + 94, y + 100);
		}
		if (n > 10) {
			if (this.s instanceof Club || this.s instanceof Spade)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.RED);
			int xOffset = 14;
			int fontSize = 40;
			if (this.n == 12) {
				xOffset -= 12;
				fontSize -= 5;
			} else if (this.n == 13)
				xOffset -= 3;
			g.setFont(new Font("Verdana", 1, fontSize));
			g.drawString(getFaceName().toUpperCase(), xOffset + x, y + 100);
		}
	}

	private boolean drawBackground(Graphics g, int x, int y, boolean normal) {
		g.setColor(new Color(55, 55, 55));
		g.fillRoundRect(x - 2, y - 2, 144, 194, 20, 22);
		g.setColor(new Color(80, 80, 255));
		if (normal)
			g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, 140, 190, 20, 22);
		if (!normal) {
			g.setColor(new Color(230, 180, 135));
			g.fillOval(x + 40, y + 10, 60, 170);
		}
		return normal;
	}

	private void drawSymbol(Graphics g, int x, int y) {
		y -= 15;
		if (this.s instanceof Club) {
			g.setColor(Color.BLACK);
			g.fillOval(x, 6 + y, 16, 16);
			g.fillOval(8 + x, y, 16, 16);
			g.fillOval(16 + x, 6 + y, 16, 16);
			g.fillRect(11 + x, 16 + y, 10, 12);
		} else if (this.s instanceof Heart) {
			g.setColor(Color.RED);
			g.fillOval(x, y, 14, 14);
			g.fillOval(x + 12, y, 14, 14);
			x += 2;
			y += 11;
			int[] xPoints = new int[] { (11 + x), x, (23 + x) };
			int[] yPoints = new int[] { (13 + y), y, y };
			g.fillPolygon(xPoints, yPoints, 3);
		} else if (this.s instanceof Spade) {
			g.setColor(Color.BLACK);
			int[] xPoints = new int[] { (10 + x + 3), (x + 3), (21 + x + 3) };
			int[] yPoints = new int[] { y, (y + 8), (y + 8) };
			g.fillPolygon(xPoints, yPoints, 3);
			g.fillOval(x + 1, y + 2, 24, 24);
			g.setColor(Color.WHITE);
			g.fillRect(x + 1, y + 21, 25, 20);
			g.setColor(Color.BLACK);
			g.fillOval(3 + x, 17 + y, 6, 6);
			g.fillOval(17 + x, 17 + y, 6, 6);
			g.setColor(Color.WHITE);
			xPoints = new int[] { (x + 3), x - 1, (x + 7) };
			yPoints = new int[] { y + 20, (y + 24), (y + 24) };
			g.fillPolygon(xPoints, yPoints, 3);
			xPoints = new int[] { (x + 24), x + 20, (x + 28) };
			yPoints = new int[] { y + 21, (y + 25), (y + 25) };
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(Color.BLACK);
			g.fillOval(x + 5, y + 21, 17, 3);
			g.setColor(Color.WHITE);
			xPoints = new int[] { (13 + x), x + 5, (22 + x) };
			yPoints = new int[] { y + 12, (y + 24), (y + 24) };
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(Color.BLACK);
			g.fillRect(x + 9, y + 8, 9, 20);
			xPoints = new int[] { (13 + x), x + 3, (23 + x) };
			yPoints = new int[] { y + 22, (y + 28), (y + 28) };
			g.fillPolygon(xPoints, yPoints, 3);
		} else if (this.s instanceof Diamond) {
			g.setColor(Color.RED);
			int[] xPoints = new int[] { 15 + x, x + 27, x + 15, x + 3 };
			int[] yPoints = new int[] { y, y + 14, y + 28, y + 14 };
			g.fillPolygon(xPoints, yPoints, 4);
		}
	}

	public boolean equals(Card c) {
		return this.toString().equals(c.toString());
	}

	public String getFaceName() {
		switch (this.n) {
		case 11:
			return "Jack";
		case 12:
			return "Queen";
		case 13:
			return "King";
		default:
			return null;
		}
	}

	public int getNumber() {
		return this.n;
	}

	public Suit getSuit() {
		return this.s;
	}

	public void reset(Suit s, int n) {
		this.s = s;
		this.n = n;
	}

	public String toString() {
		String num = this.getFaceName();
		if (num == null)
			num = (this.n == 1 ? "Ace" : Integer.toString(this.n));
		String suit;
		if (this.s instanceof Club)
			suit = "Clubs";
		else if (this.s instanceof Heart)
			suit = "Hearts";
		else if (this.s instanceof Spade)
			suit = "Spades";
		else
			suit = "Diamonds";
		return (num + " of " + suit);
	}

}
