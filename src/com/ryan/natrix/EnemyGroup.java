package com.ryan.natrix;

import javax.microedition.khronos.opengles.GL10;

public class EnemyGroup extends Entity {

	private Enemy[] ships;
	
	public EnemyGroup() {
		super();
		
		ships = new Enemy[10];
	}

	
	public void update() {
		
	}
	
	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(matrix[13], matrix[14], matrix[15]);
		
		Enemy enemy;
		
		for (int i=0; i<ships.length; i++) {
			enemy = ships[i];
			
			if (enemy == null) continue;
			
			enemy.draw(gl);
		}
		
		gl.glPopMatrix();
	}
}