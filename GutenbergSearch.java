package org.cis1200;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GutenbergSearch {
    private static final String INDEX_URL = "https://www.gutenberg.org/dirs/GUTINDEX.ALL";
    private static List<BookEntry> entries = new ArrayList<>();

    // Represents a single book entry
    static class BookEntry {
        String title;
        String author;
        int ebookNumber;

        BookEntry(String title, String author, int ebookNumber) {
            this.title = title;
            this.author = author;
            this.ebookNumber = ebookNumber;
        }
    }

    // Downloads and parses the GUTINDEX.ALL file
    public static void loadIndex() throws IOException {
        URL url = new URL(INDEX_URL);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
    
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            if (line.isEmpty() || !line.contains(", by ")) continue;
    
            // Try to find the eBook number at the end
            String[] words = line.split(" ");
            String lastWord = words[words.length - 1];
    
            try {
                int ebookNumber = Integer.parseInt(lastWord);
    
                // Remove number from line
                String main = line.substring(0, line.lastIndexOf(lastWord)).strip();
    
                // Split into title and author
                int byIndex = main.lastIndexOf(", by ");
                if (byIndex != -1) {
                    String title = main.substring(0, byIndex).strip();
                    String author = main.substring(byIndex + 5).strip();
    
                    entries.add(new BookEntry(title, author, ebookNumber));
                }
    
            } catch (NumberFormatException e) {
                // Ignore lines without numeric IDs at the end
            }
        }
    
        reader.close();
    }
    

    // Checks if an author exists in the index
    public static boolean authorExists(String authorName) {
        String lower = authorName.toLowerCase();
        for (BookEntry entry : entries) {
            if (entry.author.toLowerCase().contains(lower)) {
                return true;
            }
        }
        return false;
    }
    

    // Retrieves a list of books by the specified author
    public static List<BookEntry> getBooksByAuthor(String authorName) {
        List<BookEntry> books = new ArrayList<>();
        String lower = authorName.toLowerCase();
        for (BookEntry entry : entries) {
            if (entry.author.toLowerCase().contains(lower)) {
                books.add(entry);
            }
        }
        return books;
    }    

    //Gets plaintext from the number in getBooksByAuthor
    public static String getPlaintextByEbookNumber(int ebookNumber) {
        String url = "https://www.gutenberg.org/files/" + ebookNumber + "/" + ebookNumber + "-0.txt";
        try {
            URL bookUrl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(bookUrl.openStream()));
            StringBuilder bookText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                bookText.append(line).append("\n");
            }
            reader.close();
            return bookText.toString();
        } catch (IOException e) {
            return "Error downloading book: " + e.getMessage();
        }
    }
    

    // Retrieves the plain text URL for a specific book title
    public static String getPlaintext(String bookTitle) {
        for (BookEntry entry : entries) {
            if (entry.title.equalsIgnoreCase(bookTitle)) {
                String url = "https://www.gutenberg.org/files/" + entry.ebookNumber + "/" + entry.ebookNumber + "-0.txt";
    
                try {
                    URL bookUrl = new URL(url);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(bookUrl.openStream()));
                    StringBuilder bookText = new StringBuilder();
                    String line;
    
                    while ((line = reader.readLine()) != null) {
                        bookText.append(line).append("\n");
                    }
                    reader.close();
    
                    return bookText.toString();
                } catch (IOException e) {
                    return "⚠️ Error downloading book: " + e.getMessage();
                }
            }
        }
        return "Book not found in index.";
    }

    //Saves to CSV (just for checking if works correctly)
    public static void saveToFile(String content, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
            System.out.println("Book saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
}
