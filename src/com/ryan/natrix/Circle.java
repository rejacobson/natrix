package com.ryan.natrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


public class Circle extends Sprite {

	private static final String TAG = Circle.class.getSimpleName();
	
	// buffer holding the vertices
	private FloatBuffer vertexBuffer;	

	private float[] vertices;
	
	public Circle(float radius) {
		super();
		
		setColor(1.0f, 0.4f, 0.8f, 1.0f);
		
		int numSegments = 40;
		
		vertices = new float[numSegments*3];
		
		float angle;
		float centerX = 0;
		float centerY = 0;
		int index = 0;
		for(int i = 0; i < numSegments; i++) {
	        angle = (float) (i*2*Math.PI/numSegments);
	        
	        float x = (float) (centerX + (Math.cos(angle) * radius));
	        float y = (float) (centerY + (Math.sin(angle) * radius));
	        float z = 0;
	        	        
	        vertices[index] = x;
	        vertices[index+1] = y;
	        vertices[index+2] = z;
	        index += 3;
	    }
		
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());

		// allocates the memory from the byte buffer
		vertexBuffer = vertexByteBuffer.asFloatBuffer();
		
		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
				
		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);

		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		//Log.d(TAG, "Drawing 3");
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// set the colour for the triangle
		gl.glColor4f(color[0], color[1], color[2], color[3]);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		// Draw the vertices as line loop
		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
