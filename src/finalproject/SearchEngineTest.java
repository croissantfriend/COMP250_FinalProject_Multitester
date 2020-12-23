package finalproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class SearchEngineTest {
    SearchEngine actualEngine;
    SearchEngine expectedEngine;

   /*
    * For tester to work add the following constructor to SearchEngine.java


     public SearchEngine(){
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
	}

	*
    */

    @BeforeEach
    void setup() {
//        System.out.println("Setting up...");
        try {
            actualEngine = new SearchEngine("Test1.xml");
            actualEngine.crawlAndIndex("C");
//            actualEngine.parser = new XmlParser("Test1.xml");
//            System.out.println(actualEngine.parser.getContent("C"));
        }
        catch (Exception e) {
            System.out.println("Exception Found: " + e.toString() + "\n");
            e.printStackTrace();
        }

        try {
            expectedEngine = new SearchEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        expectedEngine.internet.addVertex("A");
        expectedEngine.internet.addVertex("B");
        expectedEngine.internet.addVertex("C");
        expectedEngine.internet.addVertex("D");
        expectedEngine.internet.addEdge("A", "B");
        expectedEngine.internet.addEdge("A", "C");
        expectedEngine.internet.addEdge("B", "A");
        expectedEngine.internet.addEdge("B", "C");
        expectedEngine.internet.addEdge("B", "D");
        expectedEngine.internet.addEdge("C", "A");
        expectedEngine.internet.addEdge("D", "C");
        expectedEngine.internet.setPageRank("A", 1.2702546296296298);
        expectedEngine.internet.setPageRank("B", 0.8194444444444444);
        expectedEngine.internet.setPageRank("C", 1.2745949074074074);
        expectedEngine.internet.setPageRank("D", 0.6357060185185185);

        expectedEngine.wordIndex.put("A", expectedEngine.internet.getNeighbors("A"));
        expectedEngine.wordIndex.put("B", expectedEngine.internet.getNeighbors("B"));
        expectedEngine.wordIndex.put("C", expectedEngine.internet.getNeighbors("C"));
        expectedEngine.wordIndex.put("D", expectedEngine.internet.getNeighbors("D"));
    }

    @AfterEach
    void tearDown() {
//        System.out.println("Tearing down...");
        setup();

        for (String s : expectedEngine.internet.getVertices()){
            actualEngine.internet.setPageRank(s, 1.0);
        }
    }

    @Test
    void crawlAndIndexSizeAnVerticesEqual() {
        assertAll(
                () -> assertTrue(verifyEqual(expectedEngine.internet.getVertices(), actualEngine.internet.getVertices()) && expectedEngine.internet.getVertices().size() == actualEngine.internet.getVertices().size(), "Size and Vertices of Graph are Correct")
        );

    }

    @Test
    void crawlAndIndexInEdgesCorrect() {
        assertAll(
                () -> assertTrue(verifyEqual(expectedEngine.internet.getEdgesInto("A"), actualEngine.internet.getEdgesInto("A")) &&
                        verifyEqual(expectedEngine.internet.getEdgesInto("B"), actualEngine.internet.getEdgesInto("B")) &&
                        verifyEqual(expectedEngine.internet.getEdgesInto("C"), actualEngine.internet.getEdgesInto("C")) &&
                        verifyEqual(expectedEngine.internet.getEdgesInto("D"), actualEngine.internet.getEdgesInto("D")),
                        "EdgesInto() each Vertex Correct")
        );

    }

    @Test
    void crawlAndIndexOutEdgesCorrect() {
        assertAll(
                () -> assertTrue(verifyEqual(expectedEngine.internet.getNeighbors("A"), actualEngine.internet.getNeighbors("A")) &&
                        verifyEqual(expectedEngine.internet.getNeighbors("B"), actualEngine.internet.getNeighbors("B")) &&
                        verifyEqual(expectedEngine.internet.getNeighbors("C"), actualEngine.internet.getNeighbors("C")) &&
                        verifyEqual(expectedEngine.internet.getNeighbors("D"), actualEngine.internet.getNeighbors("D")),
                        "EdgesOutOf each Vertex Correct aka getNeighbours()")
        );

    }

    @Test
    void crawlAndIndexAdd() {
        actualEngine.internet.addVertex("E");
        actualEngine.internet.addEdge("E", "A");
        actualEngine.internet.addEdge("E", "D");
        actualEngine.internet.addEdge("A", "E");

        assertAll(
                () -> assertTrue(5 == actualEngine.internet.getVertices().size() && Arrays.asList("A", "B", "C", "D", "E").equals(actualEngine.internet.getVertices()) && Arrays.asList("B", "C", "E").equals(actualEngine.internet.getEdgesInto("A")),
                        "AddVertex(E) to graph with certain edges. Graph updated accordingly")
        );

    }

    public boolean verifyEqual(ArrayList<String> list1, ArrayList<String> list2) {
        int c = 0;
        for (String s : list1) {
            if (!s.equals(list2.get(c))) return false;
            c++;
        }
        return true;
    }

    public boolean verifyEqualDouble(ArrayList<Double> list1, ArrayList<Double> list2) {
        int c = 0;
        for (Double s : list1) {
            if (!s.equals(list2.get(c))) return false;
            c++;
        }
        return true;
    }


    @Test
    void assignPageRanks() {
        actualEngine.assignPageRanks(0.01);
        System.out.println("url A:\nActual: " + actualEngine.internet.getPageRank("A") + "\nExpected: " + expectedEngine.internet.getPageRank("A"));
        System.out.println("url B:\nActual: " + actualEngine.internet.getPageRank("B") + "\nExpected: " + expectedEngine.internet.getPageRank("B"));
        System.out.println("url C:\nActual: " + actualEngine.internet.getPageRank("C") + "\nExpected: " + expectedEngine.internet.getPageRank("C"));
        System.out.println("url D:\nActual: " + actualEngine.internet.getPageRank("D") + "\nExpected: " + expectedEngine.internet.getPageRank("D"));
        assertAll(
                () -> assertTrue(roundAvoid(actualEngine.internet.getPageRank("A"), 3) == roundAvoid(expectedEngine.internet.getPageRank("A"), 3) &&
                        roundAvoid(actualEngine.internet.getPageRank("B"), 3) == roundAvoid(expectedEngine.internet.getPageRank("B"),3) &&
                        roundAvoid(actualEngine.internet.getPageRank("C"), 3) == roundAvoid(expectedEngine.internet.getPageRank("C"),3) &&
                        roundAvoid(actualEngine.internet.getPageRank("D"), 3) == roundAvoid(expectedEngine.internet.getPageRank("D"),3)
                , "Correct Rank assignment of each Vertex")
        );
    }

    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

   /* @Test
    void computeRanksFiveTimes() {
        actualEngine.computeRanks(actualEngine.internet.getVertices());
        actualEngine.computeRanks(actualEngine.internet.getVertices());
        actualEngine.computeRanks(actualEngine.internet.getVertices());
        actualEngine.computeRanks(actualEngine.internet.getVertices());
        actualEngine.computeRanks(actualEngine.internet.getVertices());
        assertAll(
                () -> assertTrue(roundAvoid(actualEngine.internet.getPageRank("A"), 3) == roundAvoid(expectedEngine.internet.getPageRank("A"), 3) &&
                        roundAvoid(actualEngine.internet.getPageRank("B"), 3) == roundAvoid(expectedEngine.internet.getPageRank("B"),3) &&
                        roundAvoid(actualEngine.internet.getPageRank("C"), 3) == roundAvoid(expectedEngine.internet.getPageRank("C"),3) &&
                        roundAvoid(actualEngine.internet.getPageRank("D"), 3) == roundAvoid(expectedEngine.internet.getPageRank("D"),3),
                        "calling computeRanks 5x results in equivalent PageRanks when actualEngine.assignPageRanks(0.01) is called")
        );
    }

    @Test
    void computeRanksOneTime() {
        actualEngine.computeRanks(actualEngine.internet.getVertices());
        assertAll(
                () -> assertTrue(roundAvoid(actualEngine.internet.getPageRank("A"), 3) == roundAvoid(1.1666666666666665, 3) &&
                        roundAvoid(actualEngine.internet.getPageRank("B"), 3) == roundAvoid(0.75,3) &&
                        roundAvoid(actualEngine.internet.getPageRank("C"), 3) == roundAvoid(1.4166666666666665,3) &&
                        roundAvoid(actualEngine.internet.getPageRank("D"), 3) == roundAvoid(0.6666666666666666,3), "compute rank 1x correct")
        );
    }*/

    @Test
    void getResults1() {
       actualEngine.assignPageRanks(0.01);
        assertAll(
                () -> assertEquals(new ArrayList<String>(Arrays.asList("C", "B", "D")), actualEngine.getResults("3740770036"))
        );
    }

    @Test
    void getResults2() {
        actualEngine.assignPageRanks(0.01);
        if (actualEngine.getResults("69") == null) throw new NullPointerException("getResults returns null"); // nice
        assertAll(
                () -> assertEquals(0, actualEngine.getResults("69").size())
        );
    }

    @Test
    void getResults3() {
        actualEngine.assignPageRanks(0.01);
        assertAll(
                () -> assertEquals(new ArrayList<String>(Arrays.asList("B", "D")), actualEngine.getResults("7635743784"))
        );
    }



}