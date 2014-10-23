package com.ryan.natrix;

import java.nio.FloatBuffer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class NatrixView extends GLSurfaceView {

	private static final String TAG = NatrixView.class.getSimpleName();
	    
    private GameLoop gameLoop;
    private World world;
    
    
	public NatrixView(Context context) {
		super(context);        
		gameLoop = new GameLoop(getHolder(), this);
		world = new World(this, context.getAssets());
	}
	
	public void start() {
		gameLoop.SetRunning(true);
        gameLoop.start();
	}

	public void update(long tickCount) {
		//Log.d(TAG, "Updating");
    	world.update(tickCount);
	}
	
	 public void prepareRender(FloatBuffer mVertexBuffer) {
		world.prepareRender(mVertexBuffer, 0);
		/*
		gl.glTranslatef(0.0f, -6.0f, -15.0f);
		
		world.draw(gl);
		*/
	}
	
	@Override
	public void onPause() {
		gameLoop.Pause();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		gameLoop.Resume();
		super.onResume();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
    	Log.d(TAG, "GameView -- surfaceDestroyed");
		boolean retry = true;
		while (retry) {
			try {
				gameLoop.SetRunning(false);
				gameLoop.join();
				gameLoop = null;
				retry = false;
				//((Activity)getContext()).finish();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
    @Override
    public boolean onTouchEvent(final MotionEvent event) {   
    	Log.d(TAG, "Touched: "+ new Vector2f(event.getX(), event.getY()).toString());
    	
    	// DOWN
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			world.actionDown(event.getX(), event.getY());
		}
		
		// MOVE
		if (event.getAction() == MotionEvent.ACTION_MOVE) {			
			world.actionMove(event.getX(), event.getY());
		}
		
		// UP
		if (event.getAction() == MotionEvent.ACTION_UP) {
			world.actionUp(event.getX(), event.getY());
		}
		
		return true;		
    }

}
