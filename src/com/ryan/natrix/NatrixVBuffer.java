package com.ryan.natrix;

import java.nio.FloatBuffer;

public class NatrixVBuffer  {
	
	private FloatBuffer fBuffer; 
	
	NatrixVBuffer() {
		// TODO Auto-generated constructor stub
	}

	public FloatBuffer getfBuffer() {
		return fBuffer;
	}

	public void setfBuffer(FloatBuffer fBuffer) {
		this.fBuffer = fBuffer;
	}

	/** How many bytes per float. */
	private final int mBytesPerFloat = 4;

	/** How many elements per vertex. */
	private final int mStrideBytes = 7 * mBytesPerFloat;

	/** Offset of the position data. */
	private final int mPositionOffset = 0;

	/** Size of the position data in elements. */
	private final int mPositionDataSize = 3;

	/** Offset of the color data. */
	private final int mColorOffset = 3;

	/** Size of the color data in elements. */
	private final int mColorDataSize = 4;
	
	
	
}