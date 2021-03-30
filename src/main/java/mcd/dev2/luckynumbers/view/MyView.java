package mcd.dev2.luckynumbers.view;

import mcd.dev2.luckynumbers.model.Model;
import mcd.dev2.luckynumbers.model.Position;
import mcd.dev2.luckynumbers.model.Tile;

import java.util.Scanner;

public class MyView implements View {

    private Model game;

    public MyView(Model game) {
        this.game = game;
    }

    @Override
    public void displayWelcome() {
        System.out.println("Welcome to LuckyNumbers Game");
        System.out.println("Version: LuckyNumbers 1.0");
        System.out.printf("Author: %s\n", "Léon Fashingabo");
    }

    @Override
    public void displayGame() {
        int currentPlayer = game.getCurrentPlayerNumber();
        System.out.printf("Joueur %d\n", currentPlayer);
        int boardSize = game.getBoardSize();
        System.out.print("\t");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + 1);
            System.out.print(" ");
        }
        StringBuilder line = new StringBuilder("\n---");
        for (int i = 0; i < game.getBoardSize(); i++) {
            line.append("--");
        }
        System.out.println(line);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize + 1; j++) {
                if (j == 0) {
                    System.out.print(i + 1);
                    System.out.print("| ");
                } else {
                    Tile value = game.getTile(currentPlayer, new Position(i, j - 1));
                    System.out.print(value == null ? "." : value.getValue());
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.print(line);
    }

    @Override
    public void displayWinner() {
        System.out.printf("Le gagnant est le joueur %d", game.getWinner());
    }

    @Override
    public int askPlayerCount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Combien de joueurs: ");
        int number = 0;
        if (scanner.hasNextInt()) {
            number = scanner.nextInt();
        }
        return number;
    }

    @Override
    public Position askPosition() {
        System.out.printf("\nTuile choisie: %d\n", game.getPickedTile().getValue());
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ligne: ");
        int row = Integer.parseInt(scanner.next());
        System.out.print("Colonne: ");
        int column = Integer.parseInt(scanner.next());
        Position position = new Position(row, column);
        if (game.isInside(position)) {
            return position;
        } else {
            displayError("La position est invalide");
            return askPosition();
        }
    }

    @Override
    public void displayError(String message) {
        System.err.println(message);
    }
}
