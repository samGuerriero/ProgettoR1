/**
 * Created by marco on 21/10/16.
 * In this class we will read the weboage related to the given word and we will extract data from it
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class HTMLParser {

    public HTMLParser( String word) {
        try {
            Document doc = Jsoup.connect("https://en.wiktionary.org/wiki/" + word).get();
            ParseText(doc);
        }
        catch (IOException e) {}
    }

    //Parser for the HTML page
    static void ParseText(Document doc) {
        Elements tables= doc.getElementsByTag("table");
        Iterator<Element> tableiterator = tables.iterator();
        while (tableiterator.hasNext()) {
            Element table = tableiterator.next();
            ParseTable(table);
        }
    }

    //Parser for the single table
    static void ParseTable(Element table) {

    }

}
