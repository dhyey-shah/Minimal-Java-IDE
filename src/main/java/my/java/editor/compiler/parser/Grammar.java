package my.java.editor.compiler.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Grammar {
	private BufferedReader reader;

	public Grammar() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("Grammar.specs");
		reader = new BufferedReader(new InputStreamReader(is));
	}

	static class Node {
		private String name;
		private List<Node> children;
		private Node parent;
		private boolean RECURSIVE;

		Node(String name,boolean terminal) {
			if (!terminal)
				children = new ArrayList<>();
			parent = null;
			this.name = name;
		}

		void addChild(Node c, boolean d) {
			children.add(c);
			RECURSIVE = d;
		}

		void addParent(Node p) {
			parent = p;
		}

		List<Node> getChildren() {
			return children;
		}
		
		boolean isRecursive() {
			return RECURSIVE;
		}
	}
	
	public void parseGrammar() {
		char x;
		int i = 0;
		String v="";
		List<Node> nodes = new ArrayList<>();
		
		try {
			i = reader.read();
			while(i != -1) {
				x = (char) i;
				
				if(x == ':') {
					Node n = new Node(v,true);
					v="";
					
					x = (char) reader.read();
					if(x == '=') {
						x = (char) reader.read();
						if(x == '"') {
							i = reader.read();
							while((x=(char) i) != '"' && i!= -1) {
								i = reader.read();
							}
						}
						if(x == '<') {
							i = reader.read();
							while((x=(char) i) != '>' && i!= -1) {
								v+=x;
								i = reader.read();
							}
							n.addChild(new Node(v,true),false);
						}
						if(x == '{') {
							i = reader.read();
							while((x=(char) i) != '}' && i!= -1) {
								v+=x;
								i = reader.read();
							}
							n.addChild(new Node(v,true),false);
						}
					}
				}
				else if(Character.isLetter(x)) {
					v+=x;
				}
				i= reader.read();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
