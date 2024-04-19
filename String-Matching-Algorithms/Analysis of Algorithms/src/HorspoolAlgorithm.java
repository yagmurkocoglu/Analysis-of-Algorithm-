public class HorspoolAlgorithm {
    public int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        int count = 0; // Counting the number of patterns

        // Create a character translation table
        int[] shiftTable = new int[65536];
        for (int i = 0; i < 65536; i++) {
            shiftTable[i] = patternLength;
        }
        for (int i = 0; i < patternLength - 1; i++) {
            shiftTable[pattern.charAt(i)] = patternLength - 1 - i;
        }
        
        int i = 0;
        while (i <= textLength - patternLength) {
            int j = patternLength - 1;

            // Comparison of the pattern starting from the end
            while (j >= 0 && text.charAt(i + j) == pattern.charAt(j)) {
                j--;
            }

            // Check that the pattern matches exactly
            if (j < 0) {
                count++;
                i += shiftTable[text.charAt(i + patternLength - 1)];
            } else {
                i += shiftTable[text.charAt(i + j)];
            }
        }

        return count;
    }

    private static boolean isPrefix(String word, int p) {
        for (int i = p, j = 0; i < word.length(); i++, j++) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    private static int getSuffixLength(String word, int p) {
        int length = 0;
        for (int i = p, j = word.length() - 1; i >= 0 && word.charAt(i) == word.charAt(j); i--, j--) {
            length++;
        }
        return length;
    }
}