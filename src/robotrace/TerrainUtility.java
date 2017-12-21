package robotrace;


public final class TerrainUtility {
    
    public final static int p = 3;
    public final static double ps = 40d / (double)p; // patchSide
    public final static int subdivisions = 16;
    public final static double stp = 40d/((double)(p * 3));
    static final double c = -20d; // used for bottom left corner reference
    private static int n = 0;
    
    
    public static Vector[][] bezierSurfaces =
            new Vector[][] {
                
                {   new Vector(c          , c,           0),
                    new Vector(c +     stp, c,           0),
                    new Vector(c + 2 * stp, c,           0),
                    new Vector(c + 3 * stp, c,           0),
                    new Vector(c          , c +     stp, 0),
                    new Vector(c +     stp, c +     stp, 0),
                    new Vector(c + 2 * stp, c +     stp, 0),
                    new Vector(c + 3 * stp, c +     stp, 0),
                    new Vector(c          , c + 2 * stp, 0),
                    new Vector(c +     stp, c + 2 * stp, 0),
                    new Vector(c + 2 * stp, c + 2 * stp, 0),
                    new Vector(c + 3 * stp, c + 2 * stp, 0),
                    new Vector(c          , c + 3 * stp, 0),
                    new Vector(c +     stp, c + 3 * stp, 0),
                    new Vector(c + 2 * stp, c + 3 * stp, 0),
                    new Vector(c + 3 * stp, c + 3 * stp, 0)  }
                    ,
                {   new Vector(c           + ps, c          , 0),
                    new Vector(c +     stp + ps, c          , 0),
                    new Vector(c + 2 * stp + ps, c          , 0),
                    new Vector(c + 3 * stp + ps, c          , 0),
                    new Vector(c           + ps, c +     stp, 0),
                    new Vector(c +     stp + ps, c +     stp, 0),
                    new Vector(c + 2 * stp + ps, c +     stp, 0),
                    new Vector(c + 3 * stp + ps, c +     stp, 0),
                    new Vector(c           + ps, c + 2 * stp, 0),
                    new Vector(c +     stp + ps, c + 2 * stp, 0),
                    new Vector(c + 2 * stp + ps, c + 2 * stp, 0),
                    new Vector(c + 3 * stp + ps, c + 2 * stp, 0),
                    new Vector(c           + ps, c + 3 * stp, 0),
                    new Vector(c +     stp + ps, c + 3 * stp, 0),
                    new Vector(c + 2 * stp + ps, c + 3 * stp, 0),
                    new Vector(c + 3 * stp + ps, c + 3 * stp, 0)  }
                    ,
                {   new Vector(c           +(2 % p) * ps, c +           (2 / p) * ps, 0),
                    new Vector(c +     stp +(2 % p) * ps, c +           (2 / p) * ps, 0),
                    new Vector(c + 2 * stp +(2 % p) * ps, c +           (2 / p) * ps, 0),
                    new Vector(c + 3 * stp +(2 % p) * ps, c +           (2 / p) * ps, 0),
                    new Vector(c           +(2 % p) * ps, c +     stp + (2 / p) * ps, 0),
                    new Vector(c +     stp +(2 % p) * ps, c +     stp + (2 / p) * ps, 0),
                    new Vector(c + 2 * stp +(2 % p) * ps, c +     stp + (2 / p) * ps, 0),
                    new Vector(c + 3 * stp +(2 % p) * ps, c +     stp + (2 / p) * ps, 0),
                    new Vector(c           +(2 % p) * ps, c + 2 * stp + (2 / p) * ps, 0),
                    new Vector(c +     stp +(2 % p) * ps, c + 2 * stp + (2 / p) * ps, 0),
                    new Vector(c + 2 * stp +(2 % p) * ps, c + 2 * stp + (2 / p) * ps, 0),
                    new Vector(c + 3 * stp +(2 % p) * ps, c + 2 * stp + (2 / p) * ps, 0),
                    new Vector(c           +(2 % p) * ps, c + 3 * stp + (2 / p) * ps, 0),
                    new Vector(c +     stp +(2 % p) * ps, c + 3 * stp + (2 / p) * ps, 0),
                    new Vector(c + 2 * stp +(2 % p) * ps, c + 3 * stp + (2 / p) * ps, 0),
                    new Vector(c + 3 * stp +(2 % p) * ps, c + 3 * stp + (2 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(3 % p) * ps, c +           (3 / p) * ps, 0),
                    new Vector(c +     stp +(3 % p) * ps, c +           (3 / p) * ps, 0),
                    new Vector(c + 2 * stp +(3 % p) * ps, c +           (3 / p) * ps, 0),
                    new Vector(c + 3 * stp +(3 % p) * ps, c +           (3 / p) * ps, 0),
                    new Vector(c           +(3 % p) * ps, c +     stp + (3 / p) * ps, 0),
                    new Vector(c +     stp +(3 % p) * ps, c +     stp + (3 / p) * ps, 0),
                    new Vector(c + 2 * stp +(3 % p) * ps, c +     stp + (3 / p) * ps, 0),
                    new Vector(c + 3 * stp +(3 % p) * ps, c +     stp + (3 / p) * ps, 0),
                    new Vector(c           +(3 % p) * ps, c + 2 * stp + (3 / p) * ps, 0),
                    new Vector(c +     stp +(3 % p) * ps, c + 2 * stp + (3 / p) * ps, 0),
                    new Vector(c + 2 * stp +(3 % p) * ps, c + 2 * stp + (3 / p) * ps, 0),
                    new Vector(c + 3 * stp +(3 % p) * ps, c + 2 * stp + (3 / p) * ps, 0),
                    new Vector(c           +(3 % p) * ps, c + 3 * stp + (3 / p) * ps, 0),
                    new Vector(c +     stp +(3 % p) * ps, c + 3 * stp + (3 / p) * ps, 0),
                    new Vector(c + 2 * stp +(3 % p) * ps, c + 3 * stp + (3 / p) * ps, 0),
                    new Vector(c + 3 * stp +(3 % p) * ps, c + 3 * stp + (3 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(4 % p) * ps, c +           (4 / p) * ps, 0),
                    new Vector(c +     stp +(4 % p) * ps, c +           (4 / p) * ps, 0),
                    new Vector(c + 2 * stp +(4 % p) * ps, c +           (4 / p) * ps, 0),
                    new Vector(c + 3 * stp +(4 % p) * ps, c +           (4 / p) * ps, 0),
                    new Vector(c           +(4 % p) * ps, c +     stp + (4 / p) * ps, 0),
                    new Vector(c +     stp +(4 % p) * ps, c +     stp + (4 / p) * ps, 0),
                    new Vector(c + 2 * stp +(4 % p) * ps, c +     stp + (4 / p) * ps, 0),
                    new Vector(c + 3 * stp +(4 % p) * ps, c +     stp + (4 / p) * ps, 0),
                    new Vector(c           +(4 % p) * ps, c + 2 * stp + (4 / p) * ps, 0),
                    new Vector(c +     stp +(4 % p) * ps, c + 2 * stp + (4 / p) * ps, 0),
                    new Vector(c + 2 * stp +(4 % p) * ps, c + 2 * stp + (4 / p) * ps, 0),
                    new Vector(c + 3 * stp +(4 % p) * ps, c + 2 * stp + (4 / p) * ps, 0),
                    new Vector(c           +(4 % p) * ps, c + 3 * stp + (4 / p) * ps, 0),
                    new Vector(c +     stp +(4 % p) * ps, c + 3 * stp + (4 / p) * ps, 0),
                    new Vector(c + 2 * stp +(4 % p) * ps, c + 3 * stp + (4 / p) * ps, 0),
                    new Vector(c + 3 * stp +(4 % p) * ps, c + 3 * stp + (4 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(5 % p) * ps, c +           (5 / p) * ps, 0),
                    new Vector(c +     stp +(5 % p) * ps, c +           (5 / p) * ps, 0),
                    new Vector(c + 2 * stp +(5 % p) * ps, c +           (5 / p) * ps, 0),
                    new Vector(c + 3 * stp +(5 % p) * ps, c +           (5 / p) * ps, 0),
                    new Vector(c           +(5 % p) * ps, c +     stp + (5 / p) * ps, 0),
                    new Vector(c +     stp +(5 % p) * ps, c +     stp + (5 / p) * ps, 0),
                    new Vector(c + 2 * stp +(5 % p) * ps, c +     stp + (5 / p) * ps, 0),
                    new Vector(c + 3 * stp +(5 % p) * ps, c +     stp + (5 / p) * ps, 0),
                    new Vector(c           +(5 % p) * ps, c + 2 * stp + (5 / p) * ps, 0),
                    new Vector(c +     stp +(5 % p) * ps, c + 2 * stp + (5 / p) * ps, 0),
                    new Vector(c + 2 * stp +(5 % p) * ps, c + 2 * stp + (5 / p) * ps, 0),
                    new Vector(c + 3 * stp +(5 % p) * ps, c + 2 * stp + (5 / p) * ps, 0),
                    new Vector(c           +(5 % p) * ps, c + 3 * stp + (5 / p) * ps, 0),
                    new Vector(c +     stp +(5 % p) * ps, c + 3 * stp + (5 / p) * ps, 0),
                    new Vector(c + 2 * stp +(5 % p) * ps, c + 3 * stp + (5 / p) * ps, 0),
                    new Vector(c + 3 * stp +(5 % p) * ps, c + 3 * stp + (5 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(6 % p) * ps, c +           (6 / p) * ps, 0),
                    new Vector(c +     stp +(6 % p) * ps, c +           (6 / p) * ps, 0),
                    new Vector(c + 2 * stp +(6 % p) * ps, c +           (6 / p) * ps, 0),
                    new Vector(c + 3 * stp +(6 % p) * ps, c +           (6 / p) * ps, 0),
                    new Vector(c           +(6 % p) * ps, c +     stp + (6 / p) * ps, 0),
                    new Vector(c +     stp +(6 % p) * ps, c +     stp + (6 / p) * ps, 0),
                    new Vector(c + 2 * stp +(6 % p) * ps, c +     stp + (6 / p) * ps, 0),
                    new Vector(c + 3 * stp +(6 % p) * ps, c +     stp + (6 / p) * ps, 0),
                    new Vector(c           +(6 % p) * ps, c + 2 * stp + (6 / p) * ps, 0),
                    new Vector(c +     stp +(6 % p) * ps, c + 2 * stp + (6 / p) * ps, 0),
                    new Vector(c + 2 * stp +(6 % p) * ps, c + 2 * stp + (6 / p) * ps, 0),
                    new Vector(c + 3 * stp +(6 % p) * ps, c + 2 * stp + (6 / p) * ps, 0),
                    new Vector(c           +(6 % p) * ps, c + 3 * stp + (6 / p) * ps, 0),
                    new Vector(c +     stp +(6 % p) * ps, c + 3 * stp + (6 / p) * ps, 0),
                    new Vector(c + 2 * stp +(6 % p) * ps, c + 3 * stp + (6 / p) * ps, 0),
                    new Vector(c + 3 * stp +(6 % p) * ps, c + 3 * stp + (6 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(7 % p) * ps, c +           (7 / p) * ps, 0),
                    new Vector(c +     stp +(7 % p) * ps, c +           (7 / p) * ps, 0),
                    new Vector(c + 2 * stp +(7 % p) * ps, c +           (7 / p) * ps, 0),
                    new Vector(c + 3 * stp +(7 % p) * ps, c +           (7 / p) * ps, 0),
                    new Vector(c           +(7 % p) * ps, c +     stp + (7 / p) * ps, 0),
                    new Vector(c +     stp +(7 % p) * ps, c +     stp + (7 / p) * ps, 0),
                    new Vector(c + 2 * stp +(7 % p) * ps, c +     stp + (7 / p) * ps, 0),
                    new Vector(c + 3 * stp +(7 % p) * ps, c +     stp + (7 / p) * ps, 0),
                    new Vector(c           +(7 % p) * ps, c + 2 * stp + (7 / p) * ps, 0),
                    new Vector(c +     stp +(7 % p) * ps, c + 2 * stp + (7 / p) * ps, 0),
                    new Vector(c + 2 * stp +(7 % p) * ps, c + 2 * stp + (7 / p) * ps, 0),
                    new Vector(c + 3 * stp +(7 % p) * ps, c + 2 * stp + (7 / p) * ps, 0),
                    new Vector(c           +(7 % p) * ps, c + 3 * stp + (7 / p) * ps, 0),
                    new Vector(c +     stp +(7 % p) * ps, c + 3 * stp + (7 / p) * ps, 0),
                    new Vector(c + 2 * stp +(7 % p) * ps, c + 3 * stp + (7 / p) * ps, 0),
                    new Vector(c + 3 * stp +(7 % p) * ps, c + 3 * stp + (7 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(8 % p) * ps, c +           (8 / p) * ps, 0),
                    new Vector(c +     stp +(8 % p) * ps, c +           (8 / p) * ps, 0),
                    new Vector(c + 2 * stp +(8 % p) * ps, c +           (8 / p) * ps, 0),
                    new Vector(c + 3 * stp +(8 % p) * ps, c +           (8 / p) * ps, 0),
                    new Vector(c           +(8 % p) * ps, c +     stp + (8 / p) * ps, 0),
                    new Vector(c +     stp +(8 % p) * ps, c +     stp + (8 / p) * ps, 0),
                    new Vector(c + 2 * stp +(8 % p) * ps, c +     stp + (8 / p) * ps, 0),
                    new Vector(c + 3 * stp +(8 % p) * ps, c +     stp + (8 / p) * ps, 0),
                    new Vector(c           +(8 % p) * ps, c + 2 * stp + (8 / p) * ps, 0),
                    new Vector(c +     stp +(8 % p) * ps, c + 2 * stp + (8 / p) * ps, 0),
                    new Vector(c + 2 * stp +(8 % p) * ps, c + 2 * stp + (8 / p) * ps, 0),
                    new Vector(c + 3 * stp +(8 % p) * ps, c + 2 * stp + (8 / p) * ps, 0),
                    new Vector(c           +(8 % p) * ps, c + 3 * stp + (8 / p) * ps, 0),
                    new Vector(c +     stp +(8 % p) * ps, c + 3 * stp + (8 / p) * ps, 0),
                    new Vector(c + 2 * stp +(8 % p) * ps, c + 3 * stp + (8 / p) * ps, 0),
                    new Vector(c + 3 * stp +(8 % p) * ps, c + 3 * stp + (8 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(9 % p) * ps, c +           (9 / p) * ps, 0),
                    new Vector(c +     stp +(9 % p) * ps, c +           (9 / p) * ps, 0),
                    new Vector(c + 2 * stp +(9 % p) * ps, c +           (9 / p) * ps, 0),
                    new Vector(c + 3 * stp +(9 % p) * ps, c +           (9 / p) * ps, 0),
                    new Vector(c           +(9 % p) * ps, c +     stp + (9 / p) * ps, 0),
                    new Vector(c +     stp +(9 % p) * ps, c +     stp + (9 / p) * ps, 0),
                    new Vector(c + 2 * stp +(9 % p) * ps, c +     stp + (9 / p) * ps, 0),
                    new Vector(c + 3 * stp +(9 % p) * ps, c +     stp + (9 / p) * ps, 0),
                    new Vector(c           +(9 % p) * ps, c + 2 * stp + (9 / p) * ps, 0),
                    new Vector(c +     stp +(9 % p) * ps, c + 2 * stp + (9 / p) * ps, 0),
                    new Vector(c + 2 * stp +(9 % p) * ps, c + 2 * stp + (9 / p) * ps, 0),
                    new Vector(c + 3 * stp +(9 % p) * ps, c + 2 * stp + (9 / p) * ps, 0),
                    new Vector(c           +(9 % p) * ps, c + 3 * stp + (9 / p) * ps, 0),
                    new Vector(c +     stp +(9 % p) * ps, c + 3 * stp + (9 / p) * ps, 0),
                    new Vector(c + 2 * stp +(9 % p) * ps, c + 3 * stp + (9 / p) * ps, 0),
                    new Vector(c + 3 * stp +(9 % p) * ps, c + 3 * stp + (9 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(10 % p) * ps, c +           (10 / p) * ps, 0),
                    new Vector(c +     stp +(10 % p) * ps, c +           (10 / p) * ps, 0),
                    new Vector(c + 2 * stp +(10 % p) * ps, c +           (10 / p) * ps, 0),
                    new Vector(c + 3 * stp +(10 % p) * ps, c +           (10 / p) * ps, 0),
                    new Vector(c           +(10 % p) * ps, c +     stp + (10 / p) * ps, 0),
                    new Vector(c +     stp +(10 % p) * ps, c +     stp + (10 / p) * ps, 0),
                    new Vector(c + 2 * stp +(10 % p) * ps, c +     stp + (10 / p) * ps, 0),
                    new Vector(c + 3 * stp +(10 % p) * ps, c +     stp + (10 / p) * ps, 0),
                    new Vector(c           +(10 % p) * ps, c + 2 * stp + (10 / p) * ps, 0),
                    new Vector(c +     stp +(10 % p) * ps, c + 2 * stp + (10 / p) * ps, 0),
                    new Vector(c + 2 * stp +(10 % p) * ps, c + 2 * stp + (10 / p) * ps, 0),
                    new Vector(c + 3 * stp +(10 % p) * ps, c + 2 * stp + (10 / p) * ps, 0),
                    new Vector(c           +(10 % p) * ps, c + 3 * stp + (10 / p) * ps, 0),
                    new Vector(c +     stp +(10 % p) * ps, c + 3 * stp + (10 / p) * ps, 0),
                    new Vector(c + 2 * stp +(10 % p) * ps, c + 3 * stp + (10 / p) * ps, 0),
                    new Vector(c + 3 * stp +(10 % p) * ps, c + 3 * stp + (10 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(11 % p) * ps, c +           (11 / p) * ps, 0),
                    new Vector(c +     stp +(11 % p) * ps, c +           (11 / p) * ps, 0),
                    new Vector(c + 2 * stp +(11 % p) * ps, c +           (11 / p) * ps, 0),
                    new Vector(c + 3 * stp +(11 % p) * ps, c +           (11 / p) * ps, 0),
                    new Vector(c           +(11 % p) * ps, c +     stp + (11 / p) * ps, 0),
                    new Vector(c +     stp +(11 % p) * ps, c +     stp + (11 / p) * ps, 0),
                    new Vector(c + 2 * stp +(11 % p) * ps, c +     stp + (11 / p) * ps, 0),
                    new Vector(c + 3 * stp +(11 % p) * ps, c +     stp + (11 / p) * ps, 0),
                    new Vector(c           +(11 % p) * ps, c + 2 * stp + (11 / p) * ps, 0),
                    new Vector(c +     stp +(11 % p) * ps, c + 2 * stp + (11 / p) * ps, 0),
                    new Vector(c + 2 * stp +(11 % p) * ps, c + 2 * stp + (11 / p) * ps, 0),
                    new Vector(c + 3 * stp +(11 % p) * ps, c + 2 * stp + (11 / p) * ps, 0),
                    new Vector(c           +(11 % p) * ps, c + 3 * stp + (11 / p) * ps, 0),
                    new Vector(c +     stp +(11 % p) * ps, c + 3 * stp + (11 / p) * ps, 0),
                    new Vector(c + 2 * stp +(11 % p) * ps, c + 3 * stp + (11 / p) * ps, 0),
                    new Vector(c + 3 * stp +(11 % p) * ps, c + 3 * stp + (11 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(12 % p) * ps, c +           (12 / p) * ps, 0),
                    new Vector(c +     stp +(12 % p) * ps, c +           (12 / p) * ps, 0),
                    new Vector(c + 2 * stp +(12 % p) * ps, c +           (12 / p) * ps, 0),
                    new Vector(c + 3 * stp +(12 % p) * ps, c +           (12 / p) * ps, 0),
                    new Vector(c           +(12 % p) * ps, c +     stp + (12 / p) * ps, 0),
                    new Vector(c +     stp +(12 % p) * ps, c +     stp + (12 / p) * ps, 0),
                    new Vector(c + 2 * stp +(12 % p) * ps, c +     stp + (12 / p) * ps, 0),
                    new Vector(c + 3 * stp +(12 % p) * ps, c +     stp + (12 / p) * ps, 0),
                    new Vector(c           +(12 % p) * ps, c + 2 * stp + (12 / p) * ps, 0),
                    new Vector(c +     stp +(12 % p) * ps, c + 2 * stp + (12 / p) * ps, 0),
                    new Vector(c + 2 * stp +(12 % p) * ps, c + 2 * stp + (12 / p) * ps, 0),
                    new Vector(c + 3 * stp +(12 % p) * ps, c + 2 * stp + (12 / p) * ps, 0),
                    new Vector(c           +(12 % p) * ps, c + 3 * stp + (12 / p) * ps, 0),
                    new Vector(c +     stp +(12 % p) * ps, c + 3 * stp + (12 / p) * ps, 0),
                    new Vector(c + 2 * stp +(12 % p) * ps, c + 3 * stp + (12 / p) * ps, 0),
                    new Vector(c + 3 * stp +(12 % p) * ps, c + 3 * stp + (12 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(13 % p) * ps, c +           (13 / p) * ps, 0),
                    new Vector(c +     stp +(13 % p) * ps, c +           (13 / p) * ps, 0),
                    new Vector(c + 2 * stp +(13 % p) * ps, c +           (13 / p) * ps, 0),
                    new Vector(c + 3 * stp +(13 % p) * ps, c +           (13 / p) * ps, 0),
                    new Vector(c           +(13 % p) * ps, c +     stp + (13 / p) * ps, 0),
                    new Vector(c +     stp +(13 % p) * ps, c +     stp + (13 / p) * ps, 0),
                    new Vector(c + 2 * stp +(13 % p) * ps, c +     stp + (13 / p) * ps, 0),
                    new Vector(c + 3 * stp +(13 % p) * ps, c +     stp + (13 / p) * ps, 0),
                    new Vector(c           +(13 % p) * ps, c + 2 * stp + (13 / p) * ps, 0),
                    new Vector(c +     stp +(13 % p) * ps, c + 2 * stp + (13 / p) * ps, 0),
                    new Vector(c + 2 * stp +(13 % p) * ps, c + 2 * stp + (13 / p) * ps, 0),
                    new Vector(c + 3 * stp +(13 % p) * ps, c + 2 * stp + (13 / p) * ps, 0),
                    new Vector(c           +(13 % p) * ps, c + 3 * stp + (13 / p) * ps, 0),
                    new Vector(c +     stp +(13 % p) * ps, c + 3 * stp + (13 / p) * ps, 0),
                    new Vector(c + 2 * stp +(13 % p) * ps, c + 3 * stp + (13 / p) * ps, 0),
                    new Vector(c + 3 * stp +(13 % p) * ps, c + 3 * stp + (13 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(14 % p) * ps, c +           (14 / p) * ps, 0),
                    new Vector(c +     stp +(14 % p) * ps, c +           (14 / p) * ps, 0),
                    new Vector(c + 2 * stp +(14 % p) * ps, c +           (14 / p) * ps, 0),
                    new Vector(c + 3 * stp +(14 % p) * ps, c +           (14 / p) * ps, 0),
                    new Vector(c           +(14 % p) * ps, c +     stp + (14 / p) * ps, 0),
                    new Vector(c +     stp +(14 % p) * ps, c +     stp + (14 / p) * ps, 0),
                    new Vector(c + 2 * stp +(14 % p) * ps, c +     stp + (14 / p) * ps, 0),
                    new Vector(c + 3 * stp +(14 % p) * ps, c +     stp + (14 / p) * ps, 0),
                    new Vector(c           +(14 % p) * ps, c + 2 * stp + (14 / p) * ps, 0),
                    new Vector(c +     stp +(14 % p) * ps, c + 2 * stp + (14 / p) * ps, 0),
                    new Vector(c + 2 * stp +(14 % p) * ps, c + 2 * stp + (14 / p) * ps, 0),
                    new Vector(c + 3 * stp +(14 % p) * ps, c + 2 * stp + (14 / p) * ps, 0),
                    new Vector(c           +(14 % p) * ps, c + 3 * stp + (14 / p) * ps, 0),
                    new Vector(c +     stp +(14 % p) * ps, c + 3 * stp + (14 / p) * ps, 0),
                    new Vector(c + 2 * stp +(14 % p) * ps, c + 3 * stp + (14 / p) * ps, 0),
                    new Vector(c + 3 * stp +(14 % p) * ps, c + 3 * stp + (14 / p) * ps, 0)  }
                    ,
                {   new Vector(c           +(15 % p) * ps, c +           (15 / p) * ps, 0),
                    new Vector(c +     stp +(15 % p) * ps, c +           (15 / p) * ps, 0),
                    new Vector(c + 2 * stp +(15 % p) * ps, c +           (15 / p) * ps, 0),
                    new Vector(c + 3 * stp +(15 % p) * ps, c +           (15 / p) * ps, 0),
                    new Vector(c           +(15 % p) * ps, c +     stp + (15 / p) * ps, 0),
                    new Vector(c +     stp +(15 % p) * ps, c +     stp + (15 / p) * ps, 0),
                    new Vector(c + 2 * stp +(15 % p) * ps, c +     stp + (15 / p) * ps, 0),
                    new Vector(c + 3 * stp +(15 % p) * ps, c +     stp + (15 / p) * ps, 0),
                    new Vector(c           +(15 % p) * ps, c + 2 * stp + (15 / p) * ps, 0),
                    new Vector(c +     stp +(15 % p) * ps, c + 2 * stp + (15 / p) * ps, 0),
                    new Vector(c + 2 * stp +(15 % p) * ps, c + 2 * stp + (15 / p) * ps, 0),
                    new Vector(c + 3 * stp +(15 % p) * ps, c + 2 * stp + (15 / p) * ps, 0),
                    new Vector(c           +(15 % p) * ps, c + 3 * stp + (15 / p) * ps, 0),
                    new Vector(c +     stp +(15 % p) * ps, c + 3 * stp + (15 / p) * ps, 0),
                    new Vector(c + 2 * stp +(15 % p) * ps, c + 3 * stp + (15 / p) * ps, 0),
                    new Vector(c + 3 * stp +(15 % p) * ps, c + 3 * stp + (15 / p) * ps, 0)  }
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
    
    public static Vector getPointSurface (double u, double v, int surface, Vector[][] cP) {
    
        Vector[] uCurve = new Vector[4];
        for (int i = 0; i < 4; i++)
            uCurve[i] = getPointBezier(u, cP[surface][0 + 4*i], cP[surface][1 + 4*i], cP[surface][2 + 4*i], cP[surface][3 + 4*i]); 
    
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
