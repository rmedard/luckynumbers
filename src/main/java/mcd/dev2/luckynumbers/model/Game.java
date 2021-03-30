package mcd.dev2.luckynumbers.model;

import java.util.Random;

public class Game implements Model {

    private int playerCount;
    private int currentPlayerNumber;
    private State state;
    private Tile pickedTile;
    private Board[] boards;

    public Game() {
        state = State.NOT_STARTED;
    }

    Tile pickTile(int value) {
        if (state != State.PICK_TILE) {
            throw new IllegalStateException("Invalid game state");
        }
        state = State.PLACE_TILE;
        pickedTile = new Tile(value);
        return getPickedTile();
    }

    @Override
    public void start(int playerCount) {
        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("The number of players must be between 2 and 4");
        }

        if (state != State.NOT_STARTED && state != State.GAME_OVER ) {
            throw new IllegalStateException("Invalid game state.");
        }

        this.playerCount = playerCount;
        boards = new Board[playerCount];
        for (int i = 0; i < boards.length; i++) {
            boards[i] = new Board();
        }
        currentPlayerNumber = 0;
        state = State.PICK_TILE;
    }

    @Override
    public int getBoardSize() {
        return boards[getCurrentPlayerNumber()].getSize();
    }

    @Override
    public Tile pickTile() {
        if (state != State.PICK_TILE) {
            throw new IllegalStateException("Invalid game state");
        }
        state = State.PLACE_TILE;
        pickedTile = new Tile(new Random().nextInt(20) + 1);
        return getPickedTile();
    }

    @Override
    public void putTile(Position pos) {
        Board board = boards[getCurrentPlayerNumber()];
        if (!board.isInside(pos)) {
            throw new IllegalArgumentException("Invalid position");
        }
        if (state != State.PLACE_TILE) {
            throw new IllegalStateException("Invalid game state");
        }
        board.put(getPickedTile(), pos);
        state = board.isFull() ? State.GAME_OVER : State.TURN_END;
    }

    @Override
    public void nextPlayer() {
        if (state != State.TURN_END) {
            throw new IllegalStateException("Invalid game state");
        }
        int newPlayerNumber = getCurrentPlayerNumber() + 1;
        currentPlayerNumber = newPlayerNumber > getPlayerCount() - 1 ? 0 : newPlayerNumber;
        state = State.PICK_TILE;
    }

    @Override
    public int getPlayerCount() {
        if (state == State.NOT_STARTED) {
            throw new IllegalArgumentException("Invalid game state");
        }
        return playerCount;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public int getCurrentPlayerNumber() {
        if (state == State.NOT_STARTED || state == State.GAME_OVER) {
            throw new IllegalArgumentException("Invalid game state");
        }
        return currentPlayerNumber;
    }

    @Override
    public Tile getPickedTile() {
        if (state != State.PLACE_TILE) {
            throw new IllegalStateException("Invalid game state");
        }
        return pickedTile;
    }

    @Override
    public boolean isInside(Position pos) {
        Board board = boards[getCurrentPlayerNumber()];
        return board.isInside(pos);
    }

    @Override
    public boolean canTileBePut(Position pos) {
        Board board = boards[getCurrentPlayerNumber()];
        if (!board.isInside(pos)) {
            throw new IllegalArgumentException("Invalid position");
        }
        if (state != State.PLACE_TILE) {
            throw new IllegalStateException("Invalid game state");
        }
        return board.canBePut(pickedTile, pos);
    }

    @Override
    public Tile getTile(int playerNumber, Position pos) {
        if (playerNumber < 0 || playerNumber > getPlayerCount() - 1) {
            throw new IllegalArgumentException("Invalid player number");
        }
        if (state == State.NOT_STARTED) {
            throw new IllegalStateException("Invalid game state");
        }
        Board board = boards[playerNumber];
        return board.getTile(pos);
    }

    @Override
    public int getWinner() {
        if (state != State.GAME_OVER) {
            throw new IllegalStateException("Invalid game state");
        }
        int winner = 0;
        for (int i = 0; i < boards.length; i++) {
            if (boards[i].isFull()) {
                winner = i;
                break;
            }
        }
        return winner;
    }
}
