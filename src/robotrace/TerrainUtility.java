package robotrace;


public final class TerrainUtility {
    
    public static final int patches = 64; //unused for now
    
    public static Vector[] controlPoints =
            new Vector[] {  new Vector(-20f, -20f, -13f),
                            new Vector(-13.3333333f, -20f, 0),
                            new Vector(-6.6666666f, -20f, 0),
                            new Vector(0, -20f, 0),
                            new Vector(-20f, -13.3333333f, 10f),
                            new Vector(-13.3333333, -13.3333333f, 10f),
                            new Vector(-6.6666666f, -13.3333333f, -25f),
                            new Vector(0, -13.3333333f, 5f),
                            new Vector(-20f, -6.6666666f, 0),
                            new Vector(-13.3333333, -6.6666666f, -3f),
                            new Vector(-6.6666666f, -6.6666666f, 0),
                            new Vector(0, -6.6666666f, 16f),
                            new Vector(-20f, -0, 4f),
                            new Vector(-13.3333333, 0, 22f),
                            new Vector(-6.6666666f, 0, 0),
                            new Vector(0, 0, -7f)
            };
    
    
    public static Vector getPointBezier (double t, Vector P0, Vector P1, Vector P2, Vector P3) {
    double oneMinusT = 1d - t;
        
        return P0.scale(oneMinusT * oneMinusT * oneMinusT).add
              (P1.scale(3d * oneMinusT * oneMinusT * t).add
              (P2.scale(3d * oneMinusT * t * t)).add
              (P3.scale(t * t * t)));
    }
    
    public static Vector getTangentBezier (double t, Vector P0, Vector P1, Vector P2, Vector P3) {
        double oneMinusT = 1d - t;
        
        return P1.subtract(P0).scale
              (3d * oneMinusT * oneMinusT).add
              (P2.subtract(P1).scale
              (6d * oneMinusT * t).add
              (P3.subtract(P2).scale
              (3d * t * t)));
    }
    
    public static Vector getPointSurface (double u, double v, Vector[] cP) {
    
        Vector[] uCurve = new Vector[4];
        for (int i = 0; i < 4; i++)
            uCurve[i] = getPointBezier(u, cP[0 + 4*i], cP[1 + 4*i], cP[2 + 4*i], cP[3 + 4*i]); 
    
        return getPointBezier(v, uCurve[0], uCurve[1], uCurve[2], uCurve[3]);
    }
    
    public static Vector dUBezier (double u, double v, Vector[] cPs) {
        
        Vector P[] = new Vector[4];
        Vector vCurve[] = new Vector[4];
        for (int i = 0; i < 4; ++i) {
            P[0] = cPs[i];
            P[1] = cPs[4 + i];
            P[2] = cPs[8 + i];
            P[3] = cPs[12 + i];
            vCurve[i] = getPointBezier(v, P[0], P[1], P[2], P[3]);
        } 
    
        return getTangentBezier(u, vCurve[0], vCurve[1], vCurve[2], vCurve[3]);
    }
    
    public static Vector dVBezier (double u, double v, Vector[] cPs) {
    
        Vector uCurve[] = new Vector[4];
        for (int i = 0; i < 4; ++i) {
            uCurve[i] = getPointBezier(u, cPs[0 + 4*i],cPs[1 + 4*i],cPs[2 + 4*i],cPs[3 + 4*i]);
        } 
    
        return getTangentBezier(v, uCurve[0], uCurve[1], uCurve[2], uCurve[3]);
    }
}
