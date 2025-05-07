========================
Project Name: Bookies
========================

Description:
------------
Bookies is a Java-based text generation tool that allows users to generate paragraphs
in the style of books from Project Gutenberg. Users can:

- Select an author
- Pick a book
- Choose a keyword
- Specify a desired word count

The program then generates a passage using a graph-based Markov model of the selected book’s text.

Categories:
-----------
Graph and Graph Algorithms
- Sentence generation is powered by N-grams using a weighted word adjacency graph.
- Nodes represent words; edges represent adjacency frequency (weights).
- Graphs are built from concatenated plaintexts across books.
- Data is cleaned to remove front/back matter (e.g., TOCs, publisher info).

Document Search
- Parses the GUTINDEX.ALL file from Project Gutenberg.
- Extracts author names, book titles, and book ID numbers for fast lookup.
- Retrieves plaintext files from the Gutenberg site.
- Cleans and filters documents using jsoup and regex for usable sentence generation.

Work Breakdown:
---------------

Natalie Lim (Frontend + Project Integration)
- Designed and implemented the frontend UI.
- Integrated UI with web scraping and sentence generation backend.
- Files:
  - RunMyProject.java:
    - Launches GUI with JFrame
    - Manages user input flow: author → length → book number → keyword
    - Loads author data and sets global app state
  - Bookies.java:
    - Manages UI views (intro, author/book pick, text generation)
    - Renders views with paintComponent()
    - Connects to backend components: GutenbergSearch, BookCleaner, SentenceGenerator
    - Handles keyboard UI interaction and repainting
- Wrote user manual

Kate Li (Text Processing + Sentence Generation)
- Created backend pipeline for text processing and sentence generation
- Files:
  - Main.java: handles sentence generation workflow
  - BookCleaner.java: cleans downloaded texts (TOC, metadata, etc.) using jsoup/regex
  - MarkovChain.java: builds and trains word association model
  - SentenceGenerator.java: generates keyword-based or random sentences using Markov probabilities
- Added code comments and documentation for UI teammate
- Contributed to user manual and project ideation

Christian Kim (Web Scraping + Metadata Search)
- Developed text and metadata retrieval tools from Project Gutenberg
- File:
  - GutenbergSearch.java:
    - Downloads and parses GUTINDEX.ALL for author/book metadata
    - Retrieves and stores book plaintext files via Java networking
    - Enables book search by author/title/ID
    - Implements error handling for network issues and missing files
- Ensured backend logic could integrate with UI via modular methods