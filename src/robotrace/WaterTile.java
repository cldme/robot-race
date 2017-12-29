/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotrace;

/**
 *
 * @author asusbook
 */
public class WaterTile {
    
    public static final float TILE_SIZE = 60;
     
    private float height;
    private float x,z;
     
    public WaterTile(float centerX, float centerZ, float height){
        this.x = centerX;
        this.z = centerZ;
        this.height = height;
    }
 
    public float getHeight() {
        return height;
    }
 
    public float getX() {
        return x;
    }
 
    public float getZ() {
        return z;
    }
}
