package com.ryan.natrix;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Level {

	private final static String TAG = Level.class.getSimpleName();
	
	public static int LEFT = 0;
	public static int TOP = 1;
	public static int RIGHT = 2;
	public static int BOTTOM = 3;
	
	private World world;
	
	private int levelNumber;
	private float[] levelBounds = new float[4];
	
	private int currentWave;
	
	private EnemyWave[] waves = null;
	
	public Level(World world, String levelFile) {
		this.world = world;
		
		this.levelNumber = levelNumber;
		
		levelBounds[LEFT] = -50F;
		levelBounds[RIGHT] = 50F;
		
		levelBounds[TOP] = 150F;
		levelBounds[BOTTOM] = -150F;
		
		waves = new EnemyWave[20];
		
		loadLevel(levelFile);
	}
	
	public void loadLevel(String levelFile) {
		Log.d(TAG, "Loading level: "+ levelFile);
		
		InputStream stream = null;
		String levelJson;
		JSONObject levelData = null;
		JSONArray waveList = null;
		
		try {
			stream = world.getAssets().open(levelFile);
			levelJson = Level.readLevelData(stream);
			waveList = new JSONArray(levelJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Allocate space for all waves
		waves = new EnemyWave[waveList.length()];
		
		
		// Loop through all waves
		int spawnTime = 0;
		JSONObject wave = null;
		JSONArray ships = null;
		JSONArray position = null;
		JSONArray velocity = null;
		JSONObject shipData = null;
		String shipName = "";
		Sprite enemySprite = null;
		Enemy enemyShip = null;
		
		for (int i=0; i < waveList.length(); i++) {
			Log.d(TAG, "Processing wave #"+i);
			
			// wave data
			try {
				wave = waveList.getJSONObject(i);
				spawnTime = wave.getInt("spawnTime");
				ships = wave.getJSONArray("ships");
				//Log.d(TAG, wave.toString(1));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			waves[i] = new EnemyWave(spawnTime, ships.length());
			
			//GliderSprite s;
			
			for (int j=0; j < ships.length(); j++) {
				try {
					shipData = ships.getJSONObject(j);
					shipName = "com.ryan.natrix.sprites."+shipData.getString("name")+"Sprite";
					position = shipData.getJSONArray("position");
					velocity = shipData.getJSONArray("velocity");					
					
					Log.d(TAG, "Creating enemy ship sprite with reflection: "+shipName);
					enemySprite = (Sprite) Class.forName(shipName).newInstance();
					
					if (enemySprite != null) {
						Log.d(TAG, "Adding new enemy to the wave list");
						enemyShip = new Enemy();
						enemyShip.setSprite(enemySprite);
						enemyShip.setPosition((float)position.getDouble(0), (float)position.getDouble(1), (float)position.getDouble(2));
						enemyShip.setVelocity((float)velocity.getDouble(0), (float)velocity.getDouble(1), (float)velocity.getDouble(2));
						
						Log.d(TAG, "Adding ship == "+enemyShip.toString());
						
						waves[i].addEnemy(enemyShip);
					} else {
						Log.d(TAG, "COULD NOT GET CLASS NAME");
					}
					
					//Log.d(TAG, "shipData == "+shipData.toString(1));
					//Log.d(TAG, "shipName == "+shipName);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		}
		
		//Log.d(TAG, levelJson);
		
		//waves[0] = new EnemyWave(100, 2);
		//waves[0].addEnemy( (Enemy) new Enemy().setPosition(5, 20, 0).setVelocity(0, -0.1f, 0) );
		//waves[0].addEnemy( (Enemy) new Enemy().setPosition(3, 24, 0).setVelocity(0, -0.1f, 0) );
		Log.d(TAG, toString());
	}
	
	public String toString() {
		String output = "";
		for (int i=0; i < waves.length; i++) {
			output += "WAVE #"+i+" -- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n";
			output += waves[i] == null ? "NULL" : waves[i].toString();
			output += "\n";
		}
		return output;
	}
	
	public static String readLevelData(InputStream file) {
		try {
            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = file.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            return text;
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }

	}
	
	public void update(long ticks) {
		if (currentWave >= waves.length) return;
			
		if (waves[currentWave] == null)
		{
			currentWave++;
			return;
		}
		
		EnemyWave wave = waves[currentWave];
		
		if (ticks >= wave.getSpawnTime())
		{
Log.d(TAG, "Level#update -- spawning enemy wave");

			wave.spawn(world, ticks);
			
			currentWave++;
		}
	}
	
}
