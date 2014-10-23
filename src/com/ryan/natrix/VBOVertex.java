package com.ryan.natrix;

public class VBOVertex {
	
	private final static String TAG = VBOVertex.class.getSimpleName();
	
	private float[] vertex = {
	//   x     y     z
		0.0f, 0.0f, 0.0f,
		
	//   r     g     b     a
		1.0f, 1.0f, 1.0f, 1.0f
	};
	
	public VBOVertex setVertex(float[] v) {
		for (int i = 0; i < v.length; i++) {
			vertex[i] = v[i];
		}
		return this;
	}
	
	public float[] getVertex() {
		return vertex;
	}
}