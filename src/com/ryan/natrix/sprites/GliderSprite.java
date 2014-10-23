package com.ryan.natrix.sprites;

import com.ryan.natrix.Triangle;

public class GliderSprite extends Triangle {
	
	public GliderSprite() {
		super();
		
		float vertices[] = {
			-0.6f, -0.2f,  0.0f,		// V1 - first vertex (x,y,z)
			 0.6f, -0.2f,  0.0f,		// V2 - second vertex
			 0.0f,  0.4f,  0.0f			// V3 - third vertex
		};
	
		setVertices(vertices);
		setColor(0.8f, 0.8f, 0.4f, 1.0f);
	}
	
}