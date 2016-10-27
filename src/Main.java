import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by marco on 20/10/16.
 * Open the XML Wiktionary dump and starts parsing it.
 */



public class Main {

    public static void main(String argv[]) {
        //Open the XML file and parse its "titles" for listing the pages that we have to check on Wiktionary
        //TODO: use the data in the pages listed in checkwords.txt in order to distinct between the header and
        // cell values in tables
        HeaderList hlist = new HeaderList("checkwords.txt");
        String nFile=new String("enwiktionary-20160305-pages-articles.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            File file = new File(nFile);
            TitlesHandler handler = new TitlesHandler();
            parser.parse(file, handler);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
