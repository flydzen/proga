package ticTacToe;

import java.util.Arrays;
import java.util.List;

final class Settings {
    static final int n = 1;
    static final int m = 1;
    static final int k = 3;
    static final int c = 2; // круги в турнире
    static final boolean tour = false;
    static final boolean log = false;
    static final List<Player> players = Arrays.asList(new HumanPlayer(), new RandomPlayer());
    static final boolean romb = true;
}
