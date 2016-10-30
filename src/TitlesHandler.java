import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 21/10/16.
 * The XML parser: gets all the titles in the Wiktionary XML and call the HTML parsing method if necessary
 * (from the characters method).
 */
public class TitlesHandler extends DefaultHandler {

    private boolean title=false;
    private boolean startCheck=false;
    private List<Document> docChecked;
    private List<Document> docToCheck;
    private int counter;
    private HeaderList headers = new HeaderList();
    //private String startWord=null;


    public TitlesHandler() {
        super();
        startCheck=false;
        counter=0;
        docChecked=new ArrayList<>(100);
        docToCheck=new ArrayList<>(100);
    }

    //in case we want to skip the words until a certain one
    /*public TitlesHandler(String startFrom) {
        super();
        startWord=new String(startFrom);
        startCheck=false;
    }*/

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("title")) {
            title=true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //maybe useless for us
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (title) {
            String foundTitle = new String(ch, start, length);
            //Check if it is a word that interest us or not and get infos
            /*
            * Here we made a lot of stuff:
            * - Perform continuous learning of the headers directly during the XML parsing. To do this we store the pages
            *   in two lists: one where the words are already used to get the headers and the other one that we haven't
             *  already used. Once the counter reach the 100 value, we use it to get the headers and we start the parsing
             *  of the pages in the first list. In this way we are sure we got the headers on the basis of the previous
             *  titles and the next 100 (at least)
             *
             *  todo: what happens at the end of the XML file?
            * */
            if (!foundTitle.contains(":")) {
                if (!startCheck) {
                    docChecked.add(loadPage(foundTitle));
                    counter++;
                    System.out.print(" "+counter);
                    if (counter>=100) {
                        startCheck=true;
                        //start the training of the header set
                        for (Document page : docChecked) {
                            headers.addTables(page);
                        }
                        counter=0;
                        System.out.println();
                    }
                }
                else {
                    docToCheck.add(loadPage(foundTitle));
                    counter++;
                    System.out.print(" "+counter);
                    if (counter>=100) {
                        //train the header set with the new set
                        for (Document page : docToCheck) {
                            headers.addTables(page);
                        }
                        counter=0;
                        System.out.println();
                        headers.printHeaders();
                        //parse the pages with the old set
                        for (Document page : docChecked) {
                            HTMLParser hparser = new HTMLParser(page, foundTitle);
                        }
                        docChecked=docToCheck;
                        docToCheck=new ArrayList<>(100);
                    }
                }
            }
            /*else {
                System.out.println("\tWord not considered");
            }*/

            title = false;
        }
    }

    private static Document loadPage(String title) {
        try {
            Document doc = Jsoup.connect("https://en.wiktionary.org/wiki/" + title).get();
            return doc;
        }
        catch (IOException e) {

        }
        return null;
    }

}
