package pdm.gamedev.tutorial.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pdm.gamedev.tutorial.GameClass;

public class Hud implements Disposable {
    private Viewport viewport;

    public Hud(SpriteBatch batch) {
        viewport = new FitViewport(GameClass.V_WIDTH, GameClass.V_HEIGHT, new OrthographicCamera());
    }

    @Override
    public void dispose() {

    }
}
