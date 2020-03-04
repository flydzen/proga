import java.io.*;
import java.util.*;

public class MyScanner implements Closeable {
	private static final int DEFAULT_BUFFER = 1024;
	public static final boolean[] DIGITS_CODE = getDigitsCode();
	public static final boolean[] WORDS_CODE = getWordsCode();
	private static final int MAX_SYMBOLS = 65536;

	private Reader reader;
	private int bufferSize;
	private char[] buffer;
	private int pos;
	private int curBufferSize;
	
	public MyScanner(InputStream input, int bufferSize, String encode) throws UnsupportedEncodingException {
		this.reader = new InputStreamReader(input, encode);
		createBuffer(bufferSize);
	} 

	public MyScanner(InputStream input) throws UnsupportedEncodingException {
		this(input, DEFAULT_BUFFER, "utf-8");
	} 

	public MyScanner(String input, int bufferSize, String encode) throws UnsupportedEncodingException {
		this.reader = new StringReader(input);
		createBuffer(bufferSize);
	} 

	public MyScanner(String input) throws UnsupportedEncodingException  {
		this(input, DEFAULT_BUFFER, "utf-8");
	} 

	private void createBuffer(int bufferSize) {
		this.bufferSize = bufferSize;
		this.buffer = new char[bufferSize];
	}

	private boolean readBuffer() throws IOException, EOFException {
		curBufferSize = reader.read(buffer, 0, bufferSize);
		pos = 0;
		return curBufferSize != -1;
	}

	private boolean hasNextChar() throws EOFException, IOException {
		return !(pos >= curBufferSize && !readBuffer());
	}

	private char nextChar() throws IOException, EOFException {
		if (!hasNextChar()) {
			throw new EOFException();
		}
		return buffer[pos++];  
	}

	private char curChar() throws IOException, EOFException {
		if (!hasNextChar()) {
			throw new EOFException();
		}
		return buffer[pos];  
	}

	public boolean hasNext(boolean[] code) throws IOException, EOFException {
		if (!hasNextChar()) {
			return false;
		}
		while (!code[curChar()]) {
			nextChar();
			if (!hasNextChar()) {
				return false;
			}
		}
		return true;
	}

	public String next(boolean[] code) throws IOException, InputMismatchException, EOFException {
		StringBuilder builder = new StringBuilder();
		if (!hasNextChar()) {
			throw new EOFException();
		}
		if (!hasNext(code)) {
			throw new InputMismatchException();
		}
		char x = nextChar();
		while (code[x]) {
			builder.append(x);
			if (hasNextChar()) {
				x = nextChar();
			} else {
				break;
			}
		}
		return builder.toString();		
	}

	public boolean hasNextLine() throws IOException {
		return hasNextChar();
	}

	public String nextLine() throws IOException, EOFException {
		StringBuilder builder = new StringBuilder();
		char x = nextChar();
		while (!isSep(x)) {
			builder.append(x);
			if (!hasNextChar()) {
				break;
			}
			x = nextChar();
		}
		if (hasNextChar()) {
			x = nextChar();
		}
		return builder.toString();
	}

	public void close() throws IOException {
		reader.close();
	}

	private boolean isSep(char x) {
		return Character.getType(x) == Character.CONTROL;
	}

	private static boolean[] getWordsCode() {
		boolean[] code = new boolean[MAX_SYMBOLS];
		for (int i = 0; i < MAX_SYMBOLS; i++) {
			code[i] = Character.isLetter(i) || i == '\'' || Character.getType(i) == Character.DASH_PUNCTUATION;
		}
		return code;
	}

	private static boolean[] getDigitsCode() {
		boolean[] code = new boolean[MAX_SYMBOLS];
		for (int i = 0; i < MAX_SYMBOLS; i++) {
			code[i] = Character.isDigit(i) || i == '+' || i == '-';
		}
		return code;
	}
}
