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

    public HTMLParser( String word ) {
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
        boolean noskip=true;
        //TODO: find a way to detect if a table is interesting or not for us
        if (table.className().equalsIgnoreCase("audiotable")) {
            noskip=false;
            System.out.println("\tTable skipped");
        }
        if (noskip) {
            String lang = getLanguage(table);
            String pos = getPOS(table, lang);
            if (lang.isEmpty()) lang = "Language not found";
            if (pos.isEmpty()) pos = "Part of Speech not found";
            System.out.println("\t" + lang + "\t" + pos);
        }
    }

    //Get the language of a node (usually of a table)
    static String getLanguage(Element element) {
        boolean found=false;
        String lang = new String("");
        //Get the node at the same level of the XML tag with the language
        Element divFrame = element;
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

    //Get the POS of table content
    static String getPOS(Element table, String lang) {
        boolean found=false;
        String pos=new String("");
        Element divFrame=table;
        //Get the node at the same level of the XML tag with the language
        while(!divFrame.parent().id().equalsIgnoreCase("mw-content-text")) {
            divFrame=divFrame.parent();
        }
        //Search the node containing the POS
        while (!found && divFrame!=null) {
            if (divFrame.tagName().equalsIgnoreCase("h4")||divFrame.tagName().equalsIgnoreCase("h5")) {
                Element posel=divFrame.child(0);
                if (posel.className().equalsIgnoreCase("mw-headline")) {
                    //Check if the POS found too is related to the table language
                    if (lang.equalsIgnoreCase(getLanguage(divFrame))) {
                        pos = posel.text();
                    }
                    else {
                        pos="POS language not matched";
                    }
                    found = true;
                }
            }
            divFrame = divFrame.previousElementSibling();
        }
        return pos;
    }

}
