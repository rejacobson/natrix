package com.ryan.natrix;

import android.graphics.RectF;

public class Vector3f {
  
	private static int x = 0;
	private static int y = 1;
	private static int z = 2;
	
	//public float x;
	//public float y;
	
	public float[] coords = new float[3];
	
	// Constructors
    public Vector3f() {
    	coords[x] = coords[y] = coords[z] = 0F;
    }
    public Vector3f( Vector3f v ) {
    	coords[x] = v.coords[x];
    	coords[y] = v.coords[y];
    	coords[z] = v.coords[z];
    }
    public Vector3f( float _x, float _y, float _z ) {
    	coords[x] = _x;
    	coords[y] = _y;
    	coords[z] = _z;
    }
    
    public String toString() {
    	return "[" + coords[x] + ", " + coords[y] + ", " + coords[z] + "]";
    }

    // Magnitude
    public float getMagnitude() {
    	return (float) Math.sqrt(getSquaredMagnitude());
    }
    public float getSquaredMagnitude() {
    	return coords[x]*coords[x] + coords[y]*coords[y] + coords[z]*coords[z];
    }
    
    // Distance between two vectors
    public float getDistance( Vector3f p2 ) {
    	return (float) Math.sqrt(getSquaredDistance(p2));
    }
    public static float getDistance( Vector3f p1, Vector3f p2 ) {
    	return (float) Math.sqrt(getSquaredDistance(p1, p2));
    }
    public float getSquaredDistance( Vector3f p2 ) {
    	return Vector3f.subtract(p2, this).getSquaredMagnitude();
    }
    public static float getSquaredDistance( Vector3f p1, Vector3f p2 ) {
    	return Vector3f.subtract(p2,  p1).getSquaredMagnitude();
    }

    // Normals
    public Vector3f unit() {
    	float mag = getMagnitude();
    	this.scale(1/mag);
    	return this;
    }
    public Vector3f getUnit() {
    	Vector3f unit = new Vector3f(this);
    	float mag = unit.getMagnitude();
    	return unit.scale(1/mag);
    }
    /*
    public static Vector3f normal( Vector3f v1, Vector3f v2 ) {
    	float dx = v2.x - v1.x;
    	float dy = v2.y - v1.y;	  
    	return new Vector3f( -dy, dx ).unit();
    }

    // Dot
    public float dot( Vector3f other ) {
    	return (x * other.x + y * other.y);
    }
    
    // Cross
    public float cross( Vector3f other ) {
    	return (x * other.y - y * other.x);
    }*/

    // Reflect
    /*
    public static Vector3f reflect( Vector3f a, Vector3f b ) {
		float dotProduct = -a.x*b.x - a.y*b.y;
		return new Vector3f(a.x + 2 * b.x * dotProduct,
							a.y + 2 * b.y * dotProduct);
    }
    public Vector3f reflect( Vector3f other ) {
    	float dotProduct = -x*other.x - y*other.y;
		x = x + 2 * other.x * dotProduct;
		y = y + 2 * other.y * dotProduct;
		return this;
    }*/

    // Angle Radians
    /*
    public float getAngleRadians() {
    	float angle = (float) Math.atan2( y, x );

    	if ( angle < 0 ) {
    		angle += 2 * Math.PI;
    	}

    	return angle;
    }
    
    // Angle Degrees
    public float getAngleDegrees() {
    	return getAngleRadians() * 57.29578F;
    }
*/
    // Set
    public void copy( Vector3f rhs ) {
    	coords[x] = rhs.coords[x];
    	coords[y] = rhs.coords[y];
    	coords[z] = rhs.coords[z];
    }   
    public void set( float _x, float _y, float _z ) {
    	coords[x] = _x;
    	coords[y] = _y;
    	coords[z] = _z;
    }
    
    // Equals
    /*
    public boolean equals( Vector3f rhs ) {
    	return this.x == rhs.x && this.y == rhs.y;
    }
    
    // Not Equals
    public boolean notEquals( Vector3f rhs ) {
    	return ! this.equals(rhs);
    }*/

    // Basic Math
    public Vector3f add( Vector3f rhs ) {
    	coords[x] += rhs.coords[x];
    	coords[y] += rhs.coords[y];
    	coords[z] += rhs.coords[z];
    	return this;
    }
    public Vector3f add( float _x, float _y, float _z ) {
    	coords[x] += _x;
    	coords[y] += _y;
    	coords[z] += _z;
    	return this;
    }
    public Vector3f subtract( Vector3f rhs ) {
    	coords[x] -= rhs.coords[x];
    	coords[y] -= rhs.coords[y];
    	coords[z] -= rhs.coords[z];
    	return this;
    }
    public Vector3f subtract( float _x, float _y, float _z ) {
    	coords[x] -= _x;
    	coords[y] -= _y;
    	coords[z] -= _z;
    	return this;
    }
    public Vector3f multiply( Vector3f rhs ) {
    	coords[x] *= rhs.coords[x];
    	coords[y] *= rhs.coords[y];
    	coords[z] *= rhs.coords[z];
    	return this;
    }
    public Vector3f multiply( float _x, float _y, float _z ) {
    	coords[x] *= _x;
    	coords[y] *= _y;
    	coords[z] *= _z;
    	return this;
    }
    public Vector3f scale( float rhs ) {
    	coords[x] *= rhs;
    	coords[y] *= rhs;
    	coords[z] *= rhs;
    	return this;
    }
    
