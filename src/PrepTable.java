import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by marco on 26/10/16.
 * In this class we want to take the table and preprocess it to get the cell values and the headers and all the other infos.
 * The stored values will be the list of words contained in the table with all the related headers
 */
public class PrepTable {
    private String title;
    private String language;
    private String pos;
    private List<Word> word;

    public PrepTable(Element table, String title, String language, String pos) {
        this.title=title;
        this.language=language;
        this.pos=pos;
    }
}
