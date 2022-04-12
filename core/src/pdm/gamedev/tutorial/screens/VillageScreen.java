package pdm.gamedev.tutorial.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pdm.gamedev.tutorial.GameClass;
import pdm.gamedev.tutorial.characters.Soldier;
import pdm.gamedev.tutorial.scenes.Hud;
import pdm.gamedev.tutorial.tools.B2WorldCreator;

public class VillageScreen implements Screen {
    private GameClass game;
    private OrthographicCamera camera;
    private Viewport gameViewport;
    private Hud hud;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Soldier player;
    private TextureAtlas mushroomAtlas;

    //Box2d variables
    private World wolrd;
    private Box2DDebugRenderer b2dr;

    public VillageScreen(GameClass game) {
        this.game = game;
        mushroomAtlas = new TextureAtlas("dark_creature.atlas");
        camera = new OrthographicCamera();
        gameViewport = new FitViewport(GameClass.V_WIDTH, GameClass.V_HEIGHT, camera);
        hud = new Hud(game.batch);
        map = new TmxMapLoader().load("maps/map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);

        //box2d section
        //variables
        wolrd = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(wolrd, map);

        player = new Soldier(wolrd, this);
    }

    public void update(float deltaTime){
        handleInput(deltaTime);
        wolrd.step(1/60f, 6, 2);
        player.update(deltaTime);

        camera.update();
        renderer.setView(camera);
    }

    public void handleInput(float dt)
    {
        float speed = 150f;
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.b2body.setLinearVelocity(-speed,speed);
            }else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.setLinearVelocity(speed, speed);
            }else
                player.b2body.setLinearVelocity(0f, speed);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.b2body.setLinearVelocity(-speed, -speed);
            }else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.setLinearVelocity(speed, -speed);
            }else
                player.b2body.setLinearVelocity(0f, -speed);
        }else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.b2body.setLinearVelocity(-speed, 0f);
        }else if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.b2body.setLinearVelocity(speed, 0f);
        }else
            player.b2body.setLinearVelocity(0f, 0f);
    }

    public TextureAtlas getAtlas(){
        return mushroomAtlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        update(dt);
        handleInput(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(wolrd, camera.combined); //debug render for boxes

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int heigth) {
        gameViewport.update(width, heigth);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        wolrd.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
