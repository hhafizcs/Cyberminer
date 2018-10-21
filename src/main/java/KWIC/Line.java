package KWIC;
public class Line {
	private String descriptor;
	private String url;
	
	public Line(String descriptor, String url) {
		this.descriptor = descriptor;
		this.url = url;
	}
	
	public String getDescriptor( ) {
		return descriptor;
	}
	
	public String getUrl( ) {
		return url;
	}
}