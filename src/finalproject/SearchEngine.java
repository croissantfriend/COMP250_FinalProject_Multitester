package finalproject;

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
    public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)
    public MyWebGraph internet;
    public XmlParser parser;

    public SearchEngine(String filename) throws Exception{
        this.wordIndex = new HashMap<String, ArrayList<String>>();
        this.internet = new MyWebGraph();
        this.parser = new XmlParser(filename);
    }

    /*
     * This does a graph traversal of the web, starting at the given url.
     * For each new page seen, it updates the wordIndex, the web graph,
     * and the set of visited vertices.
     *
     * 	This method will fit in about 30-50 lines (or less)
     */
    public void crawlAndIndex(String url) throws Exception {
        // TODO : Add code here
    }



    /*
     * This computes the pageRanks for every vertex in the web graph.
     * It will only be called after the graph has been constructed using
     * crawlAndIndex().
     * To implement this method, refer to the algorithm described in the
     * assignment pdf.
     *
     * This method will probably fit in about 30 lines.
     */
    public void assignPageRanks(double epsilon) {
        // TODO : Add code here
    }

    /*
     * The method takes as input an ArrayList<String> representing the urls in the web graph
     * and returns an ArrayList<double> representing the newly computed ranks for those urls.
     * Note that the double in the output list is matched to the url in the input list using
     * their position in the list.
     */
    public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
        // TODO : Add code here
        return null;
    }


    /* Returns a list of urls containing the query, ordered by rank
     * Returns an empty list if no web site contains the query.
     *
     * This method should take about 25 lines of code.
     */
    public ArrayList<String> getResults(String query) {
        // TODO: Add code here

        return null;
    }
}