package pdm.gamedev.tutorial;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import pdm.gamedev.tutorial.GameClass;

public class DesktopLauncher {
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 768;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Tutorial Game");

		config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
		new Lwjgl3Application(new GameClass(), config);
	}
}
