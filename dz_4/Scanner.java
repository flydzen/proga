import java.io.*;
import java.nio.charset.StandardCharsets;


class Scanner {
    private static final int BUFFER_SIZE = 1 << 16;
    private char[] buffer = new char[BUFFER_SIZE];
    private int pos;
    private int last;
    private BufferedReader stream;
    private char[] sep = System.getProperty("line.separator").toCharArray();
    private boolean ended = false;

    Scanner(InputStream in) throws IOException {
        stream = new BufferedReader(new InputStreamReader(in));
        pos = 0;
        last = 0;
    }

    Scanner(String name) throws FileNotFoundException {
        stream = new BufferedReader(new InputStreamReader(new FileInputStream(name), StandardCharsets.UTF_8));
        pos = 0;
        last = 0;
    }

    boolean hasNext() {
        return !ended;
    }

    boolean hasNextLine() throws IOException {
        if (!hasNext()) {
            return false;
        }
        char b = nextChar();
        while (Character.isWhitespace(b) && b != sep[0] && !ended) {
            b = nextChar();
        }
        if (b == sep[0]) {
            while (Character.isWhitespace(b) && !ended) {
                b = nextChar();
            }
            pos--;
            return false;
        } else {
            pos--;
            return true;
        }
    }

    private char[] upSize(char[] ch) {
        char[] newCh = new char[ch.length * 2];
        System.arraycopy(ch, 0, newCh, 0, ch.length);
        return newCh;
    }

    String nextLine() throws IOException {
        char[] chars = new char[64];
        int ind = 0;
        char b = nextChar();
        boolean key = false;
        while (Character.isWhitespace(b)) {
            b = nextChar();
        }
        while (last != -1) {
            key = true;
            b = nextChar();
            if (last == -1) {
                break;
            }
            if (b != sep[0]) {
                if (ind == chars.length) {
                    chars = upSize(chars);
                }
                chars[ind++] = b;
            } else {
                break;
            }
        }
        return key ? new String(chars, 0, ind) : null;
    }

    String nextString() throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = nextChar();
        while (Character.isWhitespace(c)) {
            c = nextChar();
        }
        while (!Character.isWhitespace(c) && !ended) {
            sb.append(c);
            c = nextChar();
        }
        if (!ended) {
            pos--;
        }
        return sb.toString();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextString());
    }

    private char nextChar() throws IOException {
        if (pos == last) {
            last = stream.read(buffer);
            pos = 0;
            if (last == -1) {
                ended = true;
            }
        }
        return buffer[pos++];
    }
}