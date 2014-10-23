package com.ryan.natrix;


import android.util.Log;

public class PlayerShip extends Ship {
	
	private static final String TAG = PlayerShip.class.getSimpleName();
	
	private float maxVelocity = 1.0F;
	
	public PlayerShip(BulletList bulletList) {
		super();
		this.setBulletList(bulletList);
		
		boundingCircle = new Circle(0.5f);
		setSprite(new Triangle());
		//sprite = new GliderSprite();
		//sprite.setColor(0.2f, 0.5f, 1.0f, 1.0f);
	}
	
	public void update(long ticks) {
		super.update(ticks);
		
		// Restrict speed
		float speed = velocity.getMagnitude();
		if (speed > maxVelocity) {
			velocity.unit().scale(maxVelocity);
		}
		
		velocity.scale(0.9f);
	
		if (shooting && ticks % 10 == 0) {
			Bullet b = bulletList.spawnBullet(ticks);
			
			if (b != null) {
				float[] p = getPosition();
				b.setVelocity(0.0f, 0.2f, 0.0f);
				b.setPosition(p[0], p[1], p[2]);
			}
		}
	}
}