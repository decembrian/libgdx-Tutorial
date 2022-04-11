package pdm.gamedev.tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pdm.gamedev.tutorial.screens.VillageScreen;

public class GameClass extends Game {
	public static final int V_WIDTH = 1024; //512 for x2 zooming
	public static final int V_HEIGHT = 768; //384 for x2 zooming
	public static final float PPM = 100; //pixels per meter

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new VillageScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
