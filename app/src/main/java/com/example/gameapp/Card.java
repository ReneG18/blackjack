package com.example.gameapp;

import android.content.Context;
import java.util.Map;
import java.util.HashMap;

public class Card {
    private int cardSuit;
    private int cardValue;

    private static final Map<String, Integer> card_Map = new HashMap<>();
    public Card(int cS, int cV){
        cardSuit = cS;
        cardValue = cV;
    }

    //create card map
    public static void initCardMap(Context context) {
        // If the map is already full, don't do it again
        if (!card_Map.isEmpty()) return;

        String[] suits = {"spades", "hearts", "diamonds", "clubs"};
        String[] values = {
                "two", "three", "four", "five", "six", "seven",
                "eight", "nine", "ten", "jack", "queen", "king", "ace"
        };

        for (String s : suits) {
            for (String v : values) {
                String key = s + "_" + v;
                // Use getIdentifier just once per card to find its ID
                int resId = context.getResources().getIdentifier(key, "drawable", context.getPackageName());
                if (resId != 0) {
                    card_Map.put(key, resId);
                }
            }
        }
    }

    //getter functions for calling card suits and values
    public int getCardSuit(){ return cardSuit;}
    public int getCardValue(){ return cardValue;}

    //get the suit of the card in a string and returns to the call
    public String getSuitAsString(){
        switch(cardSuit){
            case 3: return "Spades";
            case 2: return "Hearts";
            case 1: return "Diamonds";
            case 0: return "Clubs";
            default: return "no suit";
        }
    }

    //gets the card value as a string and returns to the call
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

    // Return a card back or placeholder if the specific card isn't found
    public int getCardDrawableId() {
        String key = getSuitAsString().toLowerCase() + "_" + getValueAsString().toLowerCase();
        Integer id = card_Map.get(key);
        return (id != null) ? id : R.drawable.card_back;
    }

}
