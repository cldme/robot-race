#version 120

uniform vec3 cameraPosition;
uniform vec3 sunPosition;
varying vec4 clipSpace;
varying vec3 toCameraVector;
varying vec3 fromLightVector;


void main()
{
    fromLightVector = gl_Vertex.xyz - sunPosition;
    toCameraVector = cameraPosition - gl_Vertex.xyz;
    clipSpace = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position    = clipSpace;
    gl_FrontColor = gl_Color;
}
