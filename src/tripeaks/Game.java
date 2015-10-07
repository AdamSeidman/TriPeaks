package tripeaks;

import graphics.card.Card;
import graphics.card.Club;
import graphics.card.Deck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tripeaks.graphics.Frame;
import tripeaks.graphics.Util;

public final class Game {

	private Deck deck;
	private Card setCard;
	private ArrayList<Card> setDeck;
	private ArrayList<Region> activeRegions;
	private Region[] regions = new Region[28];
	private Frame frame;
	private final Dimension SCREEN_SIZE;
	private final static Dimension CARD_SIZE = new Dimension(
			Util.getModified(140), Util.getModified(190));
	private final static int X_OFFSET = Util.getModified(86), Y_OFFSET = Util
			.getModified(145);
	private Region setDeckRegion, setCardRegion, backupButtonRegion,
			fullScreenButtonRegion, newGameRegion;
	private ArrayList<Backup> backupList = new ArrayList<Backup>();

	public Game() {
		frame = new Frame();
		activeRegions = new ArrayList<Region>();
		this.SCREEN_SIZE = frame.getSize();
		newGameRegion = new Region(Util.getModified(40), Util.getModified(55), Util.getModified(130), Util.getModified(50), -999);
		backupButtonRegion = new Region(SCREEN_SIZE.width - Util.getModified(145),
				SCREEN_SIZE.height - Util.getModified(145), Util.getModified(80), Util.getModified(80), -999);
		setDeckRegion = new Region(
				((SCREEN_SIZE.width / 2) - CARD_SIZE.width) - 15,
				(SCREEN_SIZE.height - CARD_SIZE.height) - Util.getModified(50), CARD_SIZE, -999);
		setCardRegion = new Region((SCREEN_SIZE.width / 2) + 15,
				(SCREEN_SIZE.height - CARD_SIZE.height) - Util.getModified(50), CARD_SIZE, -999);
		fullScreenButtonRegion = new Region(SCREEN_SIZE.width - Util.getModified(165), Util.getModified(57), Util.getModified(130),
				Util.getModified(35), -999);
		this.assignRegions();
		frame.setVisible(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.restart();
		JOptionPane
				.showMessageDialog(
						null,
						"<html>Welcome to TriPeaks!<br>"
								+ "The purpose of this game is to clear all the cards on the table.<br>"
								+ "On the bottom, you will see a small deck with 23 cards,<br>"
								+ "as well as a single card to play off of.<br>"
								+ "You must play a card that is one step higher or lower to clear cards.<br>"
								+ "Aces and Kings can be played off of eachother.<br>"
								+ "Cards uncover as you clear the cards on top of them.<br>"
								+ "Have fun!</html>",
						"TriPeaks  |  by Adam J Seidman",
						JOptionPane.PLAIN_MESSAGE, null);
		frame.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				int x = e.getX(), y = e.getY();
				if (Util.screenIsModified()
						&& frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
					System.out.println("USED");
					y -= (Toolkit.getDefaultToolkit().getScreenSize().height - (Util.REFERENCE_SCREEN.height * Util
							.getModifiedRatio())) / 4;
					x -= (Toolkit.getDefaultToolkit().getScreenSize().width - (Util.REFERENCE_SCREEN.height * Util
							.getModifiedRatio())) / 4;
				}
				if (newGameRegion.isContained(x, y)) {
					int answer = -1;
					while (answer == -1)
						answer = JOptionPane.showOptionDialog(null,
								"                            Are you sure?",
								"TriPeaks  |  by Adam J Seidman",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, null, null);
					if (answer == 0)
						restart();
					return;
				}
				if (fullScreenButtonRegion.isContained(x, y)) {
					if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH)
						frame.setExtendedState(frame.getExtendedState()
								| JFrame.MAXIMIZED_BOTH);
					else {
						frame.setSize(new Dimension(
								(int) (((double) Util.REFERENCE_SCREEN.width) * Util
										.getModifiedRatio()),
								(int) (((double) Util.REFERENCE_SCREEN.height) * Util
										.getModifiedRatio())));
						frame.setLocationRelativeTo(null);
					}
					Thread t = new Thread() {
						public void run() {
							long time = System.currentTimeMillis() + 175;
							while (System.currentTimeMillis() < time)
								System.out.print("");
							redraw();
						}
					};
					t.start();
					return;
				}
				if (backupButtonRegion.isContained(x, y)) {
					if (backupList.size() == 0)
						return;
					setDeck = backupList.get(0).getSetList();
					deck = backupList.get(0).getDeck();
					setCard = backupList.get(0).getSetCard();
					backupList.remove(0);
					redraw();
					return;
				}
				if (setDeckRegion.isContained(x, y)) {
					if (setDeck.size() == 0)
						return;
					backup();
					setCard = setDeck.get(0);
					setDeck.remove(0);
					redraw();
					if (activeRegions.size() == 0) {
						endGameSequence(true);
						return;
					} else if (setDeck.size() > 0)
						return;
					else {
						for (int i = 0; i < 28; i++) {
							if (deck.cards[i] == null
									|| deck.cards[i].getNumber() < 0)
								continue;
							if (Math.abs(setCard.getNumber()
									- deck.cards[i].getNumber()) == 1
									|| Math.abs(setCard.getNumber()
											- deck.cards[i].getNumber()) == 12)
								return;
						}
						endGameSequence(false);
					}
					return;
				}
				int id = -1;
				for (Region i : activeRegions)
					if (i.isContained(x, y)) {
						id = i.getID();
						break;
					}
				if (id == -1)
					return;
				if (Math.abs(setCard.getNumber() - deck.cards[id].getNumber()) == 1
						|| Math.abs(setCard.getNumber()
								- deck.cards[id].getNumber()) == 12) {
					backup();
					setCard = deck.cards[id];
					for (Region i : activeRegions)
						if (i.getID() == id) {
							activeRegions.remove(i);
							break;
						}
					deck.cards[id] = null;
					redraw();
					if (activeRegions.size() == 0) {
						endGameSequence(true);
						return;
					} else if (setDeck.size() > 0)
						return;
					else {
						for (int i = 0; i < 28; i++) {
							if (deck.cards[i] == null
									|| deck.cards[i].getNumber() < 0)
								continue;
							if (Math.abs(setCard.getNumber()
									- deck.cards[i].getNumber()) == 1
									|| Math.abs(setCard.getNumber()
											- deck.cards[i].getNumber()) == 12)
								return;
						}
						endGameSequence(false);
					}
				}
			}
		});
		frame.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				redraw();
			}

			public void focusLost(FocusEvent e) {
				redraw();
			}
		});
		frame.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent e) {
				redraw();
			}

			public void componentMoved(ComponentEvent e) {
				redraw();
			}

			public void componentResized(ComponentEvent e) {
				redraw();
			}

			public void componentShown(ComponentEvent e) {
				redraw();
			}
		});
		frame.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				int answer = -1;
				while (answer == -1)
					answer = JOptionPane.showOptionDialog(null,
							"           Are you sure you want to quit?",
							"TriPeaks  |  by Adam J Seidman",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, null, null);
				if (answer == 0) {
					frame.setVisible(false);
					System.exit(0);
				}
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
				Thread t = new Thread() {
					public void run() {
						long time = System.currentTimeMillis() + 250;
						while (System.currentTimeMillis() < time)
							System.out.print("");
						redraw();
					}
				};
				t.start();
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}
		});
	}

	private void assignActiveRegions() {
		for (int i = 18; i < 28; i++)
			if (deck.cards[i] != null && !activeRegions.contains(regions[i]))
				activeRegions.add(regions[i]);
		check(9, 18, 19);
		check(10, 19, 20);
		check(11, 20, 21);
		check(12, 21, 22);
		check(13, 22, 23);
		check(14, 23, 24);
		check(15, 24, 25);
		check(16, 25, 26);
		check(17, 26, 27);
		check(3, 9, 10);
		check(4, 10, 11);
		check(5, 12, 13);
		check(6, 13, 14);
		check(7, 15, 16);
		check(8, 16, 17);
		check(0, 3, 4);
		check(1, 5, 6);
		check(2, 7, 8);
	}

	private void assignRegions() {
		final int QUARTER = (Util.getModified(Toolkit.getDefaultToolkit().getScreenSize().width) - (3 * CARD_SIZE.width)) / 4;
		int y = 90;
		final int A = QUARTER;
		final int B = (2 * QUARTER) + CARD_SIZE.width;
		final int C = (3 * QUARTER) + (2 * CARD_SIZE.width);
		regions[0] = new Region(A, y, CARD_SIZE, 0);
		regions[1] = new Region(B, y, CARD_SIZE, 1);
		regions[2] = new Region(C, y, CARD_SIZE, 2);
		y += Y_OFFSET;
		regions[3] = new Region(A - X_OFFSET, y, CARD_SIZE, 3);
		regions[4] = new Region(A + X_OFFSET, y, CARD_SIZE, 4);
		regions[5] = new Region(B - X_OFFSET, y, CARD_SIZE, 5);
		regions[6] = new Region(B + X_OFFSET, y, CARD_SIZE, 6);
		regions[7] = new Region(C - X_OFFSET, y, CARD_SIZE, 7);
		regions[8] = new Region(C + X_OFFSET, y, CARD_SIZE, 8);
		y += Y_OFFSET;
		regions[9] = new Region(A - (2 * X_OFFSET), y, CARD_SIZE, 9);
		regions[10] = new Region(A, y, CARD_SIZE, 10);
		regions[11] = new Region(A + (2 * X_OFFSET), y, CARD_SIZE, 11);
		regions[12] = new Region(B - (2 * X_OFFSET), y, CARD_SIZE, 12);
		regions[13] = new Region(B, y, CARD_SIZE, 13);
		regions[14] = new Region(B + (2 * X_OFFSET), y, CARD_SIZE, 14);
		regions[15] = new Region(C - (2 * X_OFFSET), y, CARD_SIZE, 15);
		regions[16] = new Region(C, y, CARD_SIZE, 16);
		regions[17] = new Region(C + (2 * X_OFFSET), y, CARD_SIZE, 17);
		y += Y_OFFSET;
		regions[18] = new Region(A - (3 * X_OFFSET), y, CARD_SIZE, 18);
		regions[19] = new Region(A - X_OFFSET, y, CARD_SIZE, 19);
		regions[20] = new Region(A + X_OFFSET, y, CARD_SIZE, 20);
		regions[21] = new Region(B - (3 * X_OFFSET), y, CARD_SIZE, 21);
		regions[22] = new Region(B - X_OFFSET, y, CARD_SIZE, 22);
		regions[23] = new Region(B + X_OFFSET, y, CARD_SIZE, 23);
		regions[24] = new Region(B + (3 * X_OFFSET), y, CARD_SIZE, 24);
		regions[25] = new Region(C - X_OFFSET, y, CARD_SIZE, 25);
		regions[26] = new Region(C + X_OFFSET, y, CARD_SIZE, 26);
		regions[27] = new Region(C + (3 * X_OFFSET), y, CARD_SIZE, 27);
	}

	private void backup() {
		backupList.add(0, new Backup(new Deck(this.deck), this.setCard,
				this.setDeck));
	}

	private void check(int a, int b, int c) {
		if (deck.cards[a] != null && deck.cards[b] == null
				&& deck.cards[c] == null) {
			deck.cards[a] = new Card(deck.cards[a].getSuit(),
					Math.abs(deck.cards[a].getNumber()));
			if (!activeRegions.contains(regions[a]))
				activeRegions.add(regions[a]);
		}
	}

	private void endGameSequence(boolean won) {
		int answer = -1;
		while (answer == -1) {
			answer = JOptionPane.showOptionDialog(null,
					"            Would you like to play again?",
					(won ? "You Won" : "You Lost")
							+ "  |  TriPeaks  |  by Adam J Seidman",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					null, null);
		}
		if (answer == 1) {
			this.frame.setVisible(false);
			System.exit(0);
		}
		this.restart();
	}

	private void redraw() {
		int yOffset = 0, xOffset = 0;
		if (Util.screenIsModified()
				&& this.frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
			yOffset += (Toolkit.getDefaultToolkit().getScreenSize().height - (Util.REFERENCE_SCREEN.height * Util
					.getModifiedRatio())) / 4;
			xOffset += (Toolkit.getDefaultToolkit().getScreenSize().width - (Util.REFERENCE_SCREEN.height * Util
					.getModifiedRatio())) / 4;
		}
		frame.resetBackground();
		Graphics g = frame.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRoundRect(backupButtonRegion.getX() - 1 + xOffset,
				backupButtonRegion.getY() - 1 + yOffset,
				backupButtonRegion.getWidth() + 2,
				backupButtonRegion.getHeight() + 2, 15, 15);
		g.fillRoundRect(fullScreenButtonRegion.getX() - 1 + xOffset,
				fullScreenButtonRegion.getY() - 1 + yOffset,
				fullScreenButtonRegion.getWidth() + 2,
				fullScreenButtonRegion.getHeight() + 2, 8, 8);
		g.fillRoundRect(newGameRegion.getX() - 1 + xOffset,
				newGameRegion.getY() - 1 + yOffset,
				newGameRegion.getWidth() + 2, newGameRegion.getHeight() + 2,
				30, 30);
		g.setColor(new Color(75, 75, 75));
		g.fillRoundRect(backupButtonRegion.getX() + xOffset,
				backupButtonRegion.getY() + yOffset,
				backupButtonRegion.getWidth(), backupButtonRegion.getHeight(),
				15, 15);
		g.setColor(new Color(210, 210, 210));
		g.fillRoundRect(fullScreenButtonRegion.getX() + xOffset,
				fullScreenButtonRegion.getY() + yOffset,
				fullScreenButtonRegion.getWidth(),
				fullScreenButtonRegion.getHeight(), 8, 8);
		g.setColor(new Color(75, 0, 200));
		g.fillRoundRect(newGameRegion.getX() + xOffset, newGameRegion.getY()
				+ yOffset, newGameRegion.getWidth(), newGameRegion.getHeight(),
				30, 30);
		g.setFont(new Font("Verdana", Font.ITALIC, Util.getModified(17) + 1));
		g.setColor(new Color(240, 240, 240));
		g.drawString("NEW GAME", newGameRegion.getX() + Util.getModified(13) + xOffset,
				newGameRegion.getY() + Util.getModified(30) + yOffset);
		g.setColor(Color.BLACK);
		g.drawString("UNDO", backupButtonRegion.getX() + Util.getModified(14) + xOffset,
				backupButtonRegion.getY() + Util.getModified(44) + yOffset);
		g.drawString("FULLSCREEN", fullScreenButtonRegion.getX() + Util.getModified(5) + xOffset,
				fullScreenButtonRegion.getY() + Util.getModified(22) + yOffset);
		this.assignActiveRegions();
		for (int i = 0; i < 28; i++) {
			if (deck.cards[i] != null)
				deck.cards[i].draw(g, regions[i].getX() + xOffset,
						regions[i].getY() + yOffset);
		}
		setCard.draw(frame.getGraphics(), setCardRegion.getX() + xOffset,
				setCardRegion.getY() + yOffset);
		int setDeckSize = setDeck.size();
		if (setDeckSize > 0)
			new Card(new Club(), -1).draw(g, setDeckRegion.getX() + xOffset,
					setDeckRegion.getY() + yOffset);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Verdana", Font.BOLD, Util.getModified(40)));
		g.drawString(Integer.toString(setDeckSize) + " Card"
				+ (setDeckSize == 1 ? "" : "s") + " Left", setCardRegion.getX()
				+ xOffset + Util.getModified(225),
				setCardRegion.getY() + Util.getModified(85) + yOffset);
	}

	private void restart() {
		backupList = new ArrayList<Backup>();
		deck = new Deck();
		for (int i = 0; i < 18; i++)
			deck.cards[i] = new Card(deck.cards[i].getSuit(),
					-deck.cards[i].getNumber());
		setCard = deck.cards[28];
		setDeck = new ArrayList<Card>();
		for (int i = 29; i < 52; i++)
			setDeck.add(deck.cards[i]);
		this.assignActiveRegions();
		long time = System.currentTimeMillis() + 250;
		while (System.currentTimeMillis() < time)
			System.out.print("");
		redraw();
	}

	public static void main(String[] args) {
		new Game();
	}

}
