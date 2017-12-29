package robotrace;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class TerrainUtility {
    
    private final static int P = 4; // PATCHES PER SIDE
    private final static double PS = 40d / (double)P; // PATCH_SIDE
    private final static int SUBDIVISIONS = 24;
    private final static double S_C = 40d /(double)(P * 3);
    private final static double C = 20d; // used for corner reference
    private final static double S_V = 1d / SUBDIVISIONS;
    private final static double spread = 1f;
    private final static double halfSpread = spread / 2d;
    private final static Color[] colors = 
            
            new Color[] {
                    new Color(245, 217, 78), // Yellow
                    new Color(107, 153, 62), // Light Green
                    new Color(24, 89, 46), // Green
                    new Color(71, 56, 15), // Brown
                    new Color(220, 220, 255)  // White
                    
                };
    private final static double part = 1d / (colors.length - 1);
    
    private final static Vector[][] bezierSurfaces =
            new Vector[][] {
                
                {   new Vector(-C          , C,           0),
                    new Vector(-C +     S_C, C,           0),
                    new Vector(-C + 2 * S_C, C,           0),
                    new Vector(-C + 3 * S_C, C,           0),
                    new Vector(-C          , C -     S_C, 0),
                    new Vector(-C +     S_C, C -     S_C, 0),
                    new Vector(-C + 2 * S_C, C -     S_C, 0),
                    new Vector(-C + 3 * S_C, C -     S_C, 0),
                    new Vector(-C          , C - 2 * S_C, 0),
                    new Vector(-C +     S_C, C - 2 * S_C, 0),
                    new Vector(-C + 2 * S_C, C - 2 * S_C, 0),
                    new Vector(-C + 3 * S_C, C - 2 * S_C, 0),
                    new Vector(-C          , C - 3 * S_C, 0),
                    new Vector(-C +     S_C, C - 3 * S_C, 0),
                    new Vector(-C + 2 * S_C, C - 3 * S_C, 0),
                    new Vector(-C + 3 * S_C, C - 3 * S_C, 0)  }
                    ,
                {   new Vector(-C           + PS, C          , 0),
                    new Vector(-C +     S_C + PS, C          , 0),
                    new Vector(-C + 2 * S_C + PS, C          , 0),
                    new Vector(-C + 3 * S_C + PS, C          , 0),
                    new Vector(-C           + PS, C -     S_C, 0),
                    new Vector(-C +     S_C + PS, C -     S_C, 0),
                    new Vector(-C + 2 * S_C + PS, C -     S_C, 0),
                    new Vector(-C + 3 * S_C + PS, C -     S_C, 0),
                    new Vector(-C           + PS, C - 2 * S_C, 0),
                    new Vector(-C +     S_C + PS, C - 2 * S_C, 0),
                    new Vector(-C + 2 * S_C + PS, C - 2 * S_C, 0),
                    new Vector(-C + 3 * S_C + PS, C - 2 * S_C, 0),
                    new Vector(-C           + PS, C - 3 * S_C, 0),
                    new Vector(-C +     S_C + PS, C - 3 * S_C, 0),
                    new Vector(-C + 2 * S_C + PS, C - 3 * S_C, 0),
                    new Vector(-C + 3 * S_C + PS, C - 3 * S_C, 0)  }
                    ,
                {   new Vector(-C           +(2 % P) * PS, C -           (2 / P) * PS, 0),
                    new Vector(-C +     S_C +(2 % P) * PS, C -           (2 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(2 % P) * PS, C -           (2 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(2 % P) * PS, C -           (2 / P) * PS, 0),
                    new Vector(-C           +(2 % P) * PS, C -     S_C - (2 / P) * PS, 0),
                    new Vector(-C +     S_C +(2 % P) * PS, C -     S_C - (2 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(2 % P) * PS, C -     S_C - (2 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(2 % P) * PS, C -     S_C - (2 / P) * PS, 0),
                    new Vector(-C           +(2 % P) * PS, C - 2 * S_C - (2 / P) * PS, 0),
                    new Vector(-C +     S_C +(2 % P) * PS, C - 2 * S_C - (2 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(2 % P) * PS, C - 2 * S_C - (2 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(2 % P) * PS, C - 2 * S_C - (2 / P) * PS, 0),
                    new Vector(-C           +(2 % P) * PS, C - 3 * S_C - (2 / P) * PS, 0),
                    new Vector(-C +     S_C +(2 % P) * PS, C - 3 * S_C - (2 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(2 % P) * PS, C - 3 * S_C - (2 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(2 % P) * PS, C - 3 * S_C - (2 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(3 % P) * PS, C -           (3 / P) * PS, 0),
                    new Vector(-C +     S_C +(3 % P) * PS, C -           (3 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(3 % P) * PS, C -           (3 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(3 % P) * PS, C -           (3 / P) * PS, 0),
                    new Vector(-C           +(3 % P) * PS, C -     S_C - (3 / P) * PS, 0),
                    new Vector(-C +     S_C +(3 % P) * PS, C -     S_C - (3 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(3 % P) * PS, C -     S_C - (3 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(3 % P) * PS, C -     S_C - (3 / P) * PS, 0),
                    new Vector(-C           +(3 % P) * PS, C - 2 * S_C - (3 / P) * PS, 0),
                    new Vector(-C +     S_C +(3 % P) * PS, C - 2 * S_C - (3 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(3 % P) * PS, C - 2 * S_C - (3 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(3 % P) * PS, C - 2 * S_C - (3 / P) * PS, 0),
                    new Vector(-C           +(3 % P) * PS, C - 3 * S_C - (3 / P) * PS, 0),
                    new Vector(-C +     S_C +(3 % P) * PS, C - 3 * S_C - (3 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(3 % P) * PS, C - 3 * S_C - (3 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(3 % P) * PS, C - 3 * S_C - (3 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(4 % P) * PS, C -           (4 / P) * PS, 0),
                    new Vector(-C +     S_C +(4 % P) * PS, C -           (4 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(4 % P) * PS, C -           (4 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(4 % P) * PS, C -           (4 / P) * PS, 0),
                    new Vector(-C           +(4 % P) * PS, C -     S_C - (4 / P) * PS, 0),
                    new Vector(-C +     S_C +(4 % P) * PS, C -     S_C - (4 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(4 % P) * PS, C -     S_C - (4 / P) * PS, 55),
                    new Vector(-C + 3 * S_C +(4 % P) * PS, C -     S_C - (4 / P) * PS, 55),
                    new Vector(-C           +(4 % P) * PS, C - 2 * S_C - (4 / P) * PS, 0),
                    new Vector(-C +     S_C +(4 % P) * PS, C - 2 * S_C - (4 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(4 % P) * PS, C - 2 * S_C - (4 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(4 % P) * PS, C - 2 * S_C - (4 / P) * PS, 0),
                    new Vector(-C           +(4 % P) * PS, C - 3 * S_C - (4 / P) * PS, 0),
                    new Vector(-C +     S_C +(4 % P) * PS, C - 3 * S_C - (4 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(4 % P) * PS, C - 3 * S_C - (4 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(4 % P) * PS, C - 3 * S_C - (4 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(5 % P) * PS, C -           (5 / P) * PS, 0),
                    new Vector(-C +     S_C +(5 % P) * PS, C -           (5 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(5 % P) * PS, C -           (5 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(5 % P) * PS, C -           (5 / P) * PS, 0),
                    new Vector(-C           +(5 % P) * PS, C -     S_C - (5 / P) * PS, 55),
                    new Vector(-C +     S_C +(5 % P) * PS, C -     S_C - (5 / P) * PS, 55),
                    new Vector(-C + 2 * S_C +(5 % P) * PS, C -     S_C - (5 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(5 % P) * PS, C -     S_C - (5 / P) * PS, 0),
                    new Vector(-C           +(5 % P) * PS, C - 2 * S_C - (5 / P) * PS, 0),
                    new Vector(-C +     S_C +(5 % P) * PS, C - 2 * S_C - (5 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(5 % P) * PS, C - 2 * S_C - (5 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(5 % P) * PS, C - 2 * S_C - (5 / P) * PS, 0),
                    new Vector(-C           +(5 % P) * PS, C - 3 * S_C - (5 / P) * PS, 0),
                    new Vector(-C +     S_C +(5 % P) * PS, C - 3 * S_C - (5 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(5 % P) * PS, C - 3 * S_C - (5 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(5 % P) * PS, C - 3 * S_C - (5 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(6 % P) * PS, C -           (6 / P) * PS, 0),
                    new Vector(-C +     S_C +(6 % P) * PS, C -           (6 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(6 % P) * PS, C -           (6 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(6 % P) * PS, C -           (6 / P) * PS, 0),
                    new Vector(-C           +(6 % P) * PS, C -     S_C - (6 / P) * PS, 0),
                    new Vector(-C +     S_C +(6 % P) * PS, C -     S_C - (6 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(6 % P) * PS, C -     S_C - (6 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(6 % P) * PS, C -     S_C - (6 / P) * PS, 0),
                    new Vector(-C           +(6 % P) * PS, C - 2 * S_C - (6 / P) * PS, 0),
                    new Vector(-C +     S_C +(6 % P) * PS, C - 2 * S_C - (6 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(6 % P) * PS, C - 2 * S_C - (6 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(6 % P) * PS, C - 2 * S_C - (6 / P) * PS, 0),
                    new Vector(-C           +(6 % P) * PS, C - 3 * S_C - (6 / P) * PS, 0),
                    new Vector(-C +     S_C +(6 % P) * PS, C - 3 * S_C - (6 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(6 % P) * PS, C - 3 * S_C - (6 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(6 % P) * PS, C - 3 * S_C - (6 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(7 % P) * PS, C -           (7 / P) * PS, 0),
                    new Vector(-C +     S_C +(7 % P) * PS, C -           (7 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(7 % P) * PS, C -           (7 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(7 % P) * PS, C -           (7 / P) * PS, 0),
                    new Vector(-C           +(7 % P) * PS, C -     S_C - (7 / P) * PS, 0),
                    new Vector(-C +     S_C +(7 % P) * PS, C -     S_C - (7 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(7 % P) * PS, C -     S_C - (7 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(7 % P) * PS, C -     S_C - (7 / P) * PS, 0),
                    new Vector(-C           +(7 % P) * PS, C - 2 * S_C - (7 / P) * PS, 0),
                    new Vector(-C +     S_C +(7 % P) * PS, C - 2 * S_C - (7 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(7 % P) * PS, C - 2 * S_C - (7 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(7 % P) * PS, C - 2 * S_C - (7 / P) * PS, 0),
                    new Vector(-C           +(7 % P) * PS, C - 3 * S_C - (7 / P) * PS, 0),
                    new Vector(-C +     S_C +(7 % P) * PS, C - 3 * S_C - (7 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(7 % P) * PS, C - 3 * S_C - (7 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(7 % P) * PS, C - 3 * S_C - (7 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(8 % P) * PS, C -           (8 / P) * PS, 0),
                    new Vector(-C +     S_C +(8 % P) * PS, C -           (8 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(8 % P) * PS, C -           (8 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(8 % P) * PS, C -           (8 / P) * PS, 0),
                    new Vector(-C           +(8 % P) * PS, C -     S_C - (8 / P) * PS, 0),
                    new Vector(-C +     S_C +(8 % P) * PS, C -     S_C - (8 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(8 % P) * PS, C -     S_C - (8 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(8 % P) * PS, C -     S_C - (8 / P) * PS, 0),
                    new Vector(-C           +(8 % P) * PS, C - 2 * S_C - (8 / P) * PS, 0),
                    new Vector(-C +     S_C +(8 % P) * PS, C - 2 * S_C - (8 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(8 % P) * PS, C - 2 * S_C - (8 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(8 % P) * PS, C - 2 * S_C - (8 / P) * PS, 0),
                    new Vector(-C           +(8 % P) * PS, C - 3 * S_C - (8 / P) * PS, 0),
                    new Vector(-C +     S_C +(8 % P) * PS, C - 3 * S_C - (8 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(8 % P) * PS, C - 3 * S_C - (8 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(8 % P) * PS, C - 3 * S_C - (8 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(9 % P) * PS, C -           (9 / P) * PS, 0),
                    new Vector(-C +     S_C +(9 % P) * PS, C -           (9 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(9 % P) * PS, C -           (9 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(9 % P) * PS, C -           (9 / P) * PS, 0),
                    new Vector(-C           +(9 % P) * PS, C -     S_C - (9 / P) * PS, 0),
                    new Vector(-C +     S_C +(9 % P) * PS, C -     S_C - (9 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(9 % P) * PS, C -     S_C - (9 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(9 % P) * PS, C -     S_C - (9 / P) * PS, 0),
                    new Vector(-C           +(9 % P) * PS, C - 2 * S_C - (9 / P) * PS, 0),
                    new Vector(-C +     S_C +(9 % P) * PS, C - 2 * S_C - (9 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(9 % P) * PS, C - 2 * S_C - (9 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(9 % P) * PS, C - 2 * S_C - (9 / P) * PS, 0),
                    new Vector(-C           +(9 % P) * PS, C - 3 * S_C - (9 / P) * PS, 0),
                    new Vector(-C +     S_C +(9 % P) * PS, C - 3 * S_C - (9 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(9 % P) * PS, C - 3 * S_C - (9 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(9 % P) * PS, C - 3 * S_C - (9 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(10 % P) * PS, C -           (10 / P) * PS, 0),
                    new Vector(-C +     S_C +(10 % P) * PS, C -           (10 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(10 % P) * PS, C -           (10 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(10 % P) * PS, C -           (10 / P) * PS, 0),
                    new Vector(-C           +(10 % P) * PS, C -     S_C - (10 / P) * PS, 0),
                    new Vector(-C +     S_C +(10 % P) * PS, C -     S_C - (10 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(10 % P) * PS, C -     S_C - (10 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(10 % P) * PS, C -     S_C - (10 / P) * PS, 0),
                    new Vector(-C           +(10 % P) * PS, C - 2 * S_C - (10 / P) * PS, 0),
                    new Vector(-C +     S_C +(10 % P) * PS, C - 2 * S_C - (10 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(10 % P) * PS, C - 2 * S_C - (10 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(10 % P) * PS, C - 2 * S_C - (10 / P) * PS, 0),
                    new Vector(-C           +(10 % P) * PS, C - 3 * S_C - (10 / P) * PS, 0),
                    new Vector(-C +     S_C +(10 % P) * PS, C - 3 * S_C - (10 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(10 % P) * PS, C - 3 * S_C - (10 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(10 % P) * PS, C - 3 * S_C - (10 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(11 % P) * PS, C -           (11 / P) * PS, 0),
                    new Vector(-C +     S_C +(11 % P) * PS, C -           (11 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(11 % P) * PS, C -           (11 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(11 % P) * PS, C -           (11 / P) * PS, 0),
                    new Vector(-C           +(11 % P) * PS, C -     S_C - (11 / P) * PS, 0),
                    new Vector(-C +     S_C +(11 % P) * PS, C -     S_C - (11 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(11 % P) * PS, C -     S_C - (11 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(11 % P) * PS, C -     S_C - (11 / P) * PS, 0),
                    new Vector(-C           +(11 % P) * PS, C - 2 * S_C - (11 / P) * PS, 0),
                    new Vector(-C +     S_C +(11 % P) * PS, C - 2 * S_C - (11 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(11 % P) * PS, C - 2 * S_C - (11 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(11 % P) * PS, C - 2 * S_C - (11 / P) * PS, 0),
                    new Vector(-C           +(11 % P) * PS, C - 3 * S_C - (11 / P) * PS, 0),
                    new Vector(-C +     S_C +(11 % P) * PS, C - 3 * S_C - (11 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(11 % P) * PS, C - 3 * S_C - (11 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(11 % P) * PS, C - 3 * S_C - (11 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(12 % P) * PS, C -           (12 / P) * PS, 0),
                    new Vector(-C +     S_C +(12 % P) * PS, C -           (12 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(12 % P) * PS, C -           (12 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(12 % P) * PS, C -           (12 / P) * PS, 0),
                    new Vector(-C           +(12 % P) * PS, C -     S_C - (12 / P) * PS, 0),
                    new Vector(-C +     S_C +(12 % P) * PS, C -     S_C - (12 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(12 % P) * PS, C -     S_C - (12 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(12 % P) * PS, C -     S_C - (12 / P) * PS, 0),
                    new Vector(-C           +(12 % P) * PS, C - 2 * S_C - (12 / P) * PS, 0),
                    new Vector(-C +     S_C +(12 % P) * PS, C - 2 * S_C - (12 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(12 % P) * PS, C - 2 * S_C - (12 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(12 % P) * PS, C - 2 * S_C - (12 / P) * PS, 0),
                    new Vector(-C           +(12 % P) * PS, C - 3 * S_C - (12 / P) * PS, 0),
                    new Vector(-C +     S_C +(12 % P) * PS, C - 3 * S_C - (12 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(12 % P) * PS, C - 3 * S_C - (12 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(12 % P) * PS, C - 3 * S_C - (12 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(13 % P) * PS, C -           (13 / P) * PS, 0),
                    new Vector(-C +     S_C +(13 % P) * PS, C -           (13 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(13 % P) * PS, C -           (13 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(13 % P) * PS, C -           (13 / P) * PS, 0),
                    new Vector(-C           +(13 % P) * PS, C -     S_C - (13 / P) * PS, 0),
                    new Vector(-C +     S_C +(13 % P) * PS, C -     S_C - (13 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(13 % P) * PS, C -     S_C - (13 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(13 % P) * PS, C -     S_C - (13 / P) * PS, 0),
                    new Vector(-C           +(13 % P) * PS, C - 2 * S_C - (13 / P) * PS, 0),
                    new Vector(-C +     S_C +(13 % P) * PS, C - 2 * S_C - (13 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(13 % P) * PS, C - 2 * S_C - (13 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(13 % P) * PS, C - 2 * S_C - (13 / P) * PS, 0),
                    new Vector(-C           +(13 % P) * PS, C - 3 * S_C - (13 / P) * PS, 0),
                    new Vector(-C +     S_C +(13 % P) * PS, C - 3 * S_C - (13 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(13 % P) * PS, C - 3 * S_C - (13 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(13 % P) * PS, C - 3 * S_C - (13 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(14 % P) * PS, C -           (14 / P) * PS, 0),
                    new Vector(-C +     S_C +(14 % P) * PS, C -           (14 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(14 % P) * PS, C -           (14 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(14 % P) * PS, C -           (14 / P) * PS, 0),
                    new Vector(-C           +(14 % P) * PS, C -     S_C - (14 / P) * PS, 0),
                    new Vector(-C +     S_C +(14 % P) * PS, C -     S_C - (14 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(14 % P) * PS, C -     S_C - (14 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(14 % P) * PS, C -     S_C - (14 / P) * PS, 0),
                    new Vector(-C           +(14 % P) * PS, C - 2 * S_C - (14 / P) * PS, 0),
                    new Vector(-C +     S_C +(14 % P) * PS, C - 2 * S_C - (14 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(14 % P) * PS, C - 2 * S_C - (14 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(14 % P) * PS, C - 2 * S_C - (14 / P) * PS, 0),
                    new Vector(-C           +(14 % P) * PS, C - 3 * S_C - (14 / P) * PS, 0),
                    new Vector(-C +     S_C +(14 % P) * PS, C - 3 * S_C - (14 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(14 % P) * PS, C - 3 * S_C - (14 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(14 % P) * PS, C - 3 * S_C - (14 / P) * PS, 0)  }
                    ,
                {   new Vector(-C           +(15 % P) * PS, C -           (15 / P) * PS, 0),
                    new Vector(-C +     S_C +(15 % P) * PS, C -           (15 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(15 % P) * PS, C -           (15 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(15 % P) * PS, C -           (15 / P) * PS, 0),
                    new Vector(-C           +(15 % P) * PS, C -     S_C - (15 / P) * PS, 0),
                    new Vector(-C +     S_C +(15 % P) * PS, C -     S_C - (15 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(15 % P) * PS, C -     S_C - (15 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(15 % P) * PS, C -     S_C - (15 / P) * PS, 0),
                    new Vector(-C           +(15 % P) * PS, C - 2 * S_C - (15 / P) * PS, 0),
                    new Vector(-C +     S_C +(15 % P) * PS, C - 2 * S_C - (15 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(15 % P) * PS, C - 2 * S_C - (15 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(15 % P) * PS, C - 2 * S_C - (15 / P) * PS, 0),
                    new Vector(-C           +(15 % P) * PS, C - 3 * S_C - (15 / P) * PS, 0),
                    new Vector(-C +     S_C +(15 % P) * PS, C - 3 * S_C - (15 / P) * PS, 0),
                    new Vector(-C + 2 * S_C +(15 % P) * PS, C - 3 * S_C - (15 / P) * PS, 0),
                    new Vector(-C + 3 * S_C +(15 % P) * PS, C - 3 * S_C - (15 / P) * PS, 0)  }
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
    
    public static Vector getPointSurfaceUV (double u, double v, int surface, Vector[][] cP) {
        //System.out.println("u is " + u + " and v is " + v + " so the surface is " + (int)(u) + " + " + (int)(v) * 4 + " (" + surface + ")");

        if (u >= P) {
            u = 1d;
        } else {
            u = u - Math.floor(u);
        }
        
        if (v >= P) {
            v = 1d;
        } else {
            v = v - Math.floor(v);
        }
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
    
    public static double[][] generateHeights () {
        
        double heights[][] = new double[SUBDIVISIONS * P + 1][SUBDIVISIONS * P + 1];
	for (int z = 0; z < heights.length; z++) {
            for (int x = 0; x < heights[z].length; x++) {
		heights[z][x] = getPointSurfaceUV(x * S_V, z * S_V, getPatchUV(x * S_V, z * S_V), bezierSurfaces).z();
            }
	}
        return heights;
    }
    
//    public static Vector[] generateNormals () {
//    
//    
//    
//    
//    
//    
//    }
//    
//    public Vector calcNormal (int i0, int i1, int i2) {
//    
//        Vector v0 = vertices[i0];
//        Vector v1 = vertices[i1];
//        Vector v2 = vertices[i2];
//        
//        Vector tangentA = v1.subtract(v0);
//        Vector tangentB = v2.subtract(v0);
//        Vector normal = tangentA.cross(tangentB).normalized();
//    
//        return normal;
//    }
    
    public static int getPatchUV (double u, double v) {
        if (u >= P)
            u = P-1;
        if (v >= P)
            v = P-1;
        return (int)u + (int)v * P;
    }
    
    public static Vector[][] getSurface() {
        return bezierSurfaces;
    }
    
    public static Vector[] getPatch(int patch) {
        return bezierSurfaces[patch];
    }
    
    public static Vector getVectorInPatch(int surface, int idx) {    
        return bezierSurfaces[surface][idx];
    }
    
    public static int getSubdivisions () {
        return SUBDIVISIONS;
    }
    
    public static int getPatches () {
        return P;
    }
    
    public static Vector[] storeVertexPositions () {
    
        int s = P * SUBDIVISIONS + 1;
        
        Vector[] positions = new Vector[s * s];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = getPointSurfaceUV((i % s) * S_V, (i / s) * S_V, getPatchUV((i % s) * S_V, (i / s) * S_V), bezierSurfaces);
        }
        
        return positions;
    }

    public static Color[] generateColors (Vector [] vertices, float amplitude) {

        int s = P * SUBDIVISIONS + 1;
        
        Color[] colorsArr = new Color[s * s];
        for (int i = 0; i < vertices.length; i++) {
            colorsArr[i] = calculateColor(vertices[i].z(), amplitude);
        }
        
        return colorsArr;
    }
    
    private static Color calculateColor(double height, float amplitude) {
            double value = (height + amplitude) / (amplitude * 2);
            value = Clamp009999((value - halfSpread) * (1d / spread));
            int firstBiome = (int) Math.floor(value / part);
            double blend = (value - (firstBiome * part)) / part;
        return Interpolate(colors[firstBiome], colors[firstBiome + 1], blend);
    }
    
    private static Color Interpolate (Color first, Color second, double t) {
        float[] compsA = new float[3];
        float[] compsB = new float[3];
        first.getColorComponents(compsA);
        second.getColorComponents(compsB);
        double r = compsA[0] * (1 - t) + compsB[0] * t;
        double g = compsA[1] * (1 - t) + compsB[1] * t;
        double b = compsA[2] * (1 - t) + compsB[2] * t;
        return new Color ((float)r,(float)g,(float)b);
    }
    
    private static double Clamp009999 (double value) {
    
        if (value < 0) { return 0; }
        else if (value > 0.9999d) { return 0.9999d; }
        return value;
        
    }
}
