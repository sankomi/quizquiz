package sanko.quiz.user;

import java.util.*; //Set, HashSet
import java.nio.ByteBuffer;
import java.security.*; //SecureRandom, NoSuchAlgorithmException, InvalidKeyException
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;

@RequiredArgsConstructor
@Service
public class PasswordServ {

	private final Base32 base32 = new Base32();
	private final SecureRandom random = new SecureRandom();

	private final Set<String> usedPasswords = new HashSet<>();

	public String createKey() {
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return new String(base32.encode(bytes));
	}

	public boolean verify(String username, String key, String password) {
		//fail if password has been used
		if (usedPasswords.contains(username + password)) {
			return false;
		}

		long time = System.currentTimeMillis() / 1000L;
		long count = time / 30L;
		long countPrev = count - 1;

		String check = fromCount(key, count);
		if (check.equals(password)) {
			usedPasswords.add(username + password);
			return true;
		}

		check = fromCount(key, countPrev);
		if (check.equals(password)) {
			usedPasswords.add(username + password);
			return true;
		}

		return false;
	}

	public String fromTime(String key) {
		return fromTime(key, System.currentTimeMillis() / 1000L);
	}

	public String fromTime(String key, long time) {
		//get count from time
		long count = time / 30L;

		return fromCount(key, count);
	}

	public String fromCount(String key, long count) {
		//convert count to bytes
		byte[] c = ByteBuffer.allocate(Long.BYTES)
			.putLong(count)
			.array();

		//convert key to bytes
		byte[] k = base32.decode(key);

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

	private byte[] hmac(byte[] key, byte[] message) {
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
