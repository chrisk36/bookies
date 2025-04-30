package bookprocessor;

import java.util.regex.*;

public class BookCleaner {

    /**
     * Cleans raw Project Gutenberg text by removing headers, footers,
     * chapter labels, TOCs, and non-story sections.
     */
    public static String cleanText(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return "";
        }

        // Normalize line endings
        String text = rawText.replaceAll("\\r\\n?", "\n");

        // Step 1: Keep only content between START and END markers (if available)
        text = extractBetweenMarkers(text);

        // Step 2: Remove tables of contents, prefaces, notes, etc.
        text = removeFrontAndBackMatter(text);

        // Step 3: Remove chapter headers like "CHAPTER I" or "IN WHICH..."
        text = removeChapterHeadings(text);

        // Step 4: Remove all-caps standalone headings (usually titles, contents, etc.)
        text = text.replaceAll("(?m)^\\s*[A-Z ,.'\"-]{10,}\\s*$", "");

        // Step 5: Remove Gutenberg footer lines or junk
        text = text.replaceAll("(?i).*Project Gutenberg.*", "");

        // Step 6: Compress multiple blank lines
        text = text.replaceAll("\n{3,}", "\n\n");

        return text.trim();
    }

    private static String extractBetweenMarkers(String text) {
        StringBuilder cleaned = new StringBuilder();
        String startRegex = "(?i)^\\*\\*\\* START OF (THIS|THE) PROJECT GUTENBERG EBOOK.*?$";
        String endRegex = "(?i)^\\*\\*\\* END OF (THIS|THE) PROJECT GUTENBERG EBOOK.*?$";

        Pattern startPattern = Pattern.compile(startRegex, Pattern.MULTILINE);
        Pattern endPattern = Pattern.compile(endRegex, Pattern.MULTILINE);

        Matcher startMatcher = startPattern.matcher(text);
        Matcher endMatcher = endPattern.matcher(text);

        int lastEnd = 0;
        while (startMatcher.find()) {
            int startIdx = startMatcher.end();
            if (endMatcher.find(startIdx)) {
                int endIdx = endMatcher.start();
                cleaned.append(text, startIdx, endIdx).append("\n\n");
                lastEnd = endMatcher.end();
            } else {
                // Only START found, take until end of file
                cleaned.append(text.substring(startIdx)).append("\n\n");
                break;
            }
        }

        if (cleaned.length() == 0 && lastEnd == 0) {
            // No markers found; fallback
            return text;
        }

        return cleaned.toString();
    }

    private static String removeFrontAndBackMatter(String text) {
        // Common Gutenberg front/back matter
        String[] sections = {
            "(?is)(TABLE OF CONTENTS.*?)\\n\\s*(CHAPTER|BOOK|I\\.|I\\s)",
            "(?is)(DEDICATION.*?)\\n\\s*(CHAPTER|BOOK|I\\.|I\\s)",
            "(?is)(PREFACE.*?)\\n\\s*(CHAPTER|BOOK|I\\.|I\\s)",
            "(?is)(INTRODUCTION.*?)\\n\\s*(CHAPTER|BOOK|I\\.|I\\s)",
            "(?is)(TRANSCRIBER['’]S? NOTE.*?)\\n\\s*(CHAPTER|BOOK|I\\.|I\\s)"
        };

        for (String pattern : sections) {
            text = text.replaceAll(pattern, "");
        }

        // Remove illustrations, footnotes, etc.
        text = text.replaceAll("(?is)\\[Illustration:.*?\\]", "");
        text = text.replaceAll("(?is)\\[Footnote:.*?\\]", "");
        text = text.replaceAll("(?is)\\[.*?Project Gutenberg.*?\\]", "");

        return text;
    }

    private static String removeChapterHeadings(String text) {
        // Removes patterns like:
        // "CHAPTER I", "CHAP. IV", "IN WHICH POOH DOES SOMETHING", "BOOK ONE"
        String chapterPattern = "(?m)^\\s*(CHAPTER|CHAP\\.?|BOOK)?\\s*((THE )?[A-Z][A-Z\\s\\-\\d]{0,40})\\s*$";
        return text.replaceAll(chapterPattern, "");
    }
}
