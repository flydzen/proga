package ticTacToe;

import java.io.EOFException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws EOFException {
        if (Settings.players.size() == 0) {
            System.out.println("Некому играть");
            return;
        } else if (Settings.players.size() == 1) {
            System.out.println("Player1 выйграл энивэй. Хотя бы не проиграл");
            return;
        }
        for (Object player : Settings.players) {
            if (player == null || player.getClass() != Player.class) {
                System.out.println("Ошибка в игроках");
                return;
            }
        }
        if (!Settings.tour) {
            final Game game = new Game(Settings.players);
            int result;
            System.out.println("6@m3 nachalas'");
            result = game.play(new mnkBoard(Settings.n, Settings.m, Settings.k, Settings.players.size()));
            System.out.println("P1@y3r " + result + " w0n");
        } else {
            final Tournament tour = new Tournament(Settings.players);
            int result = tour.play();
            if (result == -1) {
                System.out.println("No winners");
            } else {
                System.out.println("YEEEEEEEEEEEEEEEEEEEEAH");
                System.out.println("***********************************");
                System.out.println("***********************************");
                System.out.println("***********************************");
                System.out.println("PLAYER " + (result + 1) + " --- WINNER");
                System.out.println("PLAYER " + (result + 1) + " --- WINNER");
                System.out.println("PLAYER " + (result + 1) + " --- WINNER");
                System.out.println("PLAYER " + (result + 1) + " --- WINNER");
                System.out.println("PLAYER " + (result + 1) + " --- WINNER");
                System.out.println("###################################");
                System.out.println("###################################");
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            }
        }
    }
}
