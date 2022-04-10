package pdm.gamedev.tutorial.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.awt.*;

public class MapUtils {
    public static TiledMap map;

    public static void initialize(TiledMap map){
        MapUtils.map = map;
    }

    private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    private static Array<Rectangle> tiles = new Array<>();

    public static Array<Rectangle> getTiles(int startX, int startY, int endX, int endY, String layerName){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);

        //return the rectangle objects to the pool from previous frame
        rectPool.freeAll(tiles);
        tiles.clear();

        for(int y = startY; y <= endY; y++){
            for(int x = startX; x <= endX; x++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if(cell != null){
                    Rectangle rect = rectPool.obtain();
                    rect.setRect(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
        return tiles;
    }
}
