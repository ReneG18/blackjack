package com.example.gameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BlackJackGame game;

    private LinearLayout dealerCardsLayout;
    private LinearLayout playerCardsLayout;
    private TextView resultText;
    private TextView statsText;

    private Button hitButton;
    private Button standButton;
    private Button newGameButton;

    private SharedPreferences prefs;

    private int wins = 0;
    private int losses = 0;
    private int gamesPlayed = 0;

    private ImageView dealerHiddenCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Card.initCardMap(this);

        game = new BlackJackGame();

        dealerCardsLayout = findViewById(R.id.dealerCards);
        playerCardsLayout = findViewById(R.id.playerCards);
        resultText = findViewById(R.id.resultText);
        statsText = findViewById(R.id.statsText);

        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);
        newGameButton = findViewById(R.id.newGameButton);

        prefs = getSharedPreferences("stats", MODE_PRIVATE);
        loadStats();
        updateStatsText();

        setButtonListeners();

        startNewGame();
    }

    private void setButtonListeners() {

        hitButton.setOnClickListener(v -> {
            Card c = game.playerHit();
            addCardToLayout(c, playerCardsLayout);

            if (game.getPlayerHand().isBust()) {
                endGame();
            }
        });

        standButton.setOnClickListener(v -> {
            game.dealerPlay();
            endGame();
        });

        newGameButton.setOnClickListener(v -> startNewGame());
    }

    private void startNewGame() {
        game.newGame();
        resultText.setText("");

        dealerCardsLayout.removeAllViews();
        playerCardsLayout.removeAllViews();
        dealerHiddenCardView = null;

        // Add dealer cards
        for (int i = 0; i < game.getDealerHand().getHand().size(); i++) {
            Card c = game.getDealerHand().getHand().get(i);
            ImageView cardView = new ImageView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(200, 300));
            cardView.setPadding(8, 0, 8, 0);

            if (i == 1) {
                // Second card face down
                cardView.setImageResource(R.drawable.card_back);
                dealerHiddenCardView = cardView;
            } else {
                cardView.setImageResource(c.getCardDrawableId());
            }

            dealerCardsLayout.addView(cardView);
        }

        // Add player cards
        for (Card c : game.getPlayerHand().getHand()) {
            addCardToLayout(c, playerCardsLayout);
        }

        hitButton.setEnabled(true);
        standButton.setEnabled(true);
    }


    private void endGame() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        game.dealerPlay();

        BlackJackGame.Result result = game.getResult();

        if (result == BlackJackGame.Result.PLAYER_WIN) {
            resultText.setText("You Win!");
            wins++;
        } else if (result == BlackJackGame.Result.DEALER_WIN) {
            resultText.setText("Dealer Wins!");
            losses++;
        } else {
            resultText.setText("Push (Tie)");
        }

        //flip dealer's second card
        if (dealerHiddenCardView != null) {
            Card secondCard = game.getDealerHand().getHand().get(1);

            dealerHiddenCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.flip));
            dealerHiddenCardView.postDelayed(() -> {
                dealerHiddenCardView.setImageResource(secondCard.getCardDrawableId());
                dealerHiddenCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.flip_back));
            }, 150);
        }

        //any remaining dealer cards beyond second
        for (int i = 2; i < game.getDealerHand().getHand().size(); i++) {
            addCardToLayout(game.getDealerHand().getHand().get(i), dealerCardsLayout);
        }

        gamesPlayed++;
        saveStats();
        updateStatsText();
    }


    private void addCardToLayout(Card card, LinearLayout layout) {
        int drawableId = card.getCardDrawableId();

        if (drawableId == 0) {
            drawableId = R.drawable.card_back; // fallback
        }

        ImageView img = new ImageView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(200, 300));
        img.setPadding(8, 0, 8, 0);
        img.setImageResource(drawableId);
        layout.addView(img);

        img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void updateStatsText() {
        statsText.setText(
                "Wins: " + wins + "   Losses: " + losses + "   Games: " + gamesPlayed
        );
    }

    private void loadStats() {
        wins = prefs.getInt("wins", 0);
        losses = prefs.getInt("losses", 0);
        gamesPlayed = prefs.getInt("games", 0);
    }

    private void saveStats() {
        prefs.edit()
                .putInt("wins", wins)
                .putInt("losses", losses)
                .putInt("games", gamesPlayed)
                .apply();
    }
}
