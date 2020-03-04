package md2html;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Scan implements Closeable {
    private static final int MAX_SIZE = 2 << 16;
    private Reader reader;
    private int size = MAX_SIZE;
    private char[] buffer;
    private int pos;
    private int last;
    private boolean DIGIT = false;
    private boolean WORDS = true;
    private static char[] END_CHARS = System.lineSeparator().toCharArray();

    public Scan(InputStream input) {
        this.reader = new InputStreamReader(input, UTF_8);
        buffer = new char[size];
    }

    public Scan(String input) {
        this(input, input.length() > 0 ? input.length() : 1);
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
        //System.out.print(buffer[pos]);
        return buffer[pos++];
    }

    public boolean hasNextInt() throws IOException {
        return hasNext(DIGIT);
    }

    public boolean hasNext() throws IOException {
        return hasNext(WORDS);
    }

    private boolean hasNext(boolean code) throws IOException {
        if (!hasNextChar()) {
            return false;
        }
        while (code ? !isWord(buffer[pos]) : !isDigit(buffer[pos])) {
            nextChar();
            if (!hasNextChar()) {
                return false;
            }
        }
        return true;
    }

    private String next(boolean code) throws IOException {
        StringBuilder builder = new StringBuilder();
        char x = nextChar();
        while (code ? isWord(x) : isDigit(x)) {
            builder.append(x);
            if (hasNextChar()) {
                x = nextChar();
            } else {
                break;
            }
        }
        return builder.toString();
    }


    public String next() throws IOException {
        return next(WORDS);
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next(DIGIT));
    }

    public boolean hasNextLine() throws IOException {
        return hasNextChar();
    }

    public String nextLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        char c = nextChar();
        while (!isEnd(c)) {
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

    private static boolean isEnd(char c) {
        return c == END_CHARS[0];
    }

    private static boolean isDigit(int c) {
        return Character.isDigit(c) || c == '+' || c == '-';
    }
}
