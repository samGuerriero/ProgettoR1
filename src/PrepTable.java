import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by marco on 26/10/16.
 * In this class we want to take the table and preprocess it to get the cell values and the headers and all the other info.
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
    
    public void preprocessTable(TitlesHandler th){
    	HeaderList headers = th.getHeaders();
    	/*In the table:
    		-	each row is a tr, either containing all th, or th and corresponding td. In some languages, it may contain all td, 
    					when the descriptor is expressed together with the inflection (e.g. Infinitive: jesti)
    		-	th is the header containing the descriptor;
    		-	td is the inflection, empty if it does not exist;
    		-	Hence, if you assign to each th its position, you get the priority. */

    	
    }

	public List<Word> getWord() {
		return word;
	}

	public void setWord(List<Word> word) {
		this.word = word;
	}
    
    
}