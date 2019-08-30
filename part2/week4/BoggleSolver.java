import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TrieST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoggleSolver {
    private static final int R = 26;

    private final Set<String> dict;
    private boolean[][] marked; // for the dfs

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        // TODO: optimize the dict to use a trie, to support fast prefix queries
        for (int i = 0; i < dictionary.length; i++) {
            dictionary[i] = dictionary[i].replace("QU", "Q");
        }
        dict = new HashSet<>(Arrays.asList(dictionary));
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        List<String> validWords = new ArrayList<>();
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                marked = new boolean[board.rows()][board.cols()];
                validWords.addAll(dfs(board, "", row, col));
            }
        }
        validWords = validWords.stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < validWords.size(); i++) {
            validWords.set(i, validWords.get(i).replace("Q", "QU"));
        }
        return validWords;
    }

    private List<String> dfs(BoggleBoard board, String currentWord, int row, int col) {
        marked[row][col] = true;
        currentWord += board.getLetter(row, col);
        List<String> validWords = new ArrayList<>();
        if (dict.contains(currentWord) && currentWord.length() >= 3) {
            validWords.add(currentWord);
        }

        int[] directions = {-1, 0, 1};
        for (int xDir : directions) {
            for (int yDir : directions) {
                int adjRow = row + yDir;
                int adjCol = col + xDir;
                if (isInRange(board, adjRow, adjCol) && !marked[adjRow][adjCol] && !(xDir == 0 && yDir == 0)) {
                    validWords.addAll(dfs(board, currentWord, adjRow, adjCol));
                }
            }
        }
        marked[row][col] = false;
        return validWords;
    }

    private boolean isInRange(BoggleBoard board, int x, int y) {
        return 0 <= x && x < board.cols() && 0 <= y && y < board.rows();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int[] lengthScores = {0, 0, 0, 1, 1, 2, 3, 5, 11};
        if (dict.contains(word.replace("QU", "Q"))) {
            if (word.length() >= 8) {
                return 11;
            }
            return lengthScores[word.length()];
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In("boggle/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("boggle/board-q.txt");
        int score = 0;
        int count = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            int s = solver.scoreOf(word);
            if (s == 0) {
                solver.scoreOf(word);
            }
            score += s;
            count++;
        }
        StdOut.println("Score = " + score + " Count = " + count);
    }
}