package nl.tomsanders.processenprocessoren.emulator;

public class Program {
	public static void main(String[] args) {
		Processor processor = new Processor();
		processor.loadIntoRam(RomParser.parseFile("C:\\Users\\Tom\\Desktop\\roms\\gcd.rom"));

		int cycles = 0;
		while (!processor.isHalted()) {
			processor.cycle();
			cycles++;
		}
		System.out.println("Halted after " + cycles + " cycles.");
	}
}
