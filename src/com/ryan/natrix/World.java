package com.ryan.natrix;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.AssetManager;
import android.util.Log;

public class World {
	
	private final static String TAG = World.class.getSimpleName();
	
	private Level currentLevel;
	
	private PlayerShip playerShip;
	private BulletList playerBullets;
	
	private Enemy[] enemies;	
	private BulletList enemyBullets;
	
	private NatrixView gameView;
	private AssetManager assets;
	private float[] screenCenter = new float[2];
	
	
	public World(NatrixView gameView, AssetManager assets) {
		this.gameView = gameView;
		this.assets = assets;
		
		screenCenter[0] = gameView.getWidth()/2;
		screenCenter[1] = gameView.getHeight()/2;
		
		playerBullets = new BulletList(20);
		enemyBullets = new BulletList(20);
		
		playerShip = new PlayerShip(playerBullets);
		playerShip.setPosition(0.0f, 0.0f, 0.0f);
		
		enemies = new Enemy[20];
		
		currentLevel = new Level(this, "Level1.json");
		
		Log.d(TAG, "World -- Screen Center == "+screenCenter[0]+":"+screenCenter[1]);
	}
	
	public AssetManager getAssets() {
		return assets;
	}
	
	public void addEnemy(Enemy e) {
		// Find next available spot, or don't add it
		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i] != null) continue;
			enemies[i] = e;
			Log.d(TAG, "World#addEnemy -- at position == "+i);
			Log.d(TAG, e.toString());
			return;
		}
	}
	
	public void removeEnemy(int index) {
		enemies[index] = null;
	}
	
    public BulletList getEnemyBullets() {
		return enemyBullets;
	}
	
    
    //////////////////////////////////
    // Update and Draw
    //////////////////////////////////
	public void update(long ticks) {
    	currentLevel.update(ticks);
    	playerShip.update(ticks);
    	playerBullets.update(ticks);
    	
    	enemyBullets.update(ticks);
    	
    	for (int i=0; i < enemies.length; i++) {
    		if (enemies[i] == null) continue;
    		
    		if (enemies[i].isDead()) {
    			removeEnemy(i);
    			continue;
    		}
//Log.d(TAG, "World#update() -- updating enemy #"+i);
    		enemies[i].update(ticks);
    	}
	}
	
	private void collisionDetection() {
		float[] enemyPos;
		
		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i] != null || enemies[i].isDead()) continue;
			
			enemyPos = enemies[i].getPosition();
			
			// Test collision with playerShip
			//if (enemyPos[1] < 2) {
				
			//}
		}
	}
    
	public void draw(GL10 gl) {
    	//gl.glTranslatef(0.0f, 0.0f, -20.0f);		// move 5 units INTO the screen
		
		playerShip.draw(gl);
    	playerBullets.draw(gl);
    	
    	enemyBullets.draw(gl);
    	
    	for (int i=0; i < enemies.length; i++) {
    		if (enemies[i] == null) continue;
    		enemies[i].draw(gl);
    	}
    }
	
	
	//////////////////////////////////
	// Window Events
	//////////////////////////////////
	public void viewChanged() {
		screenCenter[0] = gameView.getWidth()/2;
		screenCenter[1] = gameView.getHeight()/2;
    }
	
	public void actionDown(float x, float y) {
		
	}
	
	public void actionMove(float x, float y) {
		float maxVelocity = 0.1F;
		float amount = x - screenCenter[0];
		float maxRange = 15;
		
		if ( amount > 200 ) amount = 200;
		if ( amount < -200 ) amount = -200;
		
		float[] p = playerShip.getPosition();
		
		float posX = amount / maxRange;
		float distance = posX - p[0];
		float dir = distance / Math.abs(distance);
		
		playerShip.updateVelocity(0.05F*dir, 0.0f, 0.0f);
		
		//Log.d(TAG, "#actionMove -- playership: "+playerShip.toString());
		
		// Create player bullets
		playerShip.startShooting();
	}
	
	public void actionUp(float x, float y) {
		playerShip.stopShooting();
	}

	public void prepareRender(FloatBuffer mVertexBuffer, int vboPosition) {
		float[] vertices = new float[7];
		
		playerShip.getPositionAndColor(vertices, 0);
		
		//Log.d(TAG, "World#prepareRender -- vertices: "+vertices[0]+", "+vertices[1]+", "+vertices[2]+", "+vertices[3]);
		
		mVertexBuffer.put(vertices).position(vboPosition);
		
		// TODO Auto-generated method stub
		
	}
}