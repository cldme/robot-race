#version 120

uniform vec3 cameraPosition;
varying vec4 clipSpace;
varying vec3 toCameraVector;

void main()
{

    toCameraVector = normalize(vec3(gl_ModelViewMatrix * vec4(cameraPosition,1)) - vec3(gl_ModelViewMatrix * gl_Vertex));
    clipSpace = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position    = clipSpace;
    gl_FrontColor = gl_Color;
}
