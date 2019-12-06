package my.java.editor.compiler.lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TokenParser {
	private BufferedReader reader;

	public TokenParser() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("JavaToken.specs");
		reader = new BufferedReader(new InputStreamReader(is));
	}
	
	public TokenParser(String file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public TokenParser(File file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public TokenParser(InputStream is) {
		try {
			reader = new BufferedReader(new InputStreamReader(is));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List parseTokens() {
		char x;
		int i = 0;
		List<ArrayList<String>> attributeSet = new ArrayList<>();
		ArrayList<String> currIter = new ArrayList<String>();
		String attr = "";

		try {
			i = reader.read();
			while (i != -1) {
				x = (char) i;
				switch (x) {
				case ' ':
					break;
				case ':':
					attributeSet.add(new ArrayList<String>());
					currIter = attributeSet.get(attributeSet.size() - 1);
					break;

				case '{':
					x = (char) reader.read();
					while (x != '}') {
						if (x == ' ' || x == '\t' || x == '\r' || x == '\n') {
							if (attr != "")
								currIter.add(attr);
							attr = "";
						} else if (x == '|') {
							attr += x;
						} else {
							attr += x;
						}
						x = (char) reader.read();
					}
					// System.out.print(attributeSet);

					break;
				}

				i = reader.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return attributeSet;
	}

	public static void main(String[] args) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("JavaToken.specs");

		new TokenParser(is).parseTokens();
	}
}
