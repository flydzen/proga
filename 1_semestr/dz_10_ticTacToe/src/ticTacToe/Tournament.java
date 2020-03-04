package ticTacToe;

import java.io.EOFException;
import java.util.*;

class Tournament {
    private final List<Player> players;
    private int[] scores;

    public Tournament(List<Player> players){
        this.players = players;
        this.scores = new int[players.size()];
    }

    public int play() throws EOFException {
        for(int i = 0; i < Settings.c; i++){
            System.out.println("Circle " + (i + 1));
            for (int j = 0; j < players.size()-1; j++){
                for (int k = j+1; k < players.size(); k++){
                    System.out.println("Player " + (j+1) + " VS Player " + (k+1));
                    List<Player> pair;
                    if ((j+k+i)%2==1){
                        pair = Arrays.asList(players.get(j), players.get(k));
                    }else{
                        pair = Arrays.asList(players.get(k), players.get(j));
                    }
                    final Game game = new Game(pair);
                    int result = game.play(new mnkBoard(10, 10, 4, 2));
                    System.out.println(result + " won");
                    if (result == 0){
                        scores[j] += 1;
                        scores[k] += 1;
                    } else if (result == (j+k+i)%2){
                        scores[j] += 3;
                    } else{
                        scores[k] += 3;
                    }
                }
            }
        }
        int winner = 0;
        boolean foe = false;
        System.out.println("Player " + (1) + "  score: " + scores[0]);
        for (int i = 1; i < scores.length; i++) {
            System.out.println("Player " + (i+1) + "  score: " + scores[i]);
            if (scores[i] > scores[winner]){
                winner = i;
                foe = false;
            }else if(scores[i] == scores[winner]){
                foe = true;
            }
        }
        return foe? -1: winner;
    }
}
