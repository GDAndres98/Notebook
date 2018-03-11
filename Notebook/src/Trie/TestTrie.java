package Trie;

public class TestTrie {
	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.addWord("bear");
		trie.addWord("bell");
		trie.addWord("bid");
		trie.addWord("bull");
		trie.addWord("buy");
		trie.addWord("sell");
		trie.addWord("stock");
		trie.addWord("stop");
		
		System.out.println(trie.inOrden());
	}
}
