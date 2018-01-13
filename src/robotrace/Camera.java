package robotrace;

import java.nio.FloatBuffer;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(0f, 0f, 0f);

    /** The point to which the camera is looking. */
    public Vector center = new Vector(0,0,0);

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);  
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        center = gs.cnt;
//        if (gs.vDist > 60)
//            gs.vDist = 60;
//        center.x = center.x > 0 ? Math.min(center.x, 50) : Math.max(center.x, -50);
//        center.y = center.y > 0 ? Math.min(center.y, 50) : Math.max(center.y, -50);
//        center.z = center.z > 0 ? Math.min(center.z, 20) : Math.max(center.z, -20);
        eye.x = gs.vDist * Math.cos(gs.phi) * Math.cos(gs.theta) + gs.cnt.x;
        eye.y = gs.vDist * Math.cos(gs.phi) * Math.sin(gs.theta) + gs.cnt.y;
        eye.z = gs.vDist * Math.sin(gs.phi) + gs.cnt.z;        
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot robot) {

    

    }


    public void invertPitch(GlobalState gs, Water water) {
        
        gs.cnt.z -= 2d * ((float)gs.cnt.z() - water.getHeight());
        gs.phi *= -1;
    }
    
    public Vector getPosition () {
    
        return eye;
    }
}
