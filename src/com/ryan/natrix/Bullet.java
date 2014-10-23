package com.ryan.natrix;

import javax.microedition.khronos.opengles.GL10;

import android.os.SystemClock;
import android.util.Log;

public class Bullet extends Entity {

	private final static String TAG = Bullet.class.getSimpleName();
	
	private boolean alive = false;
	private int lifetime = 100;
	private long createdAt;
	
	public Bullet() {
		float vertices[] = {
			-0.25f, -0.25f,  0.0f,		// V1 - bottom left
			-0.25f,  0.25f,  0.0f,		// V2 - top left
			 0.25f,  0.25f,  0.0f,		// V4 - top right
			 0.25f, -0.25f,  0.0f		// V3 - bottom right
		};
		
		sprite = new Square(vertices);
		sprite.setColor(1.0f, 0.4f, 0.4f, 1.0f);
	}
	
	public void spawn(long ticks) {
		alive = true;
		createdAt = ticks;
	}
	
	public void kill() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isDead() {
		return !alive;
	}
	
	public void update(long ticks) {
		if (isDead()) {
			return;
		}
		
		super.update(ticks);
		
		if (ticks > createdAt + lifetime) {
			kill();
		}
	}
	
	public void draw(GL10 gl) {
		if (isDead()) {
			return;
		}
		
		super.draw(gl);
	}

}
