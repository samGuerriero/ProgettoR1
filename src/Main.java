/**
 * Created by marco on 20/10/16.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.ErrorHandler;

import java.io.IOException;

public class Main {

    void main() {
        try {
            Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        }
        catch (IOException e) {}
    }
}