    // Static Math methods
    public static Vector3f add( Vector3f a, Vector3f b ) {
    	return new Vector3f(a).add(b);
    }
    public static Vector3f subtract( Vector3f a, Vector3f b ) {
    	return new Vector3f(a).subtract(b);
    }
    public static Vector3f multiply( Vector3f a, Vector3f b ) {
    	return new Vector3f(a).multiply(b);
    }
    public static Vector3f scale( Vector3f a, float val ) {
    	return new Vector3f(a).scale(val);
    }
   
    
    ////////////////////////////////////////////////
    // 0 == no intersection
    //
    //         2
    //      ________
    //     |        |
    //  1  |        |   3
    //     |        |
    //     |________|
    //       
    //          4
    /////////////////////////////////////////////////
    /*
    public static int rectLineIntersection(RectF rect, Vector3f p1, Vector3f p2) {
    	Vector3f result;
    	Vector3f p3 = new Vector3f();
    	Vector3f p4 = new Vector3f();
    	
    	// Left line
    	p3.set(rect.left, rect.bottom);
    	p4.set(rect.left, rect.top);
    	result = lineLineIntersection(p1, p2, p3, p4);
    	if (null != result) return 1;
    	
    	// Top line
    	p3.set(rect.left, rect.top);
    	p4.set(rect.right, rect.top);
    	result = lineLineIntersection(p1, p2, p3, p4);
    	if (null != result) return 2;
    	
    	// Right line
    	p3.set(rect.right, rect.top);
    	p4.set(rect.right, rect.bottom);
    	result = lineLineIntersection(p1, p2, p3, p4);
    	if (null != result) return 3;
    	
    	// Bottom line
    	p3.set(rect.left, rect.bottom);
    	p4.set(rect.right, rect.bottom);
    	result = lineLineIntersection(p1, p2, p3, p4);
    	if (null != result) return 4;
    	
    	return 0;
    }
    
    public static Vector3f lineLineIntersection(Vector3f p1, Vector3f p2,  
    		Vector3f p3, Vector3f p4) {  
	  float xD1,yD1,xD2,yD2,xD3,yD3;  
	  float dot,deg,len1,len2;  
	  float segmentLen1,segmentLen2;  
	  float ua,ub,div;  
	  
	  // calculate differences  
	  xD1=p2.x-p1.x;  
	  xD2=p4.x-p3.x;  
	  yD1=p2.y-p1.y;  
	  yD2=p4.y-p3.y;  
	  xD3=p1.x-p3.x;  
	  yD3=p1.y-p3.y;    
	  
	  // calculate the lengths of the two lines  
	  len1=(float) Math.sqrt(xD1*xD1+yD1*yD1);  
	  len2=(float) Math.sqrt(xD2*xD2+yD2*yD2);  
	  
	  // calculate angle between the two lines.  
	  dot=(xD1*xD2+yD1*yD2); // dot product  
	  deg=dot/(len1*len2);  
	  
	  // if abs(angle)==1 then the lines are parallell,  
	  // so no intersection is possible  
	  if(Math.abs(deg)==1) return null;  
	  
	  // find intersection Pt between two lines  
	  Vector3f pt = new Vector3f(0,0);  
	  div=yD2*xD1-xD2*yD1;  
	  ua=(xD2*yD3-yD2*xD3)/div;  
	  ub=(xD1*yD3-yD1*xD3)/div;  
	  pt.x=p1.x+ua*xD1;  
	  pt.y=p1.y+ua*yD1;  
	  
	  // calculate the combined length of the two segments  
	  // between Pt-p1 and Pt-p2  
	  xD1=pt.x-p1.x;  
	  xD2=pt.x-p2.x;  
	  yD1=pt.y-p1.y;  
	  yD2=pt.y-p2.y;  
	  segmentLen1=(float) (Math.sqrt(xD1*xD1+yD1*yD1)+Math.sqrt(xD2*xD2+yD2*yD2));  
	  
	  // calculate the combined length of the two segments  
	  // between Pt-p3 and Pt-p4  
	  xD1=pt.x-p3.x;  
	  xD2=pt.x-p4.x;  
	  yD1=pt.y-p3.y;  
	  yD2=pt.y-p4.y;  
	  segmentLen2=(float) (Math.sqrt(xD1*xD1+yD1*yD1)+Math.sqrt(xD2*xD2+yD2*yD2));  
	  
	  // if the lengths of both sets of segments are the same as  
	  // the lenghts of the two lines the point is actually  
	  // on the line segment.  
	  
	  // if the point isn't on the line, return null  
	  if(Math.abs(len1-segmentLen1)>0.01 || Math.abs(len2-segmentLen2)>0.01)  
	    return null;  
	  
	  // return the valid intersection  
	  return pt;  
	}
	*/
}