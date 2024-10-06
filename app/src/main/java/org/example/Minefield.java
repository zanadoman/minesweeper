package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import javafx.scene.layout.GridPane;

public class Minefield {
    public Minefield(GridPane gridPane, int width, int height) {
        Random random = new Random();
        Width = width;
        Height = height;
        _minefield = new Mine[Width][Height];
        forEachMine((i, j) -> {
            _minefield[i][j] = new Mine(i, j, 0.8 < random.nextDouble());
            gridPane.add(_minefield[i][j].getButton(), i, j);
            _minefield[i][j].getButton().setOnMouseClicked(event -> {
                switch (event.getButton()) {
                    case PRIMARY:
                        clear(i, j);
                        break;
                    case SECONDARY:
                        _minefield[i][j].flag();
                        break;
                    default:
                        break;
                }
            });
        });
        forEachMine((i, j) -> {
            _minefield[i][j].setNeighbourCount(getNeighbourCount(i, j));
        });
    }

    public boolean clear(int x, int y) {
        if (_minefield[x][y].IsCleared()) {
            return true;
        }
        _minefield[x][y].clear();
        if (_minefield[x][y].hasMine) {
            forEachMine((i, j) -> clear(i, j));
            return false;
        }
        forEachNeighbour(x, y, (i, j) -> explore(i, j));
        return true;
    }

    private void explore(int x, int y) {
        if (!validatePosition(x, y) || _minefield[x][y].hasMine ||
                _minefield[x][y].IsCleared()) {
            return;
        }
        _minefield[x][y].clear();
        if (_minefield[x][y].getNeighbourCount() == 0) {
            forEachNeighbour(x, y, (i, j) -> explore(i, j));
        }
    }

    private boolean validatePosition(int x, int y) {
        return 0 <= x && x < Width && 0 <= y && y < Height;
    }

    private void forEachNeighbour(int x, int y,
            BiConsumer<Integer, Integer> operation) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (validatePosition(i, j) && (i != x || j != y))
                    operation.accept(i, j);
            }
        }
    }

    private void forEachMine(BiConsumer<Integer, Integer> operation) {
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                operation.accept(i, j);
            }
        }
    }

    private int getNeighbourCount(int x, int y) {
        AtomicInteger neighbourCount = new AtomicInteger(0);
        forEachNeighbour(x, y, (i, j) -> {
            if (_minefield[i][j].hasMine) {
                neighbourCount.incrementAndGet();
            }
        });
        return neighbourCount.get();
    }

    final int Width;
    final int Height;
    private Mine[][] _minefield;
}
