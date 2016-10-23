/**
 * Created by marco on 21/10/16.
 * In this class we will read the webpage related to the given word and we will extract data from it
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
        boolean tableflag=false;
        Elements tables= doc.getElementsByTag("table");
        Iterator<Element> tableiterator = tables.iterator();
        while (tableiterator.hasNext()) {
            tableflag=true;
            Element table = tableiterator.next();
            ParseTable(table);
        }
        if (!tableflag) System.out.println("\tNo tables for this word");
    }

    //Parser for the single table
    static void ParseTable(Element table) {
        //TODO: find a way to detect if a table is interesting or not for us (maybe when we couldn't detect language)
        String lang=getLanguage(table);
        if (lang.isEmpty()) System.out.println("\tLanguage not found");
        else System.out.println("\t"+lang);
    }

    //Get the language of the table content
    static String getLanguage(Element table) {
        boolean found=false;
        String lang = new String("");
        //Get the node at the same level of the XML tag with the language
        Element divFrame = table;
        while (!divFrame.parent().id().equalsIgnoreCase("mw-content-text")) {
            divFrame=divFrame.parent();
        }
        //Search the node containing the language
        while (!found && divFrame!=null) {
            if (divFrame.tagName().equalsIgnoreCase("h2")) {
                Element langel=divFrame.child(0);
                if (langel.className().equalsIgnoreCase("mw-headline")) {
                    lang = langel.text();
                    found=true;
                }
            }
            divFrame = divFrame.previousElementSibling();
        }
        return lang;
    }

}
