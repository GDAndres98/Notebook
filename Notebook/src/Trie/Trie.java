package Trie;

public class Trie {
	private int words;
	private int prefixes;
	private Trie[] edges;
	
	public Trie() {
		this.words = 0;
		this.prefixes = 0;
		this.edges = new Trie[26];
	}
	
	public void addWord(String word) {
		if(word.length() == 0)
			words++;
		else {
			prefixes++;
			int k = word.charAt(0) - 'a';
			if(edges[k] == null)
				edges[k] = new Trie();
			word = word.substring(1);
			edges[k].addWord(word);
		}
	}
	
	public int countWords(String word) {
		if(word.length() == 0)
			return words;
		int k = word.charAt(0) - 'a';
		if(edges[k] == null)
			return 0;
		word = word.substring(1);
		return edges[k].countWords(word);
	}
	
	public int countPrefixes(String prefix) {
		if(prefix.length() == 0)
			return prefixes;
		int k = prefix.charAt(0) - 'a';
		if (edges[k] == null)
			return 0;
		prefix = prefix.substring(1);
		return edges[k].countPrefixes(prefix);
	}
	
	public String inOrden() {
		String a = "";
		for (int i = 0; i < edges.length; i++)
			if(edges[i] != null)
				a += edges[i].inOrden() + " " + (char)(i + 'a') + " ";
		return a;
	}
}
