package cprail.ptc.service.beans;

public class View {
	
	private String fill = "#D7DBDD";
	private double size = 80.0;
	private String stroke = "black";
	private String strokeWidth = "2";
	
	
	public View() {}


	public String getFill() {
		return fill;
	}


	public void setFill(String fill) {
		this.fill = fill;
	}


	public double getSize() {
		return size;
	}


	public void setSize(double size) {
		this.size = size;
	}


	public String getStroke() {
		return stroke;
	}


	public void setStroke(String stroke) {
		this.stroke = stroke;
	}


	public String getStrokeWidth() {
		return strokeWidth;
	}


	public void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	
	

}