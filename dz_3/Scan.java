import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Scan implements Closeable {
    private static final int MAX_SIZE = 2 << 16;
    private static final boolean[] DIGITS = getDigits();
    private static final boolean[] WORDS = getWords();
    private static final int CHAR_SIZE = 2 << 16;
    private Charset encode = StandardCharsets.UTF_8;
    private Reader reader;
    private int size = MAX_SIZE;
    private char[] buffer;
    private int pos;
    private int last;

    public Scan(FileInputStream f) {
        this.reader = new InputStreamReader(f, encode);
        buffer = new char[size];
    }

    public Scan(InputStream input) {
        this.reader = new InputStreamReader(input, encode);
        buffer = new char[size];
    }

    public Scan(String input) {
        this.reader = new StringReader(input);
        size = input.length();
        buffer = new char[size];
    }

    public Scan(String input, int len) {
        this.reader = new StringReader(input);
        size = len;
        buffer = new char[size];
    }

    private boolean readBuffer() throws IOException {
        last = reader.read(buffer, 0, size);
        pos = 0;
        return last != -1;
    }

    private char nextChar() {
        return buffer[pos++];
    }

    boolean hasNext() throws IOException {
        return hasNext(WORDS);
    }

    boolean hasNextInt() throws IOException {
        return hasNext(DIGITS);
    }

    private boolean hasNext(boolean[] type) throws IOException {
        if (!hasNextChar()) {
            return false;
        }
        while (!type[buffer[pos]]) {
            nextChar();
            if (!hasNextChar()) {
                return false;
            }
        }
        return true;
    }

    String next() throws IOException {
		return next(WORDS);
    }

    int nextInt() throws IOException {
    	return Integer.parseInt(next(DIGITS));
	}

    private String next(boolean[] type) throws IOException {
        StringBuilder builder = new StringBuilder();
        char x = nextChar();
        while (type[x]) {
            builder.append(x);
            if (hasNextChar()) {
                x = nextChar();
            } else {
                break;
            }
        }
        return builder.toString();
    }

    boolean hasNextLine() throws IOException {
        return hasNextChar();
    }

    String nextLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        char c = nextChar();
        while (Character.getType(c) != Character.CONTROL) {
            builder.append(c);
            if (!hasNextChar()) {
                break;
            }
            c = nextChar();
        }
        if (hasNextChar()) {
            nextChar();
        }
        return builder.toString();
    }

    public void close() throws IOException {
        reader.close();
    }

    private boolean hasNextChar() throws IOException {
        return !(pos >= last && !readBuffer());
    }

    private static boolean isWord(int c) {
        return Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION;
    }

    private static boolean isDigit(int c) {
        return Character.isDigit(c) || c == '+' || c == '-';
    }

    private static boolean[] getWords() {
        boolean[] code = new boolean[CHAR_SIZE];
        for (int i = 0; i < CHAR_SIZE; i++) {
            code[i] = isWord(i);
        }
        return code;
    }

    private static boolean[] getDigits() {
        boolean[] code = new boolean[CHAR_SIZE];
        for (int i = 0; i < CHAR_SIZE; i++) {
            code[i] = isDigit(i);
        }
        return code;
    }
}
