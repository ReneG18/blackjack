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

            if (v <= 7) {
                //values 0-7 -> cards 2-9
                total += (v + 2);
            }
            else if (v <= 11) {
                //values 8-11 -> 10,j,q,k
                total += 10;
            }
            else{
                //ace cards
                aces++;
            }
        }

        // Handling aces
        for (int i = 0; i < aces; i++) {
            total += (total + 11 <= 21) ? 11 : 1;
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

