NLP Project, Project R1
Until now there are three classes:
    -Main: it contains the main function that loads the xml dump of Wiktionary and starts parsing it by using the TitlesHandler class described below.
    -TitlesHandler: it's an extension of the SAX default handler. It parses only the "title" tags in the XML file and calls HTMLParser on it.
    -HTMLParser: here we download the HTML pages related to the titles parsed before and we get all the info from it.