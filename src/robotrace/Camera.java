package robotrace;

import java.nio.FloatBuffer;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(0f, 0f, 0f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

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
        
        eye.x = gs.vDist * Math.cos(gs.phi) * Math.cos(gs.theta) + gs.cnt.x;
        eye.y = gs.vDist * Math.cos(gs.phi) * Math.sin(gs.theta) + gs.cnt.y;
        eye.z = gs.vDist * Math.sin(gs.phi) + gs.cnt.z;
        
        center = gs.cnt;
        //String newLine = System.getProperty("line.separator");
        //System.out.printf(gs.cnt.toString() + newLine);
        
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot robot) {


        
    }
    
    public FloatBuffer getViewMatrix (GlobalState gs) {
    
        FloatBuffer buffer = FloatBuffer.allocate(16);
        Vector forward = gs.cnt.scale(-1).subtract(eye).normalized();
        Vector u = up.normalized();
        Vector right = forward.cross(u).normalized();
        u = right.cross(forward);
        
        buffer.put((float)right.x()).put((float)u.x()).put((float)forward.scale(-1).x()).put(0);
        buffer.put((float)right.y()).put((float)u.y()).put((float)forward.scale(-1).y()).put(0);
        buffer.put((float)right.z()).put((float)u.z()).put((float)forward.scale(-1).z()).put(0);
        buffer.put((float)right.dot(eye)*-1).put((float)u.dot(eye)*-1).put((float)forward.dot(eye)*-1).put(1);
        
        buffer.flip();

        return buffer;
    }
    
    public FloatBuffer getProjMatrix (GlobalState gs) {
    
        FloatBuffer buffer = FloatBuffer.allocate(16);
        float n = 0.1f;
        float f = 100f;
        float e = 1f / ((float)Math.tan(45f / 2f));
        float a = (float)gs.w / (float)gs.h;
        
        buffer.put(e).put(0).put(0).put(0);
        buffer.put(0).put(e/a).put(0).put(0);
        buffer.put(0).put(0).put(-(f+n)/(f-n)).put(-1);
        buffer.put(0).put(0).put(-(2*f*n)/(f-n)).put(0);

        buffer.flip();

        return buffer;
    }
}
