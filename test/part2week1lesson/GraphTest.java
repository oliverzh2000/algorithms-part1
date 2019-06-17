package part2week1lesson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {
    Graph g0;
    Graph g2;
    Graph g3;

    @BeforeEach
    void setUp() {
        this.g0 = new Graph(0);

        this.g2 = new Graph(2);
        g2.addEdge(0, 1);

        this.g3 = new Graph(3);
        g3.addEdge(0, 1);
        g3.addEdge(1, 2);
    }

    @Test
    void addEdge() {

    }

    @Test
    void adj() {
    }

    @Test
    void v() {
    }

    @Test
    void e() {
    }

    @Test
    void toStringTest() {
    }
}