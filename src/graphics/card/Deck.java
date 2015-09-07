package graphics.card;

import java.util.Random;

public final class Deck {

	public Card[] cards;
	private static Random random = new Random();

	public Deck() {
		this.cards = new Card[52];
		this.shuffle();
	}

	public Deck(Deck d) {
		this.cards = new Card[52];
		for (int i = 0; i < 52; i++)
			if (d.cards[i] != null)
				this.cards[i] = new Card(d.cards[i].getSuit(),
						d.cards[i].getNumber());
			else
				this.cards[i] = null;
	}

	private static Suit nextSuit() {
		switch (random.nextInt(4)) {
		case 1:
			return new Heart();
		case 2:
			return new Spade();
		case 3:
			return new Diamond();
		default:
			return new Club();
		}
	}

	public void shuffle() {
		for (int i = 0; i < 52; i++) {
			cards[i] = new Card(nextSuit(), (random.nextInt(13) + 1));
			boolean secondInstance = false;
			for (int j = 0; j < i; j++)
				if (cards[j].equals(cards[i])) {
					secondInstance = true;
					break;
				}
			if (secondInstance)
				i--;
		}
	}

}
