package com.ryan.natrix;

import javax.microedition.khronos.opengles.GL10;

public class BulletList {
	
	private final static String TAG = BulletList.class.getSimpleName();
	
	private Bullet[] bullets;
	private int nextBullet = 0;
	
	public BulletList(int capacity) {
		bullets = new Bullet[capacity];
	}
	
	public Bullet spawnBullet(long ticks) {
		Bullet b = getNextAvailableBullet();
		
		if (b == null) {
			return null;
		}
		
		b.spawn(ticks);
		
		return b;
	}
	
	private Bullet getNextAvailableBullet() {
		Bullet b = getBullet(nextBullet);
		
		if (b.isDead()) {
			cycleNext();
			return b;
		}		
		
		int currentBullet = nextBullet;
		
		while (currentBullet != cycleNext() && b.isDead()) {
			b = getBullet(nextBullet);
		}
		
		if (b.isDead()) {
			return b;
		}
		
		return null;
	}
	
	private int cycleNext() {
		nextBullet++;
		if (nextBullet >= bullets.length) {
			nextBullet = 0;
		}
		return nextBullet;
	}
	
	private Bullet getBullet(int index) {
		if (index < 0 || index >= bullets.length) {
			return null;
		}
		
		if (bullets[index] == null) {
			bullets[index] = new Bullet();
		}
		
		return bullets[index];
	}
	
	public void update(long ticks) {
		for (int i=0; i < bullets.length; i++) {
			if (bullets[i] == null || bullets[i].isDead()) continue;
			bullets[i].update(ticks);
		}
	}
	
	public void draw(GL10 gl) {
		for (int i=0; i < bullets.length; i++) {
			if (bullets[i] == null || bullets[i].isDead()) continue;
			bullets[i].draw(gl);
		}
	}
}