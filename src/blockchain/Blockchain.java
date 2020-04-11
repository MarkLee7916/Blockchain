package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
	private final List<Block> chain;

	public Blockchain() {
		chain = new ArrayList<>();
	}

	public boolean isEmpty() {
		return chain.isEmpty();
	}

	public boolean isValid() {
		for (int i = 1; i < chain.size(); i++)
			if (!chain.get(i).getPreviousHash().equals(chain.get(i - 1).getHash()))
				return false;

		return true;
	}

	public void append(Block block) {
		if (isEmpty())
			startChain(block);
		else
			appendNewBlock(block);
	}	
	
	public void printHashContents() {
		for (Block block: chain) {
			System.out.println("Previous Hash is " + block.getPreviousHash());
			System.out.println("Hash is " + block.getHash() + "\n");
		}
	}

	private void appendNewBlock(Block block) {
		Block top = chain.get(chain.size() - 1);

		if (!block.getPreviousHash().equals(top.getHash()))
			throw new AssertionError("Block's previous hash entry doesn't match the previous blocks hash");
		
		chain.add(block);
	}

	private void startChain(Block block) {
		chain.add(block);
	}
}
