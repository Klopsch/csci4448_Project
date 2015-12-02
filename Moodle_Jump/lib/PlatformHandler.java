package lib;

public class PlatformHandler {
	private Platform[] platforms;
	
	public int[] getPlatforms() {
		int[] ret = new int[this.platforms.length*4];
		for (int i=0; i<this.platforms.length; i++) {
			int[] pos = platforms[i].getPosition();
			int type = platforms[i].getType();
			int status = platforms[i].getStatus();
			ret[4*i+0] = pos[0];
			ret[4*i+1] = pos[1];
			ret[4*i+2] = type;
			ret[4*i+3] = status;
		}
		return ret;
	}
	public void updatePlatforms(int[] player_position) {
		
	}
}
