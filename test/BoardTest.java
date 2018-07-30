import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board goal;
    private Board board1;
    private Board board2;
    private Board priorityBoard;

    @BeforeEach
    void setUp() {
        goal = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        board1 = new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}});
        board2 = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        priorityBoard = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
    }

    @Test
    void hamming() {
        assertEquals(0, goal.hamming());
        assertEquals(5, priorityBoard.hamming());
    }

    @Test
    void manhattan() {
        assertEquals(0, goal.manhattan());
        assertEquals(10, priorityBoard.manhattan());
    }

    @Test
    void isGoal() {
        assertTrue(goal.isGoal());
        assertFalse(board1.isGoal());
        assertFalse(board2.isGoal());
        assertFalse(priorityBoard.isGoal());
    }

    @Test
    void twin() {

    }

    @Test
    void equals() {
        assertNotEquals(board1, board2);
        assertEquals(goal, new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}));
    }

    @Test
    void neighbors() {
        Board parent = new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}});
        Board n1 = new Board(new int[][]{{8, 1, 0}, {4, 2, 3}, {7, 6, 5}});
        Board n2 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Board n3 = new Board(new int[][]{{8, 1, 3}, {4, 2, 5}, {7, 6, 0}});
    }

    @Test
    void toStringTest() {
    }
}