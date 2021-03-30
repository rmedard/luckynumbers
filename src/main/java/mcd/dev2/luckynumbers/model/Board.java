package mcd.dev2.luckynumbers.model;

public class Board {

    private final Tile[][] tiles;

    public Board() {
        tiles = new Tile[getSize()][getSize()];
    }

    public int getSize() {
        return 4;
    }

    public boolean isInside(Position position) {
        return position.getColumn() >= 0 && position.getColumn() < getSize()
                && position.getRow() >= 0 && position.getRow() < getSize();
    }

    public Tile getTile(Position position) {
        return tiles[position.getRow()][position.getColumn()];
    }

    public boolean canBePut(Tile tile, Position position) {
        if (tiles[position.getRow()][position.getColumn()] == null) {
            return !hasSameOrBiggerValueLeft(tile, position)
                    && !hasSameOrBiggerValueUp(tile, position)
                    && !hasSameOrSmallerValueRight(tile, position)
                    && !hasSameOrSmallerValueDown(tile, position);
        }
        return true;
    }

    public void put(Tile tile, Position position) {
        tiles[position.getRow()][position.getColumn()] = tile;
    }

    public boolean isFull() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                Tile tile = tiles[i][j];
                if (tile == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasSameOrBiggerValueLeft(Tile tile, Position position) {
        Position leftPosition = new Position(position.getRow(), position.getColumn() - 1);
        if (isInside(leftPosition)) {
            Tile leftTile = getTile(leftPosition);
            if (leftTile == null || leftTile.getValue() < tile.getValue()) {
                return hasSameOrBiggerValueLeft(tile, leftPosition);
            }
            return true;
        }
        return false;
    }

    private boolean hasSameOrSmallerValueRight(Tile tile, Position position) {
        Position rightPosition = new Position(position.getRow(), position.getColumn() + 1);
        if (isInside(rightPosition)) {
            Tile rightTile = getTile(rightPosition);
            if (rightTile == null || rightTile.getValue() > tile.getValue()) {
                return hasSameOrSmallerValueRight(tile, rightPosition);
            }
            return true;
        }
        return false;
    }

    private boolean hasSameOrBiggerValueUp(Tile tile, Position position) {
        Position upPosition = new Position(position.getRow() - 1, position.getColumn());
        if (isInside(upPosition)) {
            Tile upTile = getTile(upPosition);
            if (upTile == null || upTile.getValue() < tile.getValue()) {
                return hasSameOrBiggerValueUp(tile, upPosition);
            }
            return true;
        }
        return false;
    }

    private boolean hasSameOrSmallerValueDown(Tile tile, Position position) {
        Position downPosition = new Position(position.getRow() + 1, position.getColumn());
        if (isInside(downPosition)) {
            Tile downTile = getTile(downPosition);
            if (downTile == null || downTile.getValue() > tile.getValue()) {
                return hasSameOrSmallerValueDown(tile, downPosition);
            }
            return true;
        }
        return false;
    }
}
