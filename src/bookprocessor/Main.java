package bookprocessor;

public class Main {
    public static void main(String[] args) {
        // Replace with scrapped string from Chris. If there are multiple books just concatenate them.
        String rawText = "";

        // 1. Clean raw text
        String cleanedText = BookCleaner.cleanText(rawText);
        //System.out.println("=== CLEANED TEXT ===");
        //System.out.println(cleanedText.substring(0, Math.min(500, cleanedText.length())) + "\n...");

        // 2. Train sentence generator
        SentenceGenerator generator = new SentenceGenerator();
        generator.train(cleanedText);

        // 3. Generate normal sentences (without keyword)
        System.out.println("\n=== RANDOM GENERATED SENTENCES ===");
        for (int i = 0; i < 5; i++) {
            System.out.println(generator.generate(15) + ".");
        }

        // 4. Generate with keyword
        String keyword = ""; //ADD SOME SORT OF WAY FOR THE USER TO INPUT THE KEYWORD THEY WANT
        System.out.println("\n=== SENTENCES WITH KEYWORD: \"" + keyword + "\" ===");
        for (int i = 0; i < 5; i++) {
            System.out.println(generator.generateWithKeyword(keyword, 15) + ".");
        }
    }
}
