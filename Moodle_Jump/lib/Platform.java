package lib;

public class Platform {
	private int[] position;
	private double[] velocity;
	private int status;
	private int type;
	private int height;
	private int width;
	
	public int[] getPosition() {
		return this.position;
	}
	public int getStatus() {
		return this.status;
	}
	private void setStatus(int new_status) {
		this.status = new_status;
	}
	public int getType() {
		return this.type;
	}
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}
	public void update() {
		
	}
}
