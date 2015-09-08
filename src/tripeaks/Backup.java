package tripeaks;

import graphics.card.Card;
import graphics.card.Deck;

import java.util.ArrayList;

public final class Backup {

	private final Deck D;
	private final Card SET_CARD;
	private final ArrayList<Card> SET_LIST;

	public Backup(Deck d, Card setCard, ArrayList<Card> setList) {
		this.D = d;
		this.SET_CARD = setCard;
		this.SET_LIST = new ArrayList<Card>();
		for(Card i : setList)
			this.SET_LIST.add(i);
	}

	public Deck getDeck() {
		return this.D;
	}

	public Card getSetCard() {
		return this.SET_CARD;
	}

	public ArrayList<Card> getSetList() {
		return this.SET_LIST;
	}

}
