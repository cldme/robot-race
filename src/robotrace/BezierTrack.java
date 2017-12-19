
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    private final Vector[] controlPoints;

    public int CurveCount () {
    
        return (controlPoints.length - 1) / 3;
    
    }
    
    public double Clamp01 (double value) {
    
        if (value < 0) { return 0; }
        else if (value > 1) { return 1; }
        return value;
        
    }
    
    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
        
    }
    
    @Override
    protected Vector getPoint(double t) {
        
        int i;
	if (t >= 1d) {
            t = 1d;
            i = controlPoints.length - 4;
	}
	else {
            t = Clamp01(t) * CurveCount();
            i = (int)t;
            t -= i;
            i *= 3;
	}
        
        return super.getCubicBezierPnt(t, controlPoints[i], controlPoints[i + 1], controlPoints[i + 2], controlPoints[i + 3]);
    }

    @Override
    protected Vector getTangent(double t) {
        
        int i;
	if (t >= 1d) {
            t = 1d;
            i = controlPoints.length - 4;
	}
	else {
            t = Clamp01(t) * CurveCount();
            i = (int)t;
            t -= i;
            i *= 3;
	}
        
        return super.getCubicBezierTng(t, controlPoints[i], controlPoints[i + 1], controlPoints[i + 2], controlPoints[i + 3]);
    }

}
