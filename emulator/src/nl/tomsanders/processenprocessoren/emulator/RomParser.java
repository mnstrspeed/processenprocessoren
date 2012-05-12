package nl.tomsanders.processenprocessoren.emulator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RomParser {
	public static int[] parseFile(String path) {
		ArrayList<Integer> instructions = new ArrayList<Integer>();
		try {
			FileInputStream stream = new FileInputStream(path);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream));
			
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				String[] segment = currentLine.split(":");
				
				if (segment.length > 1)
					instructions.add(ByteHelper.parseHex(segment[1]));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[] result = new int[instructions.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = instructions.get(i);
		return result;
	}
}
