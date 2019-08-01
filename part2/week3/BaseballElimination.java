import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class BaseballElimination {
    private int n; // number of teams
    private int[] w; // wins
    private int[] l; // losses
    private int[] r; // remaining
    private int[][] g; // remaining games against each other team
    private HashMap<String, Integer> teamIds;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        teamIds = new HashMap<>();
        System.out.println(n);
        for (int teamId = 0; teamId < n; teamId++) {
            teamIds.put(in.readString(), teamId);
            w[teamId] = in.readInt();
            l[teamId] = in.readInt();
            r[teamId] = in.readInt();
            g[teamId] = new int[n];
            for (int remainingGame = 0; remainingGame < n; remainingGame++) {
                g[teamId][remainingGame] = in.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teamIds.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return w[teamIds.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return l[teamIds.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return r[teamIds.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return g[teamIds.get(team1)][teamIds.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination b = new BaseballElimination("baseball/teams4.txt");
        System.out.println(b);
    }
}
