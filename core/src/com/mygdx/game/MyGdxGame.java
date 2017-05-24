package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {

	// Constant rows and columns of the sprite sheet
	private static final int PERSON_FRAME_COLS = 6, PERSON_FRAME_ROWS = 1;
	private static final int FRAME_COLS = 5, FRAME_ROWS = 4;

	// Objects used
	Animation<TextureRegion> personAnimation; // Must declare frame type (TextureRegion)
	Animation<TextureRegion> fireAnimation; // Must declare frame type (TextureRegion)
	Texture personSheet, fireSheet;
	SpriteBatch spriteBatch;

	// A variable for tracking elapsed time for the animation
	float stateTime;

	@Override
	public void create() {

		// Load the sprite sheet as a Texture
		personSheet = new Texture(Gdx.files.internal("spritestrip.png"));
		fireSheet = new Texture(Gdx.files.internal("fire.png"));

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(personSheet,
																								personSheet.getWidth() / PERSON_FRAME_COLS,
																								personSheet.getHeight() / PERSON_FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[PERSON_FRAME_COLS * PERSON_FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < PERSON_FRAME_ROWS; i++) {
			for (int j = 0; j < PERSON_FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		personAnimation = new Animation<TextureRegion>(0.08f, walkFrames);

		tmp = TextureRegion.split(fireSheet,
															fireSheet.getWidth() / FRAME_COLS,
															fireSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		fireAnimation = new Animation<TextureRegion>(0.08f, walkFrames);


		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = personAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		// process user input
		if(Gdx.input.isTouched()) {
			currentFrame = fireAnimation.getKeyFrame(stateTime, false);
		}
		spriteBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
		spriteBatch.end();
	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		personSheet.dispose();
	}
}