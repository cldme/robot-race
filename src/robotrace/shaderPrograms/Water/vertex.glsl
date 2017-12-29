#version 120


uniform vec4 plane;

void main()
{
    //vec4 worldPosition = gl_ModelMatrix

    //gl_ClipDistance[0] = 

    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_FrontColor = gl_Color;
}
