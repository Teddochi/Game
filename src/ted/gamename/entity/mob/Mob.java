package ted.gamename.entity.mob;

import ted.gamename.entity.Entity;
import ted.gamename.graphics.Sprite;

public class Mob extends Entity {

	protected Sprite sprite;
	
	//1 = North, 2 = East, 3 = South, 4 = West
	protected int dir = 0;
	
	protected boolean moving = false;
	
	public void move(int xa, int ya) {
		if(xa > 0) {
			dir = 1;
		}
		
		if(xa < 0){
			dir = 3;
		}
		
		if(ya > 0){
			dir = 2;
		}
		
		if(ya < 0){
			dir = 0;
		}
		
		if(!collision()){
			x += xa;
			y += ya;
		}
		
	}
	
	public void update(){
		
	}
	
	private boolean collision(){
		return false;
	}
	
	
}
