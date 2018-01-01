#version 130
varying vec3 sunPosition;
uniform vec4 plane;

void main()
{
    vec4 worldPosition = gl_Vertex;
    gl_ClipDistance[0] = dot(worldPosition, plane);
    vec3 N = gl_Normal;
    vec3 toLight = normalize(gl_LightSource[0].position.xyz - gl_Vertex.xyz);

    gl_FrontColor = vec4(vec3(gl_Color * max(dot(toLight, N), 0.25)),1);
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
