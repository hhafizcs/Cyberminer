package KWIC;

public class KWIC {
	private LineStorage lineStorage;
	private CircularShifter shifter;
	private Alphabetizer alphabetizer;
	
	public KWIC(Line inputLine) {
		lineStorage = new LineStorage(inputLine);
		shifter = new CircularShifter();
		alphabetizer = new Alphabetizer();
	}
	
	public void setup( ) {
		shifter.setup(lineStorage); 
		alphabetizer.setup(shifter);
	}
	
	public LineStorage getLineStorage() {
		return lineStorage;
	}

	public CircularShifter getShifter() {
		return shifter;
	}

	public Alphabetizer getAlphabetizer() {
		return alphabetizer;
	}
}