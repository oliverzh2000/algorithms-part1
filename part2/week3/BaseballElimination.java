import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseballElimination {
    private int n; // number of teamNames
    private int[] w; // wins
    private int[] l; // losses
    private int[] r; // remaining
    private int[][] g; // remaining games against each other team
    private HashMap<String, Integer> teamNameIds;
    private String[] teamNames;

    private boolean[] eliminated;
    private boolean[][] isInCertificate;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        teamNameIds = new HashMap<>();
        teamNames = new String[n];
        for (int team = 0; team < n; team++) {
            String name = in.readString();
            teamNameIds.put(name, team);
            teamNames[team] = name;
            w[team] = in.readInt();
            l[team] = in.readInt();
            r[team] = in.readInt();
            g[team] = new int[n];
            for (int remainingGame = 0; remainingGame < n; remainingGame++) {
                g[team][remainingGame] = in.readInt();
            }
        }

        eliminated = new boolean[n];
        isInCertificate = new boolean[n][n];
        // Compute elimination status and isInCertificate of elimination (if applicable) for all teamNames.
        for (int team = 0; team < n; team++) {
            System.out.println("team:" + team);
            // Trivial elimination: w[team] + r[team] < w[i], for some other team i
            for (int i = 0; i < n; i++) {
                if (i != team && w[team] + r[team] < w[i]) {
                    eliminated[team] = true;
                    isInCertificate[team][i] = true;
                }
            }
            // Nontrivial elimination: build the FlowNetwork.
            int gameVertices = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (i != team && j != team) {
                        gameVertices++;
                    }
                }
            }
            int teamVertices = n - 1;

            FlowNetwork flowNet = new FlowNetwork(2 + gameVertices + teamVertices);
            int source = 0;
            int target = flowNet.V() - 1;

            int capacityFromSource = 0;
            int gameVertex = 1;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (i != team && j != team) {
                        flowNet.addEdge(new FlowEdge(source, gameVertex, g[i][j]));
                        flowNet.addEdge(new FlowEdge(gameVertex, 1 + gameVertices + i, Double.MAX_VALUE));
                        flowNet.addEdge(new FlowEdge(gameVertex, 1 + gameVertices + j, Double.MAX_VALUE));
                        capacityFromSource += g[i][j];
                        gameVertex++;
                    }
                }
            }
            int teamVertex = 1 + gameVertices;
            for (int i = 0; i < n; i++) {
                if (i != team) {
                    flowNet.addEdge(new FlowEdge(teamVertex, target, w[team] - w[i] + r[team]));
                    teamVertex++;
                }
            }
            System.out.println(flowNet);
            FordFulkerson fordFulkerson = new FordFulkerson(flowNet, source, target);

            if (fordFulkerson.value() < capacityFromSource) {
                eliminated[team] = true;
                gameVertex = 1;
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        if (i != team && j != team) {
                            if (fordFulkerson.inCut(gameVertex)) {
                                isInCertificate[team][i] = true;
                                isInCertificate[team][j] = true;
                            }
                            gameVertex++;
                        }
                    }
                }
            }
        }
    }

    // number of teamNames
    public int numberOfTeams() {
        return n;
    }

    // all teamNames
    public Iterable<String> teams() {
        return teamNameIds.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return w[teamNameIds.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return l[teamNameIds.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return r[teamNameIds.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return g[teamNameIds.get(team1)][teamNameIds.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return eliminated[teamNameIds.get(team)];
    }

    // subset R of teamNames that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated((team))) {
            return null;
        }
        List<String> certNames = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (isInCertificate[teamNameIds.get(team)][i]) {
                certNames.add(teamNames[i]);
            }
        }
        return certNames;
    }

    public static void main(String[] args) {
        BaseballElimination b = new BaseballElimination("baseball/teams5.txt");
        System.out.println(b.certificateOfElimination("Detroit"));
    }
}
