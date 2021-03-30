package mcd.dev2.luckynumbers.controller;

import mcd.dev2.luckynumbers.model.Model;
import mcd.dev2.luckynumbers.model.Position;
import mcd.dev2.luckynumbers.view.View;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void play() {
        view.displayWelcome();
        while (true) {
            switch (model.getState()) {
                case NOT_STARTED:
                    int playerCount = view.askPlayerCount();
                    model.start(playerCount);
                    break;
                case PICK_TILE:
                    model.pickTile();
                    break;
                case PLACE_TILE:
                    Position position = view.askPosition();
                    model.putTile(position);
                    break;
                case TURN_END:
                    view.displayGame();
                    model.nextPlayer();
                    break;
                case GAME_OVER:
                    view.displayWinner();
                    return;
            }
        }
    }
}
