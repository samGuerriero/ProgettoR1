import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by marco on 27/10/16.
 * This class has to get te file and use this to compute all the headers names (frequency of the word in tables check)
 */
public class HeaderList {
    //'frequence' is the min number of times that a word has to appear in order to be considered header.
    private final int frequence=5;
    private Map<String, Integer> headers = new HashMap<>();

    public HeaderList(String nfile) {
        BufferedReader reader;
        try {
            String currline;
            reader = new BufferedReader(new FileReader(nfile));
            while((currline=reader.readLine()) != null) {
                headers=frequentCells(currline,headers);
            }
            headers=removeNotFrequent(headers,frequence);
        }
        catch (IOException e) {}
    }

    public Map<String, Integer> getHeaders() {
        return new HashMap<>(headers);
    }

    public void printHeaders() {
        Set<String> keys = headers.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key+", "+headers.get(key).toString());
        }
    }

    private static Map<String, Integer> frequentCells(String page, Map<String,Integer> headers){
        //Map<String, Integer> retvalue=new HashMap<>();
        //TODO: parse cells and count them (hint: remove whitespaces at the beginning and the end of the string by using trim() and use frequence as threshold)
        try {
            //Open the Wiktionary page
            Document doc = Jsoup.connect("https://en.wiktionary.org/wiki/" + page).get();
            //Get all the tables
            Elements tables= doc.getElementsByTag("table");
            //Extract the words from the cells and check them
            for (Element table : tables) {
                if (!(table.className().equalsIgnoreCase("audiotable")||table.className().equalsIgnoreCase("toc"))) {
                    Elements rows=table.getElementsByTag("tr");
                    for (Element row : rows) {
                        Elements cells = row.children();
                        for (Element cell : cells) {
                            if (cell.hasText()) {
                                String celltext = cell.text().trim();
                                if (!celltext.isEmpty()) {
                                    if (headers.containsKey(celltext)) {
                                        headers.put(celltext, headers.get(celltext)+1);
                                    }
                                    else {
                                        headers.put(celltext, new Integer(1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {}
        return headers;
    }

    private static Map<String, Integer> removeNotFrequent(Map<String, Integer> headers, int frequence) {
        Iterator<Map.Entry<String,Integer>> iter = headers.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,Integer> entry = iter.next();
            if(entry.getValue().intValue()<frequence){
                iter.remove();
            }
        }
        return headers;
    }

}
