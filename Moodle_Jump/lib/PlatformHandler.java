package lib;

import java.util.Queue;
import java.util.Random;

public class PlatformHandler {
	private Queue<Platform> platforms;
	private int max_number;
	private int last_x = 0;
	private int last_y = 0;
	private int platform_width = 32;
	private int platform_height = 32;
	private int initial_platform_separation = 128;
	private int generate_distance = 2056;
	private int display_width;
	private int difficulty;
	private Random generator;
	
	public PlatformHandler(long seed, int max_platform_number, int max_display_width, int difficulty) {
		int[] position;
		int i;
		int x = 0;
		int y = 0+this.platform_height;
		this.last_x = x;
		this.last_y = y;
		this.generator = new Random(seed);
		this.max_number = max_platform_number;
		this.display_width = max_display_width;
		this.difficulty = difficulty;
		
		// Generate initial platforms
		for (i=0; i<this.max_number; i++) {
			x = this.generator.nextInt(this.display_width-this.platform_width);
			if (i%2 == 0) {
				y = last_y+this.initial_platform_separation;
			} else {
				if (x < this.last_x+this.platform_width && x > this.last_x) {
					if (this.last_x-this.platform_width > 0) {
						x = this.last_x-this.platform_width;
					} else {
						x = this.last_x+this.platform_width;
					}
				}
				y = last_y;
			}
			position = new int[] {x, y};
			this.platforms.add(new Platform(position, 0, 0, this.platform_height, this.platform_width, this.display_width));
			
			this.last_x = x;
			this.last_y = y;
		}
	}
	public int[][] getPlatforms() {
		int[][] ret = new int[this.platforms.size()][4];
		Platform temp_array[] = (Platform[]) this.platforms.toArray();
		for (int i=0; i<temp_array.length; i++) {
			int[] pos = temp_array[i].getPosition();
			int type = temp_array[i].getType();
			int status = temp_array[i].getStatus();
			ret[i][0] = pos[0];
			ret[i][1] = pos[1];
			ret[i][2] = type;
			ret[i][3] = status;
		}
		return ret;
	}
	public void updatePlatforms(int player_y, int[] indices_of_affected_platforms) {
		Platform[] platforms = (Platform[]) this.platforms.toArray();
		this.platforms.clear();
		for (int i=0; i<platforms.length; i++) {
			boolean affected = false;
			for (int j=0; j<indices_of_affected_platforms.length; j++) {
				if (i == indices_of_affected_platforms[j]) {
					affected = true;
					break;
				}
			}
			platforms[i].update(affected);
			// Don't add destroyed platforms
			if (platforms[i].getType() != 0 && platforms[i].getStatus() != 0) {
				this.platforms.add(platforms[i]);
			}
		}
		
		if (this.last_y-player_y <= this.generate_distance) {
			int[] type_list;
			if (this.platforms.size() >= this.max_number) {
				this.platforms.remove();
			}
			// This switch basically checks which multiple of the difficulty modifier the player is at.
			//   If this.difficulty = 1000, then player_y/this.difficulty will be 0 if the player is
			//     below 1000, 1 if the player is below 2000, and so on.  The default just catches anything
			//     more difficult than we want to define individually, so it's the hardest difficulty level.
			switch(player_y/this.difficulty) {
				case 0: type_list = new int[]{0};
					break;
				default: type_list = new int[]{0, 1};
					break;
			}
			
			for (int i=this.platforms.size(); i<this.max_number; i++) {
				int x, y, type, status=0;
				int[] position;
				x = this.generator.nextInt(this.display_width-this.platform_width);
				if (this.generator.nextBoolean()) {
					y = last_y+this.initial_platform_separation;
				} else {
					if (x < this.last_x+this.platform_width && x > this.last_x) {
						if (this.last_x-this.platform_width > 0) {
							x = this.last_x-this.platform_width;
						} else {
							x = this.last_x+this.platform_width;
						}
					}
					y = last_y;
				}
				position = new int[] {x, y};
				type = type_list[this.generator.nextInt(type_list.length)];
				switch(type) {
					case 0: status = 0;
						break;
					case 1: status = 1;
						break;
				}
				this.platforms.add(new Platform(position, status, type, this.platform_height, this.platform_width, this.display_width));
				
				this.last_x = x;
				this.last_y = y;
			}
		}
	}
}
