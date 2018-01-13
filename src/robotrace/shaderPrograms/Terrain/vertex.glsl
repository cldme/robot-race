#version 130
varying vec3 sunPosition;
uniform vec4 plane;

void main()
{
    vec4 worldPosition = gl_Vertex;
    gl_ClipDistance[0] = dot(worldPosition, plane);
    vec3 N = gl_Normal;
    vec3 finalLight = vec3(0,0,0);
    
    for (int light = 0; light < 2; light++) {

    vec3 toLight = normalize(gl_LightSource[light].position.xyz - gl_Vertex.xyz);
    
    
    finalLight += gl_Color.xyz * gl_LightSource[light].diffuse.xyz * max(dot(toLight, N), 0.25);
    }


    gl_FrontColor = vec4(finalLight,1);
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}
