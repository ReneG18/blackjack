package com.example.gameapp;

import java.util.Random;
public class DeckData {
    private Card[] deck;
    private int topCard;
    private int deckSize;

    //deck constructor
    public DeckData() {
        //create a new deck with 52 cards
        deck = new Card[52];
        //call reset deck function to randomize the card set
        resetDeck();
    }


    public void resetDeck() {
        int index = 0;
        //suit of the card
        for (int i = 3; i >= 0; i--) {
            //value of the card
            for (int j = 0; j <= 12; j++) {
                //insert card into deck
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
