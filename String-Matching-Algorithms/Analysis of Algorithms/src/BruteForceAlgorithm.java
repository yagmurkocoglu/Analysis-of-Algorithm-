public class BruteForceAlgorithm {
    public int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        int count = 0; // Counting the number of patterns

        for (int i = 0; i <= textLength - patternLength; i++) {
            int j;

            // Compare each character of the pattern with the characters in the text
            for (j = 0; j < patternLength; j++) {
                if (text.charAt(i + j) != pattern.charAt(j))
                    break;
            }

            // Check that the pattern matches exactly
            if (j == patternLength) {
                count++;
            }
        }

        return count;
    }
}