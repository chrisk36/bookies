package org.cis1200.src.bookprocessor;

import java.util.*;

public class MarkovChain {
    private Map<String, List<String>> markovChain = new HashMap<>();
    private Random random = new Random();
    public static final String END_TOKEN = "<END>";

    public void train(String text) {
        String[] words = text.split("\\s+");
        String prevWord = null;
        
        for (String word : words) {
            if (prevWord != null) {
                if (!markovChain.containsKey(prevWord)) {
                    markovChain.put(prevWord, new ArrayList<>());
                }
                markovChain.get(prevWord).add(word);
            }
            prevWord = word;
        }
        
        if (prevWord != null) {
            if (!markovChain.containsKey(prevWord)) {
                markovChain.put(prevWord, new ArrayList<>());
            }
            markovChain.get(prevWord).add(END_TOKEN);
        }
    }

    public String generateSentence(int maxLength) {
        List<String> sentence = new ArrayList<>();
        List<String> keys = new ArrayList<>(markovChain.keySet());
        if (keys.isEmpty()) return "";
        
        String currentWord = keys.get(random.nextInt(keys.size()));
        sentence.add(currentWord);
        
        while (sentence.size() < maxLength) {
            List<String> nextWords = markovChain.get(currentWord);
            if (nextWords == null || nextWords.isEmpty()) break;
            
            String nextWord = nextWords.get(random.nextInt(nextWords.size()));
            if (nextWord.equals(END_TOKEN)) break;
            
            sentence.add(nextWord);
            currentWord = nextWord;
        }
        
        return String.join(" ", sentence);
    }
}