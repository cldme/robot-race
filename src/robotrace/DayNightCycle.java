package robotrace;

import java.awt.Color;
import com.jogamp.opengl.GL2;

public class DayNightCycle {
    
    public float time;
    public long dayLengthSeconds;
    
    private final Color[][] skyColors = 
            new Color[][] {
            { new Color(17,21,86), // 0
            new Color(17,21,86),
            new Color(17,21,86),
            new Color(17,21,86),
            new Color(17,21,86) }
            ,
            { new Color(27,32,116), // 3
            new Color(37,43,127),
            new Color(52,58,147),
            new Color(63,69,160),
            new Color(86,92,182) }
            ,
            { new Color(53,94,138), // 6
            new Color(167,195,209),
            new Color(253,251,177),
            new Color(252,163,105),
            new Color(247,117,92) }  
            ,
            { new Color(2,100,199),  // 9
            new Color(98,151,227),
            new Color(141,177,240),
            new Color(171,198,247),
            new Color(179,206,252) }
            ,
            { new Color(1,120,200), // 12
            new Color(97,167,226),
            new Color(142,195,239),
            new Color(170,213,248),
            new Color(179,217,253) } 
            ,
            { new Color(2,100,199), // 15
            new Color(98,151,227),
            new Color(141,177,240),
            new Color(171,198,247),
            new Color(179,206,252) }  
            ,
            { new Color(53,94,138), // 18
            new Color(167,195,209),
            new Color(253,251,177),
            new Color(252,163,105),
            new Color(247,117,92) }
            ,
            { new Color(27,32,116), // 21
            new Color(37,43,127),
            new Color(52,58,147),
            new Color(63,69,160),
            new Color(86,92,182) }
            ,
            { new Color(17,21,86), // 24
            new Color(17,21,86),
            new Color(17,21,86),
            new Color(17,21,86),
            new Color(17,21,86) } 
            };
    
    public DayNightCycle (long dayLengthSeconds) {
        this.time = 0;
        this.dayLengthSeconds = dayLengthSeconds;
    }
    
    public void advanceTime (float timePassed) {
    
        time = timePassed * (skyColors.length - 1) / (float)dayLengthSeconds;
        //System.out.println(time);
    }
    
    public Color getSkyColor (float timePassed, int section) {
        advanceTime(timePassed);
        time %= skyColors.length - 1;
        int t = (int)time;
        time = time - t;
        return Interpolate(skyColors[t][section], skyColors[t+1][section], time);
    }
    
    private Color Interpolate (Color first, Color second, double t) {
        float[] compsA = new float[3];
        float[] compsB = new float[3];
        first.getColorComponents(compsA);
        second.getColorComponents(compsB);
        double r = compsA[0] * (1 - t) + compsB[0] * t;
        double g = compsA[1] * (1 - t) + compsB[1] * t;
        double b = compsA[2] * (1 - t) + compsB[2] * t;
        return new Color ((float)r,(float)g,(float)b);
    }
    
    private void setColor(Color c, GL2 gl) {
        float[] comps = new float[4];
        comps = c.getColorComponents(comps);
        gl.glColor3f(comps[0], comps[1], comps[2]);
    }
    
    public void drawSky (GL2 gl, GlobalState gs, float time, Robot robot) {
    
        float modelview[] = new float[16];

        gl.glPushMatrix();
        
        if(gs.camMode == 0) {
        gl.glTranslatef(-400 * (float)Math.cos(gs.theta), -400 * (float)Math.sin(gs.theta),0);
        } else {
        Vector trans = robot.direction.scale(10);
        gl.glTranslated(trans.x(),trans.y(),0);
        }

        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX , modelview, 0);

        modelview[0] = 1.0f;
        modelview[1] = 0;     
        modelview[2] = 0;
        modelview[4] = 0;
        modelview[5] = 1.0f;     
        modelview[6] = 0;
        
        gl.glLoadMatrixf(modelview, 0);
        
        gl.glBegin(GL2.GL_QUAD_STRIP);
        Color c = getSkyColor(time, 4);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, -1000);
        gl.glVertex3f(500, 0, -1000);
        
        c = getSkyColor(time, 4);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, -20);
        gl.glVertex3f(500, 0, -20);
        
        c = getSkyColor(time, 3);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, 0);
        gl.glVertex3f(500, 0, 0);
        
        c = getSkyColor(time, 2);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, 20);
        gl.glVertex3f(500, 0, 20);
        
        c = getSkyColor(time, 1);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, 40);
        gl.glVertex3f(500, 0, 40);
        
        c = getSkyColor(time, 0);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, 80);
        gl.glVertex3f(500, 0, 80);
        
        c = getSkyColor(time, 0);
        setColor(c, gl);
        gl.glVertex3f(-500, 0, 10000);
        gl.glVertex3f(500, 0, 10000);
        
        gl.glEnd();

        gl.glPopMatrix();
    }
    
}
