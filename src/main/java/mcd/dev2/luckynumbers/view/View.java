package mcd.dev2.luckynumbers.view;

import mcd.dev2.luckynumbers.model.Position;

public interface View {

    void displayWelcome();
    void displayGame();
    void displayWinner();
    int askPlayerCount();
    Position askPosition();
    void displayError(String message);
}
