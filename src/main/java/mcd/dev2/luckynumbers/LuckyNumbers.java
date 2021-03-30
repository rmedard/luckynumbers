package mcd.dev2.luckynumbers;

import mcd.dev2.luckynumbers.controller.Controller;
import mcd.dev2.luckynumbers.model.Game;
import mcd.dev2.luckynumbers.model.Model;
import mcd.dev2.luckynumbers.view.MyView;

public class LuckyNumbers {

    public static void main(String[] args) {
        Model game = new Game();
        Controller controller = new Controller(game, new MyView(game));
        controller.play();
    }
}
