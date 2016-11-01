NLP Project, Project R1
Until now there are three classes:
    -Main: it contains the main function that loads the xml dump of Wiktionary and starts parsing it by using the TitlesHandler class described below. (FINISHED)
    -TitlesHandler: it's an extension of the SAX default handler. It parses only the "title" tags in the XML file and calls HTMLParser on it. (FINISHED)
    -HTMLParser: here we download the HTML pages related to the titles parsed before and we get all the info from it.
    -HeaderList: by using a txt file it gets the tables from few pages of wiktionary and use them to get the names of the headers of the tables. (FINISHED)
    -PrepTable: it is used to preprocess the table: it gives the main info of it and the list of the cells contained in it (of type Word).
    -Word: A particular data structure: contains the single word and all the headers related to it.
    -WordHeaders: It's a particular data structure for representing an header of a word.