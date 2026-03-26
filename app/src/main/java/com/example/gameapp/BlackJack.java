package com.example.gameapp;

import java.util.ArrayList;
public class BlackJack {
    private ArrayList<Card> hand;
    public BlackJack(){ hand = new ArrayList<>();}
    public void resetHand(){ hand.clear();}
    public void addCard(Card card){ hand.add(card);}
    public ArrayList<Card> getHand(){ return hand;}

    public int getHandValue() {
        int total = 0;
        int aces = 0;

        //Calculating hand
        for (Card c : hand) {
            int v = c.getCardValue();

            if (v >= 0 && v <= 7) {
                total += (v + 2);
            }
            else if (v >= 8 && v <= 10) {
                total += 10;
            }
            else if (v == 11) {
                total += 10;
            }
            else if (v == 12) {
                aces++;
            }
        }

        // Handling aces
        for (int i = 0; i < aces; i++) {
            if (total + 11 <= 21) {
                total += 11;
            } else {
                total += 1;
            }
        }

        return total;
    }
    public boolean isBlackjack() {
        return (hand.size() == 2 && getHandValue() == 21);
    }

    public boolean isBust() {
        return getHandValue() > 21;
    }
}

