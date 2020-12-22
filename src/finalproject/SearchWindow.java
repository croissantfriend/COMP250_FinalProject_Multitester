package finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchWindow {

    private JFrame frame;
    private JTextField txtA;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SearchWindow window = new SearchWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * @throws Exception
     */
    public SearchWindow() throws Exception {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * @throws Exception
     */
    private void initialize() throws Exception {

//        ArrayList<SearchEngine> libraries = new ArrayList<SearchEngine>();
//
//
//        SearchEngine library=new SearchEngine("HoF.xml");
//        library.crawlAndIndex("www.myhalloffamerecord.cn");
//
//        SearchEngine library2 = new SearchEngine("cs60Links.xml");
//        library2.crawlAndIndex("https://cs.mcgill.ca");
//
//        SearchEngine library3 = new SearchEngine("mcgillWiki.xml");
//        library3.crawlAndIndex("https://en.wikipedia.org/wiki/McGill_University");
//
//        SearchEngine library4 = new SearchEngine("test.xml");
//        library4.crawlAndIndex("www.cs.mcgill.ca");
//
//        for (SearchEngine Library : libraries)
//            Library.assignPageRanks(0.01);

        SearchEngine Library = new SearchEngine("megatest.xml");;
        Library.crawlAndIndex("siteA");
        Library.crawlAndIndex("siteE");
        Library.crawlAndIndex("siteF");
        Library.crawlAndIndex("siteG");
        Library.crawlAndIndex("siteK");
        Library.crawlAndIndex("siteI");
        Library.crawlAndIndex("www.myhalloffamerecord.cn");
        Library.crawlAndIndex("https://cs.mcgill.ca");
        Library.crawlAndIndex("https://en.wikipedia.org/wiki/McGill_University");
        Library.assignPageRanks(0.01);

        frame = new JFrame();
        frame.setBounds(100, 100, 770, 468);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel lblNewLabel = new JLabel("Enter your keyword here");
        lblNewLabel.setBounds(185,200,150,30);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setText("<html><font face=\"SimSun\";style=font:30pt>" + "Welcome to LXLibrary" + "</font>");
        lblNewLabel_1.setBounds(210,120, 434,50);
        frame.getContentPane().add(lblNewLabel_1);

        txtA = new JTextField();
        txtA.setText(null);
        txtA.setBounds(331,205, 210, 21);
        frame.getContentPane().add(txtA);
        txtA.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 61, 734, 358);
        scrollPane.setVisible(false);
        frame.getContentPane().add(scrollPane);

        JTextArea output = new JTextArea();
        output.setLineWrap(true);
        scrollPane.setViewportView(output);

        JButton btnNewButton_1 = new JButton("Back");
        JButton btnNewButton = new JButton("Search");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFieldAction(e);
            }
            private void searchFieldAction(ActionEvent e) {
                lblNewLabel_1.setText("<html><font face=\"SimSun\";style=font:12pt>" + "LXLibrary, search all you want" + "</font>");
                lblNewLabel_1.setBounds(0, 0, 434, 15);
                btnNewButton_1.setBounds(404, 29, 93, 23);
                btnNewButton.setBounds(313,29, 83, 23);
                lblNewLabel.setText("Your keyword is");
                lblNewLabel.setBounds(0,25,134,30);
                txtA.setBounds(95, 30, 210, 21);
                scrollPane.setVisible(true);
                output.setText("");
                ArrayList<String> results;
                ArrayList<String> content;
                results=Library.getResults(txtA.getText());
                if(results==null||results.size()==0) output.append("Your keyword generates no result. Please try another keyword");
                else {
                    for(int i=0;i<results.size();i++) {
                        output.append((i+1)+"."+results.get(i)+" "+Library.internet.getPageRank(results.get(i))+"\n");
                        content=Library.parser.getContent(results.get(i));
                        for(int j=0;j<33&&j<content.size();j++) output.append(content.get(j)+" ");
                        if(content.size()>33) output.append("...");
                        output.append("\n\n");
                    }
                }
                output.setCaretPosition(0);
            }
        });
        btnNewButton.setBounds(185,250, 83, 23);
        frame.getContentPane().add(btnNewButton);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backButtonAction(e);
            }
            private void backButtonAction(ActionEvent e) {
                if(btnNewButton.getX()==185&&btnNewButton.getY()==250) System.exit(0);
                lblNewLabel_1.setText("<html><font face=\"SimSun\";style=font:30pt>" + "Welcome to LXLibrary" + "</font>");
                lblNewLabel_1.setBounds(210,120, 434,50);
                btnNewButton_1.setBounds(446,250, 93, 23);
                btnNewButton.setBounds(185,250, 83, 23);
                lblNewLabel.setText("Enter your keyword here");
                lblNewLabel.setBounds(185,200,150,30);
                txtA.setBounds(331,205,210,21);
                output.setText(null);
                txtA.setText(null);
                scrollPane.setVisible(false);
            }
        });
        btnNewButton_1.setBounds(446,250, 93, 23);
        frame.getContentPane().add(btnNewButton_1);
    }
}