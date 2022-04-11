package pdm.gamedev.tutorial.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Mushroom extends Sprite{

    //Box2d variables
    public World world;
    public Body b2body;

    public Mushroom(World world){
        this.world = world;
        defineMushroom();
    }

    public void defineMushroom(){
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
}
