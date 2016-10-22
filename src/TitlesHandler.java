import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by marco on 21/10/16.
 * The XML parser: gets all the titles in the Wiktionary XML and call the HTML parsing method if necessary
 * (from the characters method).
 */
public class TitlesHandler extends DefaultHandler {

    private boolean title=false;
    private boolean startCheck=false;
    private String startWord=null;


    public TitlesHandler() {
        super();
        startCheck=true;
    }

    //in case we want to skip the words until a certain one
    public TitlesHandler(String startFrom) {
        super();
        startWord=new String(startFrom);
        startCheck=false;
    }

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
            System.out.println("Title: " + foundTitle);
            //Check if it is a word that interest us or not and get infos
            if (startCheck) {
                if (!foundTitle.contains(":")) {
                    HTMLParser hparser = new HTMLParser(foundTitle);
                }
            }
            else {
                if (foundTitle.equals(startWord)) startCheck=true;
            }
            title = false;
        }
    }

}
