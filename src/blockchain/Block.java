package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Block {
	private final String previousHash;
	private final String hash;
	private final long id;
	private final long timestamp;
	private final int zerosNeeded;

	public Block(String p, long i) {
		previousHash = p;
		id = i;
		timestamp = System.currentTimeMillis();
		zerosNeeded = secureRandomNumber(2, 5);
		hash = generateHash();
	}

	private int secureRandomNumber(int lowerLimit, int upperLimit) {
		SecureRandom rng = new SecureRandom();

		return rng.nextInt(upperLimit) + lowerLimit;
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	// Generates a hash that satisifies computational work constraint using SHA-256
	private String generateHash() {
		MessageDigest hashFunction = getHashFunction();
		
		int magicNumber;
		byte[] hash;
		String representation;
		String hexHash;

		do {
			magicNumber = secureRandomNumber(0, Integer.MAX_VALUE);
			representation = id + timestamp + previousHash + magicNumber;

			hash = hashFunction.digest(representation.getBytes(StandardCharsets.UTF_8));
			hexHash = byteArrayToHexString(hash);

		} while (!satisfiesZerosNeeded(hexHash));

		return hexHash;
	}

	// Returns true if hash satisifies computational work constraint
	private boolean satisfiesZerosNeeded(String hash) {
		for (int i = 0; i < zerosNeeded; i++)
			if (hash.charAt(i) != '0')
				return false;

		return true;
	}

	// Returns a digest that runs the SHA-256 cryptographic hash function
	private MessageDigest getHashFunction() {
		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return digest;
	}

	private String byteArrayToHexString(byte[] hash) {
		StringBuilder hexString = new StringBuilder("");

		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);

			if (hex.length() == 1)
				hexString.append('0');

			hexString.append(hex);
		}

		return hexString.toString();
	}
}
