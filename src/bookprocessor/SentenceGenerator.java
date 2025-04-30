package org.cis1200.src.bookprocessor;

import java.util.*;
import java.util.regex.*;

public class SentenceGenerator {
    private MarkovChain markovChain = new MarkovChain();
    private Set<String> normalizedWords = new HashSet<>();

    /**
     * Trains the MarkovChain on the cleaned text.
     * Stores lowercased, punctuation-stripped words for keyword checking.
     */
    public void train(String cleanedText) {
        markovChain.train(cleanedText);
        normalizedWords.clear();

        // Normalize all words for keyword tracking
        for (String word : cleanedText.split("\\s+")) {
            normalizedWords.add(normalizeWord(word));
        }
        System.out.println ("trained!");
        for (String str: normalizedWords) {
            System.out.println (str);
        }
    }

    /**
     * Generates a random sentence of a given length.
     */
    public String generate(int length) {
        return markovChain.generateSentence(length);
    }

    /**
     * Generates a sentence that includes the given keyword.
     * Uses word-boundary match for accuracy.
     */
    public String generateWithKeyword(String keyword, int length) {
        if (keyword == null || keyword.isEmpty() || length <= 0) {
            return generate(length);
        }

        String normKeyword = normalizeWord(keyword);
        if (!normalizedWords.contains(normKeyword)) {
            return "[Keyword not found in training text: \"" + keyword + "\"]";
        }

        Pattern keywordPattern = Pattern.compile("\\b" + Pattern.quote(normKeyword) + "\\b", Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < 100; i++) {
            String sentence = markovChain.generateSentence(length);
            if (keywordPattern.matcher(sentence).find()) {
                return capitalizeSentence(sentence);
            }
        }

        return "[Unable to generate sentence with keyword after 100 tries.]";
    }

    /**
     * Strips punctuation and lowercases the word for normalization.
     */
    private String normalizeWord(String word) {
        return word.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    /**
     * Capitalizes the first letter of the sentence and adds a period if needed.
     */
    private String capitalizeSentence(String sentence) {
        sentence = sentence.trim();
        if (sentence.isEmpty()) return sentence;
        sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
        if (!sentence.matches(".*[.!?]$")) {
            sentence += ".";
        }
        return sentence;
    }
}
