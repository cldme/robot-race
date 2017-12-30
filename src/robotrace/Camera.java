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
        center = gs.cnt;
        eye.x = gs.vDist * Math.cos(gs.phi) * Math.cos(gs.theta) + gs.cnt.x;
        eye.y = gs.vDist * Math.cos(gs.phi) * Math.sin(gs.theta) + gs.cnt.y;
        eye.z = gs.vDist * Math.sin(gs.phi) + gs.cnt.z;
        
        
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
        Vector forward = eye.subtract(gs.cnt).normalized();
        Vector right = up.cross(forward).normalized();
        Vector u = forward.cross(right);
        
//        buffer.put((float)right.x()).put((float)u.x()).put((float)forward.scale(-1).x()).put(0);
//        buffer.put((float)right.y()).put((float)u.y()).put((float)forward.scale(-1).y()).put(0);
//        buffer.put((float)right.z()).put((float)u.z()).put((float)forward.scale(-1).z()).put(0);
//        buffer.put((float)right.dot(eye)*-1).put((float)u.dot(eye)*-1).put((float)forward.dot(eye)*-1).put(1);
        
        buffer.put((float)right.x()).put((float)right.y()).put((float)right.z()).put(0);
        buffer.put((float)u.x()).put((float)u.y()).put((float)u.z()).put(0);
        buffer.put((float)forward.x()).put((float)forward.y()).put((float)forward.z()).put(0);
        
        buffer.put((float)right.dot(eye)).
               put((float)u.dot(eye)).
               put((float)forward.dot(eye)).
               put(1);
        
        buffer.flip();

        return buffer;
    }
    
    public FloatBuffer getProjMatrix (GlobalState gs) {
    
        FloatBuffer buffer = FloatBuffer.allocate(16);
        float n = 0.1f;
        float f = 100f;
        float e = 1f / (float)(Math.tan(Math.toRadians(45f)));
        float a = (float)gs.w / (float)gs.h;
        
        buffer.put(e).put(0).put(0).put(0);
        buffer.put(0).put(e/a).put(0).put(0);
        buffer.put(0).put(0).put((-(f+n))/(f-n)).put(-1);
        buffer.put(0).put(0).put(-(2*f*n)/(f-n)).put(0);
//        float n = 0.1f;
//        float f = 100f;
//        float scale = (float)Math.tan(Math.toRadians(45f)) * n;
//        float r = gs.w/gs.h * scale;
//        float l = -r;
//        float t = scale;
//        float b = -t;
//        
//        buffer.put(2 * n / (r - 1)).put(0).put(0).put(0);
//        buffer.put(0).put(2 * n / (t - b)).put(0).put(0);
//        buffer.put((r + l) / (r - l)).put((t + b) / (t - b)).put(-(f + n) / (f - n)).put(-1);
//        buffer.put(0).put(0).put(-2 * f * n / (f - n)).put(0);
        
        buffer.flip();

        return buffer;
    }
}
