public class BoyerMooreAlgorithm {
    private int[] charTable;
    private int[] offsetTable;

    public int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        int count = 0;

        // Initialize character table and offset table
        charTable = makeCharTable(pattern);
        offsetTable = makeOffsetTable(pattern);

        // Search for the pattern in the text
        int i = patternLength - 1;
        while (i < textLength) {
            int j = patternLength - 1;
            while (text.charAt(i) == pattern.charAt(j)) {
                if (j == 0) {
                    count++;
                    break;
                }
                i--;
                j--;
            }
            i += Math.max(offsetTable[patternLength - 1 - j], charTable[text.charAt(i)]);
        }

        return count;
    }
    // Create character table based on pattern
    private int[] makeCharTable(String pattern) {
        final int tableSize = 65536;
        int[] table = new int[tableSize];
        int patternLength = pattern.length();

        for (int i = 0; i < tableSize; i++) {
            table[i] = patternLength;
        }

        for (int i = 0; i < patternLength - 1; i++) {
            table[pattern.charAt(i)] = patternLength - 1 - i;
        }

        return table;
    }
    // Create offset table based on pattern
    private int[] makeOffsetTable(String pattern) {
        int patternLength = pattern.length();
        int[] table = new int[patternLength];
        int lastPrefixPosition = patternLength;

        for (int i = patternLength - 1; i >= 0; i--) {
            if (isPrefix(pattern, i + 1)) {
                lastPrefixPosition = i + 1;
            }
            table[patternLength - 1 - i] = lastPrefixPosition - i + patternLength - 1;
        }

        for (int i = 0; i < patternLength - 1; i++) {
            int suffixLength = suffixLength(pattern, i);
            table[suffixLength] = patternLength - 1 - i + suffixLength;
        }

        return table;
    }
    // Check if pattern is prefix of suffix starting at position p
    private boolean isPrefix(String pattern, int p) {
        int patternLength = pattern.length();
        for (int i = p, j = 0; i < patternLength; i++, j++) {
            if (pattern.charAt(i) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }
    // Calculate length of longest suffix of pattern ending at position p
    private int suffixLength(String pattern, int p) {
        int patternLength = pattern.length();
        int length = 0;
        for (int i = p, j = patternLength - 1; i >= 0 && pattern.charAt(i) == pattern.charAt(j); i--, j--) {
            length++;
        }
        return length;
    }
}