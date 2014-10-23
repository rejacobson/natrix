package com.ryan.natrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;


public class Triangle extends Sprite {

	private static final String TAG = Triangle.class.getSimpleName();
	
	// buffer holding the vertices
	private FloatBuffer vertexBuffer;	

	public Triangle() {
		super( new float[] {
			-0.5f, -0.25f, 0,
	        0.5f, -0.25f, 0,
	        0.0f,  0.559016994f, 0
		} );		
	}
	
	public void draw(GL10 gl) {
		//Log.d(TAG, "Drawing 3");
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// set the colour for the triangle
		gl.glColor4f(color[0], color[1], color[2], color[3]);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
