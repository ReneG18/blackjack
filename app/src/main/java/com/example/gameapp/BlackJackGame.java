package com.example.gameapp;

public class BlackJackGame {
    private DeckData deck;
    private BlackJack playerHand;
    private BlackJack dealerHand;

    public enum Result{
        PLAYER_WIN,
        DEALER_WIN,
        PUSH
    }
    public BlackJackGame(){
        deck = new DeckData();
        playerHand = new BlackJack();
        dealerHand = new BlackJack();
    }
    public void newGame(){
        playerHand.resetHand();
        dealerHand.resetHand();

        if (deck.cardsLeftInDeck() < 15) {
            deck.resetDeck();
        }

        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
    }
    public Card playerHit() {
        Card c = deck.dealCard();
        playerHand.addCard(c);
        return c;
    }

//    public Card dealerHit() {
//        Card c = deck.dealCard();
//        dealerHand.addCard(c);
//        return c;
//    }

    public BlackJack getPlayerHand() {
        return playerHand;
    }

    public BlackJack getDealerHand() {
        return dealerHand;
    }

    public void dealerPlay() {
        while (dealerHand.getHandValue() < 17) {
            dealerHand.addCard(deck.dealCard());
        }
    }

    public Result getResult() {

        int playerTotal = playerHand.getHandValue();
        int dealerTotal = dealerHand.getHandValue();

        if (playerHand.isBust()) {
            return Result.DEALER_WIN;
        }

        if (dealerHand.isBust()) {
            return Result.PLAYER_WIN;
        }

        if (playerTotal > dealerTotal) {
            return Result.PLAYER_WIN;
        } else if (dealerTotal > playerTotal) {
            return Result.DEALER_WIN;
        } else {
            return Result.PUSH;
        }
    }
}
