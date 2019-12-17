package ticTacToe;

import java.io.EOFException;

public interface Board extends Position{
    Position getPosition();
    Cell getCell();
    Result makeMove(Move move);
}
