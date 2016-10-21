/**
 * Created by marco on 21/10/16.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class HTMLParser {

    public HTMLParser() {}

    public void ParseWord(String word) {
        try {
            Document doc = Jsoup.connect("https://en.wiktionary.org/wiki/" + word).get();
        }
        catch (IOException e) {}
    }
}
