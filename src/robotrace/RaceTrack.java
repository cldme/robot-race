package robotrace;

import static com.jogamp.opengl.GL.GL_LINES;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;


/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    private final static int resolution = 300;
    private final static int repeats = 30;
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        
        Textures.track.bind(gl);
        Textures.track.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);

        drawTop(gl);
        drawOuter(gl);
        drawInner(gl);
        //drawBezier(gl);
    }
    
    void drawBezier(GL2 gl) {
    
    gl.glBegin(GL_POINTS);
    
        for (int points = 0; points <= 400; points++) {
                
            Vector point = getPoint(points/400d);
            gl.glColor3f(1f,0f,0f);
            gl.glVertex3d(point.x,point.y,point.z);
                    
        }
    
    gl.glEnd();
    
    gl.glBegin(GL_LINES);
    
        for (int points = 0; points <= 400; points++) {
                
            Vector point = getPoint(points/400d);
            Vector normal = getTangent(points/400d).cross(new Vector (0f,0f,1f)).normalized();
            
            gl.glVertex3d(point.x,point.y,point.z);
            gl.glVertex3d(point.x+normal.x,point.y+normal.y,point.z+normal.z);
                    
        }
    
    gl.glEnd();    

    }
    
    void drawTopQuad (GL2 gl) {
        gl.glBegin(GL_QUADS);
            for (int i = -2; i < 2; i++) {
                
                for (int points = 0; points < resolution; points++) {
                
                    Vector pointPrev = getPoint(points/(double)resolution);
                    Vector pointNext = getPoint((points + 1)/(double)resolution);
                    Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();
                    Vector normal2 = getTangent((points+1)/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();

                    Vector result = pointPrev.add(normal.scale((i+1) * laneWidth));
                    Vector result2 = pointPrev.add(normal.scale(i * laneWidth));
                    Vector result3 = pointNext.add(normal2.scale(i* laneWidth));
                    Vector result4 = pointNext.add(normal2.scale((i+1) * laneWidth));
                    
                    
                    gl.glNormal3d(0,0,1);
                    
                    gl.glTexCoord2d(0, 0);
                    gl.glVertex3d(result.x,result.y,result.z);
                    gl.glTexCoord2d(1, 0);
                    gl.glVertex3d(result2.x,result2.y,result2.z);
                    gl.glTexCoord2d(0, 1);
                    gl.glVertex3d(result3.x,result3.y,result3.z);
                    gl.glTexCoord2d(1, 1);
                    gl.glVertex3d(result4.x,result4.y,result4.z);
                    
                }

                
            }
    gl.glEnd();
    }
    
    void drawTop (GL2 gl) {
        
            for (int i = -2; i < 2; i++) {
                
                gl.glBegin(GL_QUAD_STRIP);
                for (int points = 0; points < 2; points++) {
                
                    Vector point = getPoint(points/(double)resolution);
                    Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();
                    
                    Vector result = point.add(normal.scale((i+1) * laneWidth));
                    Vector result2 = point.add(normal.scale(i * laneWidth));
                    gl.glNormal3d(0,0,1); //
                    gl.glTexCoord2d(1, points * (repeats / (double)resolution));
                    gl.glVertex3d(result.x,result.y,result.z);
                    gl.glTexCoord2d(0, points * (repeats / (double)resolution));
                    gl.glVertex3d(result2.x,result2.y,result2.z);
                    
                }            

                for (int points = 2; points <= resolution; points++) {

                    Vector point = getPoint(points/(double)resolution);
                    Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();
                    
                    Vector result = point.add(normal.scale((i+1) * laneWidth));
                    Vector result2 = point.add(normal.scale(i * laneWidth));
                    gl.glNormal3d(0, 0, 1);
                    gl.glTexCoord2d(1, points * (repeats / (double)resolution));
                    gl.glVertex3d(result.x,result.y,result.z);
                    gl.glTexCoord2d(0, points * (repeats / (double)resolution));
                    gl.glVertex3d(result2.x,result2.y,result2.z);
                }
                gl.glEnd();
            }
    }
    
    void drawOuter (GL2 gl) {
        
        gl.glBegin(GL_QUAD_STRIP);
        
        for (int points = 0; points < 2; points++) {
        
            Vector point = getPoint(points/(double)resolution);
            Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();

            Vector result = point.add(normal.scale(2*laneWidth));
            
            gl.glNormal3d(-normal.x,-normal.y,0);
            gl.glTexCoord2d(points * (repeats / (double)resolution),1);
            gl.glVertex3d(result.x,result.y,result.z);
            gl.glTexCoord2d(points * (repeats / (double)resolution),0);
            gl.glVertex3d(result.x,result.y,result.z-1);
        }
        
        for (int points = 2; points <= resolution; points++) {
            
            Vector point = getPoint(points/(double)resolution);
            Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();
            
            Vector result = point.add(normal.scale(2*laneWidth));
            
            gl.glNormal3d(-normal.x,-normal.y,0);
            gl.glTexCoord2d(points * (repeats / (double)resolution),1);
            gl.glVertex3d(result.x,result.y,result.z);
            gl.glTexCoord2d(points * (repeats / (double)resolution),0);
            gl.glVertex3d(result.x,result.y,result.z-1);
        }
        gl.glEnd();
    }
    
    void drawInner (GL2 gl) {
        gl.glBegin(GL_QUAD_STRIP);
        
        for (int points = 0; points < 2; points++) {
        
            Vector point = getPoint(points/(double)resolution);
            Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();

            Vector result = point.add(normal.scale(-2*laneWidth));
            
            gl.glNormal3d(-normal.x,-normal.y,0);
            gl.glTexCoord2d(points * (repeats / (double)resolution),1);
            gl.glVertex3d(result.x,result.y,result.z);
            gl.glTexCoord2d(points * (repeats / (double)resolution),0);
            gl.glVertex3d(result.x,result.y,result.z-1);
        }
        
        for (int points = 2; points <= resolution; points++) {
            
            Vector point = getPoint(points/(double)resolution);
            Vector normal = getTangent(points/(double)resolution).cross(new Vector (0f,0f,1f)).normalized();
                
            Vector result = point.add(normal.scale(-2*laneWidth));
            
            gl.glNormal3d(-normal.x,-normal.y,0);
            gl.glTexCoord2d(points * (repeats / (double)resolution),1);
            gl.glVertex3d(result.x,result.y,result.z);
            gl.glTexCoord2d(points * (repeats / (double)resolution),0);
            gl.glVertex3d(result.x,result.y,result.z-1);
        }
        gl.glEnd();
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){

        return Vector.O;

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        return Vector.O;

    }
    
    public Vector getCubicBezierPnt(double t, Vector P0, Vector P1,Vector P2, Vector P3) {
        double oneMinusT = 1d - t;
        
        return P0.scale(oneMinusT * oneMinusT * oneMinusT).add
              (P1.scale(3d * oneMinusT * oneMinusT * t).add
              (P2.scale(3d * oneMinusT * t * t)).add
              (P3.scale(t * t * t)));
    }
    
    public Vector getCubicBezierTng(double t, Vector P0, Vector P1,Vector P2, Vector P3) {
        double oneMinusT = 1d - t;
        
        return P1.subtract(P0).scale
              (3d * oneMinusT * oneMinusT).add
              (P2.subtract(P1).scale
              (6d * oneMinusT * t).add
              (P3.subtract(P2).scale
              (3d * t * t)));
    }
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
