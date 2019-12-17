package ticTacToe;

public interface Player {
    Move move(Position position, Cell cell);

    void print(String s);
}
