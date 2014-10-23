package com.ryan.natrix;

import com.ryan.natrix.sprites.GliderSprite;


public class Enemy extends Ship {
	
	private static final String TAG = Enemy.class.getSimpleName();
	
	private boolean alive = false;
	private long createdAt;
	private int lifetime;
	
	public Enemy() {
		super();
		
		// TODO Auto-generated constructor stub
		boundingCircle = new Circle(0.5f);
		setSprite(new GliderSprite());
	}
	
	public void spawn(long ticks, int lifetime) {
		this.alive = true;
		this.lifetime = lifetime;
		this.createdAt = ticks;
	}
	
	public void kill() {
		this.alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isDead() {
		return !alive;
	}
	
	public void update(long ticks) {
		if (!this.alive) return;
		
		super.update(ticks);
		
		if (ticks > createdAt+lifetime) {
			this.kill();
		}
	}

}
