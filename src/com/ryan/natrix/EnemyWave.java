package com.ryan.natrix;

import android.util.Log;

public class EnemyWave {
	
	private final static String TAG = EnemyWave.class.getSimpleName();
	
	private long spawnTime = 0;
	private int lifetime = 300;
	
	private Enemy[] enemyList;
	
	public EnemyWave(long spawnTime, int numEnemies) {
		this.spawnTime = spawnTime;
		enemyList = new Enemy[numEnemies];
		
Log.d(TAG, "EnemyWave #EnemyWave -- number of enemies == "+ enemyList.length);
	}
	
	public String toString() {
		String output = "";
		for (int i=0; i < enemyList.length; i++) {
			output += " #"+i+" -- ";
			output += enemyList[i] == null ? "NULL" : enemyList[i].toString();
			output += "\n";
		}		
		return output;
	}
	
	public long getSpawnTime() {
		return spawnTime;
	}
	
	public void spawn(World world, long ticks) {
Log.d(TAG, "EnemyWave #spawn -- 1");
		for (int i=0; i < enemyList.length; i++) {
			enemyList[i].spawn(ticks, lifetime);
			
Log.d(TAG, "EnemyWave #spawn -- "+ enemyList[i].toString());

			world.addEnemy(enemyList[i]);
		}
	}
	
	public void addEnemy(Enemy e) {
Log.d(TAG, "EnemyWave #addEnemy == "+ e.toString());
		for (int i=0; i < enemyList.length; i++) {
			if (enemyList[i] != null) continue;
			
			enemyList[i] = e;
			return;
		}
	}
}