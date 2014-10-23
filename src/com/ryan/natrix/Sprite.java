package com.ryan.natrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.Matrix;
import android.util.Log;

public class Sprite {
	
	private final static String TAG = Sprite.class.getSimpleName();
	
	// buffer holding the vertices
	protected FloatBuffer vertexBuffer;
	
	protected float[] vertices = null;
		
	protected float color[] = { 0.0f, 1.0f, 0.0f, 1.0f };
	
	public Sprite() {
		
	}
	
	public Sprite(float[] v) {
		this.vertices = v;
		initVertexBuffer();
	}
	
	public String toString() {
		String str = "Sprite vertices == ";
		
		if (vertices == null) return str+" null";
		
		str += "\n";
		
		for (int i=0; i < vertices.length; i++) {
			str += vertices[i]+", ";
			if ((i+1) % 3 == 0) {
				str += "\n";
			}
		}
		
		Log.d(TAG, "Sprite#toString -- "+ str);
		
		return str;
	}
	
	private void initVertexBuffer() {
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
	
	public Sprite setVertices(float[] v) {
		this.vertices = v;
		
		return this;
	}
	
	public void getPositionAndColor(float[] resultVec, int resultVecOffset, float[] matrix, int matrixOffset) {
		Matrix.multiplyMV(resultVec, resultVecOffset, matrix, matrixOffset, this.vertices, 0);
		resultVec[resultVecOffset+3] = color[0];
		resultVec[resultVecOffset+4] = color[1];
		resultVec[resultVecOffset+5] = color[2];
		resultVec[resultVecOffset+6] = color[3];
	}
	
	public Sprite setColor(float[] c) {
		System.arraycopy(c, 0, this.color, 0, 4);
		return this;
	}
	
	public Sprite setColor(float r, float g, float b, float a) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
		return this;
	}
	
	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}
	
	public void draw(GL10 gl) {
		
	}
	
}