/**
 * Created by marco on 21/10/16.
 * In this class we will read the weboage related to the given word and we will extract data from it
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class HTMLParser {

    public HTMLParser( String word) {
        try {
            Document doc = Jsoup.connect("https://en.wiktionary.org/wiki/" + word).get();
            ParseText(doc);
        }
        catch (IOException e) {}
    }

    static void ParseText(Document doc) {

    }

}
