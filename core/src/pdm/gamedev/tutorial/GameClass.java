package pdm.gamedev.tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import pdm.gamedev.tutorial.characters.Mushroom;

import java.util.ArrayList;

public class GameClass extends Game {
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	Mushroom mushroomCharacter;

	TiledMapTileLayer layer;
	ArrayList<Actor> wallList = new ArrayList<>();

	//Box2d variables
	World wolrd;
	Box2DDebugRenderer b2dr;
	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		map = new TmxMapLoader().load("maps/map2.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		mushroomCharacter = new Mushroom(new Sprite(new Texture("mushroom.png")), (TiledMapTileLayer) map.getLayers().get(0));

		//Box2d section
		wolrd = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();

		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		MapObjects object = map.getLayers().get("PhysicsObj").getObjects();
		for(MapObject o: object){
			String name = o.getName();

			RectangleMapObject rectangle = (RectangleMapObject) o;
			Rectangle r = rectangle.getRectangle();

			if(name.equals("Start"))
				mushroomCharacter.setPosition(r.x, r.y);
		}

		for(MapObject obj: map.getLayers().get("Unreachable").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) obj).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

			body = wolrd.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
			fdef.shape = shape;
			body.createFixture(fdef);
		}


//		object = map.getLayers().get("Unreachable").getObjects();
//		for(MapObject solidObj: object){
//			RectangleMapObject rect = (RectangleMapObject) solidObj;
//			Rectangle r = rect.getRectangle();
//
//			Actor solid = new Actor();
//			solid.setPosition(r.x, r.y);
//			solid.setSize(r.width, r.height);
//			wallList.add(solid);
//		}

		Gdx.input.setInputProcessor(mushroomCharacter);
	}

	public void update(float dt){
		wolrd.step(1/60f, 6, 2);
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();


//		renderer.getBatch().begin();
//		mushroomCharacter.draw(renderer.getBatch());
//		renderer.getBatch().end();

		b2dr.render(wolrd, camera.combined);
		update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		map.dispose();
		renderer.dispose();
	}
}
