package nl.tomsanders.processenprocessoren.emulator;
import java.nio.ByteBuffer;


public class ByteHelper {
	public static int[] toIntArray(byte[] bytes) {
		if (bytes.length % 4 == 0) {
			int[] result = new int[bytes.length / 4];
		
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			for (int i = 0; i < result.length; i++) 
				result[i] = buffer.getInt();
			
			return result;
		} else {
			throw new RuntimeException("Invalid format");
		}
	}
	
	public static boolean getBitBoolean(int source, int position) {
		return getBits(source, position, position) == 1 ? true : false;
	}
	
	public static int getBit(int source, int position) {
		return getBits(source, position, position);
	}
	
	public static int getBits(int source, int start, int end) {
		if (start > end)
			throw new RuntimeException("start > end, can't do that.");
		
		int bitmask = 0;
		for (int i = start; i <= end; i++) {
			int mask = 1;
			mask <<= i;
			bitmask |= mask;
		}
		int result = source & bitmask;
		return result >>> start;
	}
	
	public static int getSignedBits(int source, int start, int end) {
		int bitmask = 0;
		for (int i = start; i <= end; i++) {
			int mask = 1;
			mask <<= i;
			bitmask |= mask;
		}
		int result = source & bitmask;
		
		int signMask = 1;
		signMask <<= end;
		if ((signMask & result) != 0) {
			int replicateMask = 0;
			for (int i = end + 1; i < 32; i++) {
				int mask = 1;
				mask <<= i;
				replicateMask |= mask;
			}
			result |= replicateMask;
		}
		return result >> start;
	}
	
	public static int parseHex(String str) {
		char[] hex = str.toCharArray();
		
		int total = 0;
		for (int i = hex.length - 1; i >= 0; i--) {
			total = total + (int)Math.pow(16, hex.length - i - 1) 
					* Character.digit(hex[i], 16);
		}
		return total;
	}
}
