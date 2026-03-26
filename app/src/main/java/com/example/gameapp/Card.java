package com.example.gameapp;

import android.content.Context;

public class Card {
    private int cardSuit;
    private int cardValue;
    public Card(int cS, int cV){
        cardSuit = cS;
        cardValue = cV;
    }
    public int getCardSuit(){ return cardSuit;}
    public int getCardValue(){ return cardValue;}

    public String getSuitAsString(){
        switch(cardSuit){
            case 3: return "Spades";
            case 2: return "Hearts";
            case 1: return "Diamonds";
            case 0: return "Clubs";
            default: return "no suit";
        }
    }

    public String getValueAsString(){
        switch(cardValue){
            case 0: return "Two";
            case 1: return "Three";
            case 2: return "Four";
            case 3: return "Five";
            case 4: return "Six";
            case 5: return "Seven";
            case 6: return "Eight";
            case 7: return "Nine";
            case 8: return "Ten";
            case 9: return "Jack";
            case 10: return "Queen";
            case 11: return "King";
            case 12: return "Ace";
            default: return "unknown";
        }
    }

    public int getCardDrawableId(Context context) {
        String drawableName = getSuitAsString().toLowerCase() + "_" + getValueAsString().toLowerCase();
        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }


}
