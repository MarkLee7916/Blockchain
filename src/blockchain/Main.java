package blockchain;

public class Main {

	public static void main(String[] args) {
		Blockchain blockchain = new Blockchain();
		Block b = new Block("-1", 87);
		String h;

		for (int i = 0; i < 100; i++) {
			h = b.getHash();
			blockchain.append(b);
			b = new Block(h, 123);
		}

		System.out.println("Is Valid? " + blockchain.isValid());
		blockchain.printHashContents();
	}
}
