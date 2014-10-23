package com.ryan.natrix;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.opengl.Matrix;
import android.util.Log;

public class Entity {
	
	private static final String TAG = Entity.class.getSimpleName();
	private static int idCount = 0;
	private int id = 0;
	
	protected float matrix[] = new float[16];
	protected Vector3f velocity = new Vector3f();
	
	protected Sprite sprite = null;
	
	protected Circle boundingCircle = null;
	
	//private Triangle triangle = new Triangle();
	
	private int width = 20;
	private int height = 20;
	
	public Entity() {
		id = idCount++;
		Matrix.setIdentityM(matrix, 0);
		velocity = new Vector3f();
		sprite = null;
		
Log.d(TAG, "Entity #Entity -- created entity #"+getId());
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "#" + getId() + " -- p = [" + getX() + ", " + getY() + ", " + getZ() + "] -- v = "+ velocity.toString();
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void getPositionAndColor(float[] resultVec, int resultVecOffset) {
		if (sprite == null) return;		
		sprite.getPositionAndColor(resultVec, resultVecOffset, matrix, 0);
	}
	
	/*public Entity(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	//////////////////////////////////////////////////
	// Bitmap
	//////////////////////////////////////////////////
	public Bitmap getBitmap() {
		return bitmap;
	}	
	public Entity setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		return this;
	}*/

	//////////////////////////////////////////////////
	// Velocity
	//////////////////////////////////////////////////
	public Vector3f getVelocity() {
		return velocity;
	}
	public Entity setVelocity(Vector3f v) {
		velocity.copy(v);
		return this;
	}
	public Entity setVelocity(float x, float y, float z) {
		velocity.set(x, y, z);
		return this;
	}
	
	public Entity updateVelocity(Vector3f v) {
		velocity.add(v);
		return this;
	}
	public Entity updateVelocity(float x, float y, float z) {
		velocity.add(x, y, z);
		return this;
	}
	
	//////////////////////////////////////////////////
	// Position
	//////////////////////////////////////////////////
	/*public Vector3f getPosition() {
		return new Vector3f(matrix[0], matrix[1], matrix[2]);
		//float[] position = {matrix[0], matrix[1], matrix[2]};
		//return position;
	}*/
	public float[] getPosition() {
		float[] position = {matrix[12], matrix[13], matrix[14]};
		return position;
	}
	public float getX() {
		return matrix[12];
	}
	public float getY() {
		return matrix[13];
	}
	public float getZ() {
		return matrix[14];
	}
	
	public Entity setPosition(float[] p) {
		matrix[12] = matrix[13] = matrix[14] = 0;
		Matrix.translateM(matrix, 0, p[0], p[1], p[2]);
		return this;
	}
	public Entity setPosition(float x, float y, float z) {
		matrix[12] = matrix[13] = matrix[14] = 0;
		Matrix.translateM(matrix, 0, x, y, z);
		return this;
	}
	
	public Entity updatePosition(float[] p) {
		Matrix.translateM(matrix, 0, p[0], p[1], p[2]);
		return this;
	}
	public Entity updatePosition(float x, float y, float z) {
		Matrix.translateM(matrix, 0, x, y, z);
		return this;
	}
	
	/*public RectF getRect() {
		return new RectF(
				position.x - width/2,   // left
				position.y - height/2,  // top
				position.x + width/2,   // right
				position.y + height/2); // bottom
	}
	public RectF getDoubleRect() {
		return new RectF(
				position.x - width,   // left
				position.y - height,  // top
				position.x + width,   // right
				position.y + height); // bottom
	}*/
		
	/*public boolean isMoving() {
		return velocity.getSquaredMagnitude() > 0.0001F;
	}
	public boolean isStopped() {
		return !isMoving();
	}*/
	
	/*public boolean collidesWith(Entity entity) {
		// Check if the entities are moving away from each other
		Vector2f positionDiff = Vector2f.subtract(entity.position, this.position);
		Vector2f velocityDiff = Vector2f.subtract(entity.velocity, this.velocity);
		
		if (velocityDiff.dot(positionDiff) >= 0) return false;
		
		// test rect intersection
		RectF a = getRect();
		RectF b = entity.getRect();
		return RectF.intersects(a, b);
	}
	
	public static int getCollisionEdge(Entity a, Entity b, Vector2f movingDirection) {
		RectF b_rect = b.getDoubleRect();
		Vector2f pnt1 = a.position;
		Vector2f pnt2 = Vector2f.subtract(a.position, movingDirection);
		return Vector2f.rectLineIntersection(b_rect, pnt1, pnt2);
	}*/
	
	public void update(long tickCount) {				
		Matrix.translateM(matrix,  0, velocity.coords[0], velocity.coords[1], velocity.coords[2]);
	}
	
	public void draw(GL10 gl) {
		//Log.d(TAG, "Drawing 2");
		gl.glPushMatrix();
		gl.glTranslatef(matrix[12], matrix[13], matrix[14]);
		
		if (sprite != null) {
			sprite.draw(gl);
		}
		
		if (boundingCircle != null) {
			boundingCircle.draw(gl);
		}
		//triangle.draw(gl);
		
		gl.glPopMatrix();
	}
}