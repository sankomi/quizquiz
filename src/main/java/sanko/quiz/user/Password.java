package sanko.quiz.user;

import java.nio.ByteBuffer;
import java.security.*; //NoSuchAlgorithmException, InvalidKeyException
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

public class Password {

	public static boolean verify(String key, String password) {
		String check = fromTime(key);

		return check.equals(password);
	}

	public static String fromTime(String key) {
		return fromTime(key, System.currentTimeMillis() / 1000L);
	}

	public static String fromTime(String key, long time) {
		//get count from time
		long count = time / 30L;

		return fromCount(key, count);
	}

	public static String fromCount(String key, long count) {
		//convert count to bytes
		byte[] c = ByteBuffer.allocate(Long.BYTES)
			.putLong(count)
			.array();

		//convert key to bytes
		byte[] k = new Base32().decode(key);

		//hash count with key
		byte[] hash = hmac(k, c);
		if (hash == null) return null;

		//use last 4 bits as offset
		int offset = hash[hash.length - 1] & 0xf;

		//get 4 bytes from offset (mask first with 0x7f and rest with 0xff)
		int first = hash[offset] & 0x7f;
		int second = hash[offset + 1] & 0xff;
		int third = hash[offset + 2] & 0xff;
		int fourth = hash[offset + 3] & 0xff;

		//get number from 4 bytes
		int number = first << 24 | second << 16 | third << 8 | fourth;

		//return last 6 numbers
		return String.format("%06d", number % 1000000);
	}

	private static byte[] hmac(byte[] key, byte[] message) {
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA1");
			mac.init(secretKeySpec);
			return mac.doFinal(message);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			return null;
		}
	}

}
