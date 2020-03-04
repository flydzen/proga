package ticTacToe;

import java.io.PrintStream;
import java.nio.channels.Channel;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    public void print(String s){
        out.println(s);
    }

    private int[] parser(String[] xy){
        int[] num = new int[2];
        try{
            num[0] = Integer.parseInt(xy[0]);
            num[1] = Integer.parseInt(xy[1]);
        } catch (NumberFormatException e){
            System.out.print("Неправильный ввод");
            num[0] = -1;
            num[1] = -1;
        }
        return num;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        //((mnkBoard)position).makeMove(new Move(1, 1, Cell.O));
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            String[] xy = in.nextLine().split(" ");
            if (xy.length != 2){
                System.out.println("Кривой ввод");
                continue;
            }
            int[] num = new int[2];
            try{
                num[0] = Integer.parseInt(xy[0]);
                num[1] = Integer.parseInt(xy[1]);
            } catch (NumberFormatException e){
                System.out.print("Неправильный ввод");
                continue;
            }
            final Move move = new Move(num[0], num[1], cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
