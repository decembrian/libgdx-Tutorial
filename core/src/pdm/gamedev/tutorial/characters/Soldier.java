package pdm.gamedev.tutorial.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import pdm.gamedev.tutorial.screens.VillageScreen;


public class Soldier extends Sprite{
    private TextureRegion soldierWalkForward;
    private TextureRegion soldierWalkBackward;
    private TextureRegion soldierWalkLeft;
    private TextureRegion soldierWalkRight;
    private Animation soldiersForwardWalk;
    private Animation soldiersBackwardWalk;
    private Animation soldiersLeftWalk;
    private Animation soldiersRightWalk;
    private float stateTimer; //probable never needed
    private static int step;

    public enum State { UP, DOWN, LEFT, RIGHT };
    public State currentState, previousState;

    //Box2d variables
    public World world;
    public Body b2body;

    public Soldier(World world, VillageScreen screen){
        super(screen.getAtlas().findRegion("soldier"));
        this.world = world;
        currentState = State.UP;
        stateTimer = 0;
        step = 0;
        //for static texture. do not need
        //soldierWalkForward = new TextureRegion(getTexture(), 3, 99, 32, 32);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        loadAnimation(frames);

        soldierWalkForward = new TextureRegion(new Texture("moveForward.png"));
        soldierWalkBackward = new TextureRegion(new Texture("moveBack.png"));
        soldierWalkLeft = new TextureRegion(new Texture("moveLeft.png"));
        soldierWalkRight = new TextureRegion(new Texture("moveRight.png"));

        defineSoldier();
        setBounds(35, 35, 32, 32);
        setRegion(soldierWalkForward);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float deltaTime){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case UP:
                region = soldierWalkForward;
                break;
            case DOWN:
                region = soldierWalkBackward;
                break;
            case LEFT:
                region = soldierWalkLeft;
                break;
            case RIGHT:
                region = soldierWalkRight;
                break;
        }
        return region;
    }

    public State getState(){
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            return State.UP;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            return State.DOWN;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            return State.LEFT;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            return State.RIGHT;
    }

    public void defineSoldier(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50, 50);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void loadAnimation(Array<TextureRegion> frames){
        for(int i = 3; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i + step, 99, 32, 32));
            step += 32;
        }
        soldiersForwardWalk = new Animation(0.1f, frames);
        frames.clear();
        step = 0;

        for(int i = 3; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i + step, 67, 32, 32));
            step += 32;
        }
        soldiersRightWalk = new Animation(0.1f, frames);
        frames.clear();
        step = 0;

        for(int i = 3; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i + step, 35, 32, 32));
            step += 32;
        }
        soldiersLeftWalk = new Animation(0.1f, frames);
        frames.clear();
        step = 0;

        for(int i = 3; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i + step, 3, 32, 32));
            step += 32;
        }
        soldiersBackwardWalk = new Animation(0.1f, frames);
        frames.clear();
    }
}
