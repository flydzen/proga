package ticTacToe;

public enum Cell {
    X ("X"), O ("O"), G("&"), A("@"), M ("-"), I ("|"), U("*"), E("□"), None(" ");

    private String title;

    Cell(String x) {
        this.title = x;
    }

    @Override
    public String toString() {
        return title;
    }
}
