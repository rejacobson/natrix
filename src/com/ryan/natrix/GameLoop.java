package com.ryan.natrix;

import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
	
	private static final String TAG = GameLoop.class.getSimpleName();
	
	
	private long mLastTime;
    
    private Object mPauseLock;
    private boolean mFinished;
    private boolean mPaused = false;
    private int mProfileFrames;
    private long mProfileTime;
    
    private long tickCount = 0;
    
    private static final float PROFILE_REPORT_DELAY = 3.0f;

	
	
	
	
	// desired fps
	private final static int MAX_FPS = 50;
	
	// maximum number of frames to be skipped
	private final static int MAX_FRAME_SKIPS = 5;
	
	// the frame period
	private final static int FRAME_PERIOD = 1000 / MAX_FPS;
	
	
	
	private boolean running;
	private boolean paused;
	private SurfaceHolder surfaceHolder;
	private NatrixView gameView;
	
	public GameLoop(SurfaceHolder surfaceHolder, NatrixView gameView) {
		super();
		
		Log.d(TAG, "Setting up GameLoop");
		
		this.surfaceHolder = surfaceHolder;
		this.gameView = gameView;
		this.paused = false;
		
		
		mLastTime = SystemClock.uptimeMillis();
        mPauseLock = new Object();
        mFinished = false;
        mPaused = false;
	}
	
	public void SetRunning(boolean running) {
		this.running = running;
	}
	
	public void Pause() {
		this.paused = true;
	}
	public void Resume() {
		this.paused = false;
	}
	
	@Override
	public void run() {
		mLastTime = SystemClock.uptimeMillis();
        mFinished = false;
        
        Log.d(TAG, "entering GameLoop");
        
        while (running) {
        	if (paused) continue;
        	
        	final long time = SystemClock.uptimeMillis();
	        final long timeDelta = time - mLastTime;
	        long finalDelta = timeDelta;
	        
	        if (timeDelta > 12) {
	            float secondsDelta = (time - mLastTime) * 0.001f;
	            if (secondsDelta > 0.1f) {
	                secondsDelta = 0.1f;
	            }
	            mLastTime = time;
	
	            //Log.d(TAG, "Updating gameView");
	            
	            this.gameView.update(tickCount);
	            tickCount++;
	            
	            /*
	            CameraSystem camera = mGameRoot.sSystemRegistry.cameraSystem;
	            float x = 0.0f;
	            float y = 0.0f;
	            if (camera != null) {
	                x = camera.getFocusPositionX();
	                y = camera.getFocusPositionY();
	            }
	            BaseObject.sSystemRegistry.renderSystem.swap(mRenderer, x, y);
	            */
	            
	            final long endTime = SystemClock.uptimeMillis();
	            
	            finalDelta = endTime - time;
	            
	            mProfileTime += finalDelta;
	            mProfileFrames++;
	            
	            if (mProfileTime > PROFILE_REPORT_DELAY * 1000) {
	                final long averageFrameTime = mProfileTime / mProfileFrames;
	                Log.d(TAG, "Game Profile - Average: " + averageFrameTime);
	                mProfileTime = 0;
	                mProfileFrames = 0;
	                //mGameRoot.sSystemRegistry.hudSystem.setFPS(1000 / (int)averageFrameTime);
	            }
	        }
	        // If the game logic completed in less than 16ms, that means it's running
	        // faster than 60fps, which is our target frame rate.  In that case we should
	        // yield to the rendering thread, at least for the remaining frame.
	       
	        if (finalDelta < 16) {
	            try {
	            	//Log.d(TAG, "Sleeping");
	                Thread.sleep(16 - finalDelta);
	            } catch (InterruptedException e) {
	                // Interruptions here are no big deal.
	            }
	        }
        }

	}
}