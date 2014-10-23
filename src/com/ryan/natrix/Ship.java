package com.ryan.natrix;

public class Ship extends Entity {
	
	protected BulletList bulletList;
	protected boolean shooting = false;
	
	public Ship() {
		
	}
	
	public void setBulletList(BulletList bulletList) {
		this.bulletList = bulletList;
	}
	
	public void startShooting() {
		shooting = true;
	}
	
	public void stopShooting() {
		shooting = false;
	}
	
	public void shoot(long time) {
		
	}
}