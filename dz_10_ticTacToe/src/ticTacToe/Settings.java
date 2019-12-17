package ticTacToe;

import java.util.Arrays;
import java.util.List;

final class Settings {
    static final int n = 2;
    static final int m = 2;
    static final int k = 3;
    static final int c = 2; // круги в турнире
    static final boolean tour = false;
    static final boolean log = false;
    static final List<Player> players = Arrays.asList(new HumanPlayer());
    static final boolean romb = true;
}
