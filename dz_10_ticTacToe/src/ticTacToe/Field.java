package ticTacToe;

import java.util.Arrays;

public class Field implements Position{
    private final Board board;
    Field(Board board){
        this.board = board;
    }
    @Override
    public boolean isValid(final Move move) {
        return board.isValid(move);
    }
    @Override
    public String toString(){
        return board.toString();
    }
}
