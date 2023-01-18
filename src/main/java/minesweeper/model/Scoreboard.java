package minesweeper.model;

public class Scoreboard {
    private int players;
    private int[] scores;

    public Scoreboard(int players) {
        this.players = players;
        scores = new int[players];
        for (int player=0; player<players; player++) {
            scores[player] = 0;
        }
    }

    public void setScore(int player, int points) {
        scores[player] = points;
    }

    public void addScore(int player, int points) {
        scores[player] += points;
    }

    public int getScore(int player) {
        return scores[player];
    }

    public int getWinner() {
        int max_score = 0;
        int winner = 0;
        for (int player=0; player<players; player++) {
            if (scores[player] > max_score) {
                max_score = scores[player];
                winner = player;
            }
        }
        return winner;
    }

    public int getPlayers() {
        return players;
    }
}
