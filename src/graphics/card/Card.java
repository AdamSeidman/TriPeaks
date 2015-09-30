package graphics.card;

import graphics.card.Club;
import graphics.card.Diamond;
import graphics.card.Heart;
import graphics.card.Spade;
import graphics.card.Suit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import tripeaks.graphics.Util;

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
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(100));
		if (n > 10)
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(138));
		if (n == 7)
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(65));
		if (n == 9 || n == 10) {
			drawSymbol(g, x + Util.getModified(15), y + Util.getModified(80));
			drawSymbol(g, x + Util.getModified(93), y + Util.getModified(80));
			drawSymbol(g, x + Util.getModified(93), y + Util.getModified(120));
			drawSymbol(g, x + Util.getModified(15), y + Util.getModified(120));
		}
		if (n >= 4 && n <= 10) {
			drawSymbol(g, x + Util.getModified(15), y + Util.getModified(35));
			drawSymbol(g, x + Util.getModified(15), y + Util.getModified(157));
			drawSymbol(g, x + Util.getModified(93), y + Util.getModified(35));
			drawSymbol(g, x + Util.getModified(93), y + Util.getModified(157));
		}
		if (n == 2 || n == 3) {
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(40));
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(160));
		}
		if (n == 8 || n == 10) {
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(70));
			drawSymbol(g, x + Util.getModified(56), y + Util.getModified(130));
		}
		if (n >= 6 && n <= 8) {
			drawSymbol(g, x + Util.getModified(14), y + Util.getModified(100));
			drawSymbol(g, x + Util.getModified(94), y + Util.getModified(100));
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
			fontSize = Util.getModified(fontSize);
			g.setFont(new Font("Verdana", 1, fontSize));
			g.drawString(getFaceName().toUpperCase(), Util.getModified(xOffset)
					+ x, y + Util.getModified(100));
		}
	}

	private boolean drawBackground(Graphics g, int x, int y, boolean normal) {
		g.setColor(new Color(55, 55, 55));
		g.fillRoundRect(Util.getModified(-2) + x, Util.getModified(-2) + y,
				Util.getModified(144), Util.getModified(194), 20, 22);
		g.setColor(new Color(80, 80, 255));
		if (normal)
			g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, Util.getModified(140), Util.getModified(190), 20,
				22);
		if (!normal) {
			g.setColor(new Color(230, 180, 135));
			g.fillOval(Util.getModified(40) + x, Util.getModified(10) + y,
					Util.getModified(60), Util.getModified(170));
		}
		return normal;
	}

	private void drawSymbol(Graphics g, int x, int y) {
		y -= Util.getModified(16);
		if (this.s instanceof Club) {
			g.setColor(Color.BLACK);
			g.fillOval(x, Util.getModified(6) + y, Util.getModified(16),
					Util.getModified(16));
			g.fillOval(Util.getModified(8) + x, y, Util.getModified(16),
					Util.getModified(16));
			g.fillOval(Util.getModified(16) + x, Util.getModified(6) + y,
					Util.getModified(16), Util.getModified(16));
			g.fillRect(Util.getModified(11) + x, Util.getModified(16) + y,
					Util.getModified(10), Util.getModified(12));
		} else if (this.s instanceof Heart) {
			g.setColor(Color.RED);
			g.fillOval(x, y, Util.getModified(14), Util.getModified(14));
			g.fillOval(Util.getModified(11) + x, y, Util.getModified(14),
					Util.getModified(14));
			x += Util.getModified(2);
			y += Util.getModified(11);
			int[] xPoints = new int[] { Util.getModified(11) + x, x,
					Util.getModified(23) + x };
			int[] yPoints = new int[] { Util.getModified(13) + y, y, y };
			g.fillPolygon(xPoints, yPoints, 3);
		} else if (this.s instanceof Spade) {
			g.setColor(Color.BLACK);
			int[] xPoints = new int[] { Util.getModified(13) + x,
					Util.getModified(3) + x, Util.getModified(24) + x };
			int[] yPoints = new int[] { y, Util.getModified(8) + y,
					Util.getModified(8) + y };
			g.fillPolygon(xPoints, yPoints, 3);
			g.fillOval(Util.getModified(1) + x, Util.getModified(2) + y,
					Util.getModified(24), Util.getModified(24));
			g.setColor(Color.WHITE);
			g.fillRect(Util.getModified(1) + x, Util.getModified(21) + y,
					Util.getModified(25), Util.getModified(20));
			g.setColor(Color.BLACK);
			g.fillOval(Util.getModified(3) + x, Util.getModified(17) + y,
					Util.getModified(6), Util.getModified(6));
			g.fillOval(Util.getModified(17) + x, Util.getModified(17) + y,
					Util.getModified(6), Util.getModified(6));
			g.setColor(Color.WHITE);
			xPoints = new int[] { Util.getModified(3) + x,
					Util.getModified(-1) + x, Util.getModified(7) + x };
			yPoints = new int[] { Util.getModified(20) + y,
					Util.getModified(24) + y, Util.getModified(24) + y };
			g.fillPolygon(xPoints, yPoints, 3);
			xPoints = new int[] { Util.getModified(24) + x,
					Util.getModified(20) + x, Util.getModified(28) + x };
			yPoints = new int[] { Util.getModified(21) + y,
					Util.getModified(25) + y, Util.getModified(25) + y };
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(Color.BLACK);
			g.fillOval(Util.getModified(5) + x, Util.getModified(21) + y,
					Util.getModified(17), Util.getModified(3));
			g.setColor(Color.WHITE);
			xPoints = new int[] { Util.getModified(13) + x,
					Util.getModified(5) + x, Util.getModified(22) + x };
			yPoints = new int[] { Util.getModified(12) + y,
					Util.getModified(24) + y, Util.getModified(24) + y };
			g.fillPolygon(xPoints, yPoints, 3);
			g.setColor(Color.BLACK);
			g.fillRect(Util.getModified(10) + x, Util.getModified(8) + y,
					Util.getModified(9), Util.getModified(20));
			xPoints = new int[] { Util.getModified(13) + x,
					Util.getModified(3) + x, Util.getModified(23) + x };
			yPoints = new int[] { Util.getModified(22) + y,
					Util.getModified(28) + y, Util.getModified(28) + y };
			g.fillPolygon(xPoints, yPoints, 3);
		} else if (this.s instanceof Diamond) {
			g.setColor(Color.RED);
			int[] xPoints = new int[] { Util.getModified(15) + x,
					Util.getModified(27) + x, Util.getModified(15) + x,
					Util.getModified(3) + x };
			int[] yPoints = new int[] { y, Util.getModified(14) + y,
					Util.getModified(28) + y, Util.getModified(14) + y };
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