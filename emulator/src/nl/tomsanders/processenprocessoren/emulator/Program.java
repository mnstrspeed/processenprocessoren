package nl.tomsanders.processenprocessoren.emulator;

public class Program {
	public Program() {
		IoBuffer buffer = new IoBuffer();
		Processor processor = new Processor(buffer);
		new Terminal(buffer);

		processor.loadIntoRam(RomParser.parseFile("test.rom"));
		int cycles = 0;
		while (!processor.isHalted()) {
			processor.cycle();
			cycles++;
		}
		System.out.println("Halted after " + cycles + " cycles.");
	}

	public static void main(String[] args) {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		new Program();
	}
}
