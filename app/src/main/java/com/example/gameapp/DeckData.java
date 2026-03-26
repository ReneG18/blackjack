package com.example.gameapp;

import java.util.Random;
public class DeckData {
    private Card[] deck;
    private int topCard;
    private int deckSize;
    public DeckData() {
        deck = new Card[52];
        resetDeck();
    }

    public void resetDeck() {
        int index = 0;

        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 12; j++) {
                deck[index++] = new Card(i, j);
            }
        }

        topCard = 0;
        shuffle();
    }
    public void shuffle() {
        Random rand = new Random();

        // Fisher-Yates shuffle
        for (int i = deck.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public Card dealCard() {
        if (topCard >= deck.length) {
            resetDeck();
        }
        return deck[topCard++];
    }

    public int cardsLeftInDeck() {
        return deck.length - topCard;
    }

    public Card peekTopCard() {
        return deck[topCard];
    }

    public Card peekPreviousCard() {
        if (topCard > 0) return deck[topCard - 1];
        return null;
    }

}
