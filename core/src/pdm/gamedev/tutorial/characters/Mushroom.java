package pdm.gamedev.tutorial.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Mushroom extends Sprite implements InputProcessor {

    private static final int acceleration = 3;

    //Box2d variables
    public World world;
    public Body b2body;

    private Vector2 velocity = new Vector2();
    private float speed = 25 * acceleration;
    private TiledMapTileLayer collisionLayer;
    private Rectangle mushroomRect;

    public Mushroom(World world){
        this.world = world;
        defineMushroom();
    }

    public Mushroom(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        mushroomRect = new Rectangle();
    }

    public void defineMushroom(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32, 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta){

        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        boolean collisionX = false, collisionY = false;
        float oldX = getX(), oldY = getY();


        //move on X axis
        setX(getX() + velocity.x * delta);
        if (velocity.x < 0) {
            //check if the left tile is mark 'blocked'
            collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
                    .getTile().getProperties().containsKey("blocked");

            //check if the middle left tile is mark 'blocked'
            if(!collisionX)
                collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / 2 / tileHeight))
                    .getTile().getProperties().containsKey("blocked");

            //check if the bottom left tile is mark 'blocked'
            if(!collisionX)
                collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight))
                    .getTile().getProperties().containsKey("blocked");
        }else if(velocity.x > 0){
            //check if top right tile is mark 'blocked'
            collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth),(int) ((getY() + getHeight()) / tileHeight))
                    .getTile().getProperties().containsKey("blocked");

            //check if middle right tile is mark 'blocked'
            if(!collisionX)
                collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight))
                        .getTile().getProperties().containsKey("blocked");

            //check if bottom right tile is mark 'blocked'
            if(!collisionX)
                collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight))
                        .getTile().getProperties().containsKey("blocked");
        }

        //react to collision X
        if(collisionX){
            setX(oldX);
            velocity.x = 0;
        }


        //move on Y
        setY(getY() + velocity.y * delta);
        if(velocity.y < 0){
            //check if bottom left tile is mark 'blocked'
            collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight))
                    .getTile().getProperties().containsKey("blocked");

            //check if middle left tile is mark 'blocked'
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) (getY() / tileHeight))
                        .getTile().getProperties().containsKey("blocked");

            //check if bottom left tile is mark 'blocked'
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) (((getX() + getWidth()) / tileWidth)), (int) ((getY() / tileHeight)))
                        .getTile().getProperties().containsKey("blocked");
        }else if(velocity.y > 0){
            //check if top left tile is mark 'blocked
            collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
                    .getTile().getProperties().containsKey("blocked");

            //check if top middle tile is mark 'blocked'
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight())/ tileHeight))
                        .getTile().getProperties().containsKey("blocked");

            //check if top right tile is mark 'blocked'
            if(!collisionY)
                collisionY = collisionLayer.getCell((int) ((getX() + getWidth())/ tileWidth), (int) ((getY() + getHeight()) / tileHeight))
                        .getTile().getProperties().containsKey("blocked");
        }

        //react to collision Y
        if(collisionY){
            setY(oldY);
            velocity.y = 0;
        }
    }

    @Override
    public boolean keyDown(int i) {
        switch (i){
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.A:
                velocity.x  = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int i) {
        switch (i){
            case Input.Keys.A:
                velocity.x = 0;
                break;
            case Input.Keys.D:
                velocity.x = 0;
                break;
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.W:
                velocity.y = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    //Getters & Setters block
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
