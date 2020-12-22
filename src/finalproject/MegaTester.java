package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MegaTester {
    private static Random rand = new Random();

    public static void main(String[] argv) throws Exception{
        testCrawlAndIndexMultipleCrawls();
        testCrawlAndIndexGraphStructure();
        testCrawlAndIndexWordIndexing();
        testGetResults();
        testComputeRanks();
        testAssignRanks();
        testCorrectnessFastsort();
        testSortingSpeed();
    }

    public static void testSortingSpeed(){
        int totalTests = 0, tooSlow = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        System.out.print("Testing fastSort() on 10000 elements many times... ");
        for (int i = 0; i < 10000; i++){
            map.clear();
            for (int j = 0; j <= 1000; j++) {
                int val = j + (int)(Math.random() * (i-j+1));
                map.put(val, val);
            }
            long startTime = System.nanoTime();
            Sorting.slowSort(map);
            long endTime = System.nanoTime();
            long durationSlow = (endTime - startTime);

            map.clear();
            for (int j = 0; j <= 1000; j++) {
                int val = j + (int)(Math.random() * (i-j+1));
                map.put(val, val);
            }
            startTime = System.nanoTime();
            Sorting.fastSort(map);
            endTime = System.nanoTime();
            long durationFast = (endTime - startTime);
            totalTests++;
            if ((durationSlow/durationFast) < 10) tooSlow++;
        }
        System.out.printf("FastSort() was too slow on %d out of %d tests\n", tooSlow, totalTests);
    }

    public static void testCorrectnessFastsort(){
        HashMap<Integer, Integer> map = new HashMap<>();
        System.out.print("Testing correctness of fastsort() on many lists... ");
        for (int i = 0; i < 1000; i++){
            map.clear();
            for (int j = 0; j < 100; j++){
                int val = rand.nextInt(1000);
                map.put(val, val);
            }
            ArrayList<Integer> out = Sorting.fastSort(map);
            if (!isSorted(out)) {
                System.out.println("Failed: fastSort() does not sort correctly");
                return;
            }
        }
        System.out.println("Passed");
    }

    private static <K extends Comparable> boolean isSorted(ArrayList<K> list){
        for (int i = 0; i < list.size() - 1; i++){
            if (list.get(i).compareTo(list.get(i+1)) < 0){
                return false;
            }
        }
        return true;
    }

    private static void testCrawlAndIndexMultipleCrawls() throws Exception{
        SearchEngine se = new SearchEngine("megatest.xml");
        se.crawlAndIndex("siteA");
        System.out.print("Testing crawlAndIndex() first crawl... ");
        ArrayList<String> urls = se.internet.getVertices();
        if (urls.size() != 4){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        int edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 6){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");
        System.out.print("Testing second crawl from same website... ");
        se.crawlAndIndex("siteA");
        urls = se.internet.getVertices();
        if (urls.size() != 4){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 6){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");
        System.out.print("Testing third crawl from new website... ");
        se.crawlAndIndex("siteE");
        urls = se.internet.getVertices();
        if (urls.size() != 5){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 8){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");

        System.out.print("Testing fourth crawl from disconnected website... ");
        se.crawlAndIndex("siteF");
        urls = se.internet.getVertices();
        if (urls.size() != 6){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 8){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");

        System.out.print("Testing fifth crawl from standalone cycle of websites... ");
        se.crawlAndIndex("siteG");
        urls = se.internet.getVertices();
        if (urls.size() != 9){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 11){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");

        System.out.print("Testing sixth crawl from website already visited... ");
        se.crawlAndIndex("siteC");
        urls = se.internet.getVertices();
        if (urls.size() != 9){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 11){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");

        System.out.print("Testing seventh crawl from website in the middle of path... ");
        se.crawlAndIndex("siteK");
        urls = se.internet.getVertices();
        if (urls.size() != 11){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 12){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");

        System.out.print("Testing eighth crawl from website at the start of path... ");
        se.crawlAndIndex("siteI");
        urls = se.internet.getVertices();
        if (urls.size() != 12){
            System.out.println("Failed: incorrect amount of vertices");
            return;
        }
        edges = 0;
        for (String v: urls){
            for (String e: se.internet.getEdgesInto(v)) edges++;
        }
        if (edges != 13){
            System.out.println("Failed: incorrect amount of edges");
            return;
        }
        System.out.println("Passed");
    }

    public static void testAssignRanks() throws Exception{
        SearchEngine se = new SearchEngine("megatest.xml");
        se.crawlAndIndex("siteA");
        se.assignPageRanks(0.0001);
        HashMap<String, Double> vals = new HashMap<>();
        vals.put("siteA", 1.2453002930);
        vals.put("siteB", 1.0377197266);
        vals.put("siteC", 0.8113098145);
        vals.put("siteD", 0.9056701660);

        System.out.print("Testing assignPageRanks() after single crawl... ");
        ArrayList<String> urls = se.internet.getVertices();
        for (String v: urls){
            if (!floatCompare(se.internet.getPageRank(v), vals.get(v))){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", v, se.internet.getPageRank(v), vals.get(v));
                return;
            }
        }
        System.out.println("Passed");
        vals.clear();


        se.crawlAndIndex("siteE");
        se.assignPageRanks(0.0001);
        vals.put("siteE", 0.5);
        vals.put("siteC", 0.8443450928);
        vals.put("siteD", 1.0471496582);
        vals.put("siteA", 1.3773498535);
        vals.put("siteB", 1.2311553955);
        System.out.print("Testing assignPageRanks() after second crawl... ");
        urls = se.internet.getVertices();
        for (String v: urls){
            if (!floatCompare(se.internet.getPageRank(v), vals.get(v))){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", v, se.internet.getPageRank(v), vals.get(v));
                return;
            }
        }
        System.out.println("Passed");


        se.crawlAndIndex("siteF");
        se.assignPageRanks(0.0001);
        vals.put("siteF", 0.5);
        System.out.print("Testing assignPageRanks() after third crawl... ");
        urls = se.internet.getVertices();
        for (String v: urls){
            if (!floatCompare(se.internet.getPageRank(v), vals.get(v))){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", v, se.internet.getPageRank(v), vals.get(v));
                return;
            }
        }
        System.out.println("Passed");


        se.crawlAndIndex("siteG");
        se.assignPageRanks(0.0001);
        vals.put("siteH", 1.0);
        vals.put("siteG", 1.0);
        vals.put("siteJ", 1.0);
        System.out.print("Testing assignPageRanks() after fourth crawl... ");
        urls = se.internet.getVertices();
        for (String v: urls){
            if (!floatCompare(se.internet.getPageRank(v), vals.get(v))){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", v, se.internet.getPageRank(v), vals.get(v));
                return;
            }
        }
        System.out.println("Passed");

        se.crawlAndIndex("siteK");
        se.assignPageRanks(0.0001);
        vals.put("siteK", 0.5);
        vals.put("siteL", 0.75);
        System.out.print("Testing assignPageRanks() after fifth crawl... ");
        urls = se.internet.getVertices();
        for (String v: urls){
            if (!floatCompare(se.internet.getPageRank(v), vals.get(v))){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", v, se.internet.getPageRank(v), vals.get(v));
                return;
            }
        }
        System.out.println("Passed");
        vals.clear();

        se.crawlAndIndex("siteI");
        se.assignPageRanks(0.0001);
        vals.put("siteE", 0.5);
        vals.put("siteC", 0.8443450928);
        vals.put("siteD", 1.0471496582);
        vals.put("siteA", 1.3773498535);
        vals.put("siteB", 1.2311553955);
        vals.put("siteF", 0.5);
        vals.put("siteH", 1.0);
        vals.put("siteG", 1.0);
        vals.put("siteJ", 1.0);
        vals.put("siteI", 0.5);
        vals.put("siteL", 0.875);
        vals.put("siteK", 0.75);
        System.out.print("Testing assignPageRanks() after sixth crawl... ");
        urls = se.internet.getVertices();
        for (String v: urls){
            if (!floatCompare(se.internet.getPageRank(v), vals.get(v))){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", v, se.internet.getPageRank(v), vals.get(v));
                return;
            }
        }
        System.out.println("Passed");
    }

    private static boolean floatCompare(double val1, double val2){
        //val1 = Math.round(val1 * 10000d) / 10000d;
        //val2 = Math.round(val2 * 10000d) / 10000d;
        return (Math.abs(val1 - val2) <= 1e-4);
    }

    public static void testComputeRanks() throws Exception {
        SearchEngine se = new SearchEngine("megatest.xml");
        se.crawlAndIndex("siteA");
        ArrayList<String> urls = se.internet.getVertices();
        HashMap<String, Double> expectedResults = new HashMap<>();
        expectedResults.put("siteC", 0.75);
        expectedResults.put("siteD", 1.0);
        expectedResults.put("siteA", 1.25);
        expectedResults.put("siteB", 1.0);
        for (String v : urls)
            se.internet.setPageRank(v, 1.0);

        System.out.print("Testing computeRanks on 4 vertices... ");
        ArrayList<Double> rankAfterOneIteration = se.computeRanks(urls);
        for (int i = 0; i < urls.size(); i++) {
            String vertex = urls.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = expectedResults.get(vertex);

            if (!floatCompare(expectedRank, rank)){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", vertex, rank, expectedRank);
                return;
            }
        }
        System.out.println("Passed");


        se.crawlAndIndex("siteE");
        urls = se.internet.getVertices();
        expectedResults.clear();
        expectedResults.put("siteC", 0.75);
        expectedResults.put("siteD", 1.25);
        expectedResults.put("siteA", 1.25);
        expectedResults.put("siteB", 1.25);
        expectedResults.put("siteE", 0.5);
        for (String v : urls)
            se.internet.setPageRank(v, 1.0);

        System.out.print("Testing computeRanks on 5 vertices... ");
        rankAfterOneIteration = se.computeRanks(urls);
        for (int i = 0; i < urls.size(); i++) {
            String vertex = urls.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = expectedResults.get(vertex);

            if (!floatCompare(expectedRank, rank)) {
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", vertex, rank, expectedRank);
                return;
            }
        }
        System.out.println("Passed");



        se.crawlAndIndex("siteF");
        urls = se.internet.getVertices();
        expectedResults.put("siteF", 0.5);
        for (String v : urls)
            se.internet.setPageRank(v, 1.0);

        System.out.print("Testing computeRanks on 6 vertices... ");
        rankAfterOneIteration = se.computeRanks(urls);
        for (int i = 0; i < urls.size(); i++) {
            String vertex = urls.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = expectedResults.get(vertex);

            if (!floatCompare(expectedRank, rank)){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", vertex, rank, expectedRank);
                return;
            }


        }
        System.out.println("Passed");

        se.crawlAndIndex("siteG");
        urls = se.internet.getVertices();
        expectedResults.put("siteG", 1.0);
        expectedResults.put("siteH", 1.0);
        expectedResults.put("siteJ", 1.0);
        for (String v : urls)
            se.internet.setPageRank(v, 1.0);

        System.out.print("Testing computeRanks on 9 vertices... ");
        rankAfterOneIteration = se.computeRanks(urls);
        for (int i = 0; i < urls.size(); i++) {
            String vertex = urls.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = expectedResults.get(vertex);

            if (!floatCompare(expectedRank, rank)){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", vertex, rank, expectedRank);
                return;
            }


        }
        System.out.println("Passed");


        se.crawlAndIndex("siteK");
        urls = se.internet.getVertices();
        expectedResults.put("siteL", 1.0);
        expectedResults.put("siteK", 0.5);
        for (String v : urls)
            se.internet.setPageRank(v, 1.0);

        System.out.print("Testing computeRanks on 11 vertices... ");
        rankAfterOneIteration = se.computeRanks(urls);
        for (int i = 0; i < urls.size(); i++) {
            String vertex = urls.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = expectedResults.get(vertex);

            if (!floatCompare(expectedRank, rank)){
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", vertex, rank, expectedRank);
                return;
            }


        }
        System.out.println("Passed");


        se.crawlAndIndex("siteI");
        urls = se.internet.getVertices();
        expectedResults.remove("siteK");
        expectedResults.put("siteK", 1.0);
        expectedResults.put("siteI", 0.5);
        for (String v : urls)
            se.internet.setPageRank(v, 1.0);

        System.out.print("Testing computeRanks on 12 vertices... ");
        rankAfterOneIteration = se.computeRanks(urls);
        for (int i = 0; i < urls.size(); i++) {
            String vertex = urls.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = expectedResults.get(vertex);

            if (!floatCompare(expectedRank, rank)) {
                System.out.println("Failed: wrong value for page rank");
                System.out.printf("For site %s: got %f but expected %f\n", vertex, rank, expectedRank);
                return;
            }
        }
        System.out.println("Passed");
    }


    public static void testCrawlAndIndexGraphStructure() throws Exception{
        SearchEngine se = new SearchEngine("megatest.xml");

        System.out.print("Testing graph structure after single crawl... ");
        se.crawlAndIndex("siteA");
        ArrayList<String> urls = se.internet.getVertices();

        ArrayList<String> neighboursOfA = se.internet.getNeighbors("siteA");
        ArrayList<String> neighboursOfB = se.internet.getNeighbors("siteB");
        ArrayList<String> neighboursOfC = se.internet.getNeighbors("siteC");
        ArrayList<String> neighboursOfD = se.internet.getNeighbors("siteD");

        boolean vertices = (urls.contains("siteA") && urls.contains("siteB")
                && urls.contains("siteC") && urls.contains("siteD"));
        boolean edges = (neighboursOfA.contains("siteC") && neighboursOfA.contains("siteB")
                && neighboursOfC.contains("siteD") && neighboursOfD.contains("siteA")
                && neighboursOfD.contains("siteB") && neighboursOfB.contains("siteA"));

        if (!edges || !vertices){
            System.out.println("Failed: graph structure not correct");
            return;
        }
        System.out.println("Passed");


        System.out.print("Testing graph structure after second crawl... ");
        se.crawlAndIndex("siteE");
        urls = se.internet.getVertices();

        neighboursOfA = se.internet.getNeighbors("siteA");
        neighboursOfB = se.internet.getNeighbors("siteB");
        neighboursOfC = se.internet.getNeighbors("siteC");
        neighboursOfD = se.internet.getNeighbors("siteD");
        ArrayList<String> neighboursOfE = se.internet.getNeighbors("siteE");

        vertices = (urls.contains("siteA") && urls.contains("siteB")
                && urls.contains("siteC") && urls.contains("siteD") && urls.contains("siteE"));
        edges = (neighboursOfA.contains("siteC") && neighboursOfA.contains("siteB")
                && neighboursOfC.contains("siteD") && neighboursOfD.contains("siteA")
                && neighboursOfD.contains("siteB") && neighboursOfB.contains("siteA")
                && neighboursOfE.contains("siteB") && neighboursOfE.contains("siteD"));

        if (!edges || !vertices){
            System.out.println("Failed: graph structure not correct");
            return;
        }
        System.out.println("Passed");

        System.out.print("Testing graph structure after third crawl... ");
        se.crawlAndIndex("siteF");
        urls = se.internet.getVertices();

        neighboursOfA = se.internet.getNeighbors("siteA");
        neighboursOfB = se.internet.getNeighbors("siteB");
        neighboursOfC = se.internet.getNeighbors("siteC");
        neighboursOfD = se.internet.getNeighbors("siteD");
        neighboursOfE = se.internet.getNeighbors("siteE");
        ArrayList<String> neighboursOfF = se.internet.getNeighbors("siteF");

        vertices = (urls.contains("siteA") && urls.contains("siteB")
                && urls.contains("siteC") && urls.contains("siteD")
                && urls.contains("siteE") && urls.contains("siteF"));
        edges = (neighboursOfA.contains("siteC") && neighboursOfA.contains("siteB")
                && neighboursOfC.contains("siteD") && neighboursOfD.contains("siteA")
                && neighboursOfD.contains("siteB") && neighboursOfB.contains("siteA")
                && neighboursOfE.contains("siteB") && neighboursOfE.contains("siteD")
                && (neighboursOfF.size() == 0));

        if (!edges || !vertices){
            System.out.println("Failed: graph structure not correct");
            return;
        }
        System.out.println("Passed");


        System.out.print("Testing graph structure after fourth crawl... ");
        se.crawlAndIndex("siteH");
        urls = se.internet.getVertices();

        neighboursOfA = se.internet.getNeighbors("siteA");
        neighboursOfB = se.internet.getNeighbors("siteB");
        neighboursOfC = se.internet.getNeighbors("siteC");
        neighboursOfD = se.internet.getNeighbors("siteD");
        neighboursOfE = se.internet.getNeighbors("siteE");
        neighboursOfF = se.internet.getNeighbors("siteF");
        ArrayList<String> neighboursOfG = se.internet.getNeighbors("siteG");
        ArrayList<String> neighboursOfH = se.internet.getNeighbors("siteH");
        ArrayList<String> neighboursOfJ = se.internet.getNeighbors("siteJ");

        vertices = (urls.contains("siteA") && urls.contains("siteB")
                && urls.contains("siteC") && urls.contains("siteD")
                && urls.contains("siteE") && urls.contains("siteF")
                && urls.contains("siteG") && urls.contains("siteH")
                && urls.contains("siteJ"));
        edges = (neighboursOfA.contains("siteC") && neighboursOfA.contains("siteB")
                && neighboursOfC.contains("siteD") && neighboursOfD.contains("siteA")
                && neighboursOfD.contains("siteB") && neighboursOfB.contains("siteA")
                && neighboursOfE.contains("siteB") && neighboursOfE.contains("siteD")
                && (neighboursOfF.size() == 0) && neighboursOfG.contains("siteH")
                && neighboursOfH.contains("siteJ") && neighboursOfJ.contains("siteG"));

        if (!edges || !vertices){
            System.out.println("Failed: graph structure not correct");
            return;
        }
        System.out.println("Passed");


        System.out.print("Testing graph structure after fifth crawl... ");
        se.crawlAndIndex("siteK");
        urls = se.internet.getVertices();

        neighboursOfA = se.internet.getNeighbors("siteA");
        neighboursOfB = se.internet.getNeighbors("siteB");
        neighboursOfC = se.internet.getNeighbors("siteC");
        neighboursOfD = se.internet.getNeighbors("siteD");
        neighboursOfE = se.internet.getNeighbors("siteE");
        neighboursOfF = se.internet.getNeighbors("siteF");
        neighboursOfG = se.internet.getNeighbors("siteG");
        neighboursOfH = se.internet.getNeighbors("siteH");
        neighboursOfJ = se.internet.getNeighbors("siteJ");
        ArrayList<String> neighboursOfK = se.internet.getNeighbors("siteK");
        ArrayList<String> neighboursOfL = se.internet.getNeighbors("siteL");

        vertices = (urls.contains("siteA") && urls.contains("siteB")
                && urls.contains("siteC") && urls.contains("siteD")
                && urls.contains("siteE") && urls.contains("siteF")
                && urls.contains("siteG") && urls.contains("siteH")
                && urls.contains("siteJ") && urls.contains("siteK")
                && urls.contains("siteL"));
        edges = (neighboursOfA.contains("siteC") && neighboursOfA.contains("siteB")
                && neighboursOfC.contains("siteD") && neighboursOfD.contains("siteA")
                && neighboursOfD.contains("siteB") && neighboursOfB.contains("siteA")
                && neighboursOfE.contains("siteB") && neighboursOfE.contains("siteD")
                && (neighboursOfF.size() == 0) && neighboursOfG.contains("siteH")
                && neighboursOfH.contains("siteJ") && neighboursOfJ.contains("siteG")
                && neighboursOfK.contains("siteL") && (neighboursOfL.size() == 0));

        if (!edges || !vertices){
            System.out.println("Failed: graph structure not correct");
            return;
        }
        System.out.println("Passed");


        System.out.print("Testing graph structure after sixth crawl... ");
        se.crawlAndIndex("siteI");
        urls = se.internet.getVertices();

        neighboursOfA = se.internet.getNeighbors("siteA");
        neighboursOfB = se.internet.getNeighbors("siteB");
        neighboursOfC = se.internet.getNeighbors("siteC");
        neighboursOfD = se.internet.getNeighbors("siteD");
        neighboursOfE = se.internet.getNeighbors("siteE");
        neighboursOfF = se.internet.getNeighbors("siteF");
        neighboursOfG = se.internet.getNeighbors("siteG");
        neighboursOfH = se.internet.getNeighbors("siteH");
        neighboursOfJ = se.internet.getNeighbors("siteJ");
        neighboursOfK = se.internet.getNeighbors("siteK");
        neighboursOfL = se.internet.getNeighbors("siteL");
        ArrayList<String> neighboursOfI = se.internet.getNeighbors("siteI");

        vertices = (urls.contains("siteA") && urls.contains("siteB")
                && urls.contains("siteC") && urls.contains("siteD")
                && urls.contains("siteE") && urls.contains("siteF")
                && urls.contains("siteG") && urls.contains("siteH")
                && urls.contains("siteJ") && urls.contains("siteK")
                && urls.contains("siteL") && urls.contains("siteI"));
        edges = (neighboursOfA.contains("siteC") && neighboursOfA.contains("siteB")
                && neighboursOfC.contains("siteD") && neighboursOfD.contains("siteA")
                && neighboursOfD.contains("siteB") && neighboursOfB.contains("siteA")
                && neighboursOfE.contains("siteB") && neighboursOfE.contains("siteD")
                && (neighboursOfF.size() == 0) && neighboursOfG.contains("siteH")
                && neighboursOfH.contains("siteJ") && neighboursOfJ.contains("siteG")
                && neighboursOfK.contains("siteL") && (neighboursOfL.size() == 0)
                && neighboursOfI.contains("siteK"));

        if (!edges || !vertices){
            System.out.println("Failed: graph structure not correct");
            return;
        }
        System.out.println("Passed");
    }

    public static void testGetResults() throws Exception {
        SearchEngine se = new SearchEngine("megatest.xml");
        se.crawlAndIndex("siteA");
        se.crawlAndIndex("siteE");
        se.crawlAndIndex("siteF");
        se.crawlAndIndex("siteG");
        se.crawlAndIndex("siteK");
        se.crawlAndIndex("siteI");
        se.assignPageRanks(0.0001);
        System.out.print("Testing getResults on several queries... ");
        ArrayList<String> dumb = se.getResults("dumb");
        ArrayList<String> what = se.getResults("what");
        ArrayList<String> an = se.getResults("an");
        ArrayList<String> to = se.getResults("to");
        ArrayList<String> combat = se.getResults("combat");
        ArrayList<String> away = se.getResults("away");
        ArrayList<String> pointless = se.getResults("pointless");

        boolean correctSizes = (dumb.size() == 3 && what.size() == 2 && an.size() == 5 && to.size() == 7
                && combat.size() == 2 && away.size() == 3 && pointless.size() == 0);
        boolean correctEntries = (dumb.contains("siteA") && dumb.contains("siteH")
                && dumb.contains("siteC") && what.contains("siteG") && what.contains("siteK")
                && an.contains("siteA") && an.contains("siteB") && an.contains("siteJ")
                && an.contains("siteE") && an.contains("siteF") && to.contains("siteD")
                && to.contains("siteG") && to.contains("siteH") && to.contains("siteL")
                && to.contains("siteC") && to.contains("siteK") && to.contains("siteI")
                && combat.contains("siteK") && combat.contains("siteI") && away.contains("siteG")
                && away.contains("siteK") && away.contains("siteI"));

        if (!correctSizes || !correctEntries){
            System.out.println("Failed: Results were incorrect");
            return;
        }
        System.out.println("Passed");


        System.out.print("Testing getResults for case sensitivity... ");
        dumb = se.getResults("duMb");
        what = se.getResults("What");
        an = se.getResults("AN");
        to = se.getResults("tO");
        combat = se.getResults("coMBat");
        away = se.getResults("AwAy");
        pointless = se.getResults("poinTleSS");

        correctSizes = (dumb.size() == 3 && what.size() == 2 && an.size() == 5 && to.size() == 7
                && combat.size() == 2 && away.size() == 3 && pointless.size() == 0);
        correctEntries = (dumb.contains("siteA") && dumb.contains("siteH")
                && dumb.contains("siteC") && what.contains("siteG") && what.contains("siteK")
                && an.contains("siteA") && an.contains("siteB") && an.contains("siteJ")
                && an.contains("siteE") && an.contains("siteF") && to.contains("siteD")
                && to.contains("siteG") && to.contains("siteH") && to.contains("siteL")
                && to.contains("siteC") && to.contains("siteK") && to.contains("siteI")
                && combat.contains("siteK") && combat.contains("siteI") && away.contains("siteG")
                && away.contains("siteK") && away.contains("siteI"));

        if (!correctSizes || !correctEntries){
            System.out.println("Failed: Results were incorrect");
            return;
        }
        System.out.println("Passed");
    }

    public static void testCrawlAndIndexWordIndexing() throws Exception {
        SearchEngine se = new SearchEngine("megatest.xml");
        se.crawlAndIndex("siteA");
        System.out.print("Testing wordindex on several keywords after single crawl... ");
        ArrayList<String> dumb = se.wordIndex.get("dumb");
        ArrayList<String> what = se.wordIndex.get("what");
        ArrayList<String> an = se.wordIndex.get("an");
        ArrayList<String> to = se.wordIndex.get("to");
        ArrayList<String> combat = se.wordIndex.get("combat");
        ArrayList<String> away = se.wordIndex.get("away");
        ArrayList<String> pointless = se.wordIndex.get("pointless");
        boolean correctSizes = (dumb.size() == 2 && what == null && an.size() == 2 && to.size() == 2
                        && combat == null && away == null && pointless == null);
        if (!correctSizes){
            System.out.println("Failed: word entries might contain duplicate URLs");
            return;
        }
        System.out.println("Passed");

        se.crawlAndIndex("siteE");
        System.out.print("Testing wordindex on several keywords after second crawl... ");
        dumb = se.wordIndex.get("dumb");
        what = se.wordIndex.get("what");
        an = se.wordIndex.get("an");
        to = se.wordIndex.get("to");
        combat = se.wordIndex.get("combat");
        away = se.wordIndex.get("away");
        pointless = se.wordIndex.get("pointless");
        correctSizes = (dumb.size() == 2 && what == null && an.size() == 3 && to.size() == 2
                && combat == null && away == null && pointless == null);
        if (!correctSizes){
            System.out.println("Failed: word entries might contain duplicate URLs");
            return;
        }
        System.out.println("Passed");

        se.crawlAndIndex("siteF");
        System.out.print("Testing wordindex on several keywords after third crawl... ");
        dumb = se.wordIndex.get("dumb");
        what = se.wordIndex.get("what");
        an = se.wordIndex.get("an");
        to = se.wordIndex.get("to");
        combat = se.wordIndex.get("combat");
        away = se.wordIndex.get("away");
        pointless = se.wordIndex.get("pointless");
        correctSizes = (dumb.size() == 2 && what == null && an.size() == 4 && to.size() == 2
                && combat == null && away == null && pointless == null);
        if (!correctSizes){
            System.out.println("Failed: word entries might contain duplicate URLs");
            return;
        }
        System.out.println("Passed");

        se.crawlAndIndex("siteG");
        System.out.print("Testing wordindex on several keywords after fourth crawl... ");
        dumb = se.wordIndex.get("dumb");
        what = se.wordIndex.get("what");
        an = se.wordIndex.get("an");
        to = se.wordIndex.get("to");
        combat = se.wordIndex.get("combat");
        away = se.wordIndex.get("away");
        pointless = se.wordIndex.get("pointless");
        correctSizes = (dumb.size() == 3 && what.size() == 1 && an.size() == 5 && to.size() == 4
                && combat == null && away.size() == 1 && pointless == null);
        if (!correctSizes){
            System.out.println("Failed: word entries might contain duplicate URLs");
            return;
        }
        System.out.println("Passed");

        se.crawlAndIndex("siteI");
        System.out.print("Testing wordindex on several keywords after fifth crawl... ");
        dumb = se.wordIndex.get("dumb");
        what = se.wordIndex.get("what");
        an = se.wordIndex.get("an");
        to = se.wordIndex.get("to");
        combat = se.wordIndex.get("combat");
        away = se.wordIndex.get("away");
        pointless = se.wordIndex.get("pointless");
        correctSizes = (dumb.size() == 3 && what.size() == 2 && an.size() == 5 && to.size() == 7
                && combat.size() == 2 && away.size() == 3 && pointless == null);
        if (!correctSizes){
            System.out.println("Failed: word entries might contain duplicate URLs");
            return;
        }
        System.out.println("Passed");
    }
}
