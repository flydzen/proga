package ticTacToe;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class mnkBoard implements Board {
    private final Cell[][] cells;
    private Cell[] chips;
    private final int n, m, k;
    private int free;
    private int turn;
    private final int players;

    public mnkBoard(int n, int m, int k, int cou) throws EOFException {
        if (Settings.romb){
            n = n*2-1;
            m = n;
        }
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        if (Settings.romb) {
            int temp = n/2;
            for (int i = 0; i < n/2; i++){
                for (int j = 0; j < temp; j++){
                    cells[i][j] = Cell.None;
                    cells[i][n - 1 - j] = Cell.None;
                    cells[n - 1 - i][j] = Cell.None;
                    cells[n - 1 - i][n - 1 - j] = Cell.None;
                }
                temp--;
            }
        }

        this.k = k;
        this.players = cou;
        this.m = m;
        this.n = n;
        this.free = n*m;
        this.turn = 0;
        chips = Cell.values();
        if (cou > 7){
            throw new EOFException("Слишком много игроков");
        }
    }

    public Board newBoard() throws EOFException {
        return new mnkBoard(n,m,k,players);
    }

    @Override
    public Position getPosition() {
        return new Field(this);
    }

    @Override
    public Cell getCell() {
        return chips[turn];
    }

    private boolean check(int x, int y, Cell v){
        int ver = 0;
        int hor = 0;
        int d1 = 0;
        int d2 = 0;
        for (int i = -k+1; i < k; i++){
            hor += (x+i >= 0 && x+i < m && cells[x+i][y] == v) ? 1 : -hor;
            ver += (y+i >= 0 && y+i < n && cells[x][y+i] == v) ? 1 : -ver;
            d1 += (x+i >= 0 && x+i < m && y+i >= 0 && y+i < n &&  cells[x+i][y+i] == v) ? 1 : -d1;
            d2 += (x-i >= 0 && x-i < m && y+i >= 0 && y+i < n &&  cells[x-i][y+i] == v) ? 1 : -d2;
            if (hor == k || ver == k || d1 == k || d2 == k){
                return true;
            }
        }
        return false;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        free--;
        cells[move.getRow()][move.getColumn()] = move.getValue();
        if (check(move.getRow(), move.getColumn(), move.getValue())){
            return Result.WIN;
        } else if (free == 0){
            return Result.DRAW;
        } else {
            turn = (turn+1)%players;
            return Result.UNKNOWN;
        }
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getColumn() && move.getColumn() < m
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && chips[turn] == getCell();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("   ");
        for (int i = 0; i < m; i++){
            sb.append(String.format("%3d", i));
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(String.format("%3d", r));
            for (int c = 0; c < m; c++) {
                sb.append(String.format("%3s", cells[r][c]));
            }
        }
        return sb.toString();
    }
}
