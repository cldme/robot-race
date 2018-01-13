#version 120
varying vec3 N;
varying vec3 P;
varying vec3 V;
uniform vec3 cameraPosition;
varying vec4 VaryingTexCoord0;
uniform sampler2D tex;

vec4 shading(vec3 P, vec3 N) {

    vec4 result = vec4(0,0,0,1);
    
    vec3 view = normalize(V);

    for (int light = 0; light < 2; light++) {
        vec3 L = normalize(gl_LightSource[light].position.xyz);
        vec3 R = reflect(-L, N);
        result += gl_LightSource[light].diffuse * max(0,dot(N, L));
        result += gl_LightSource[light].specular * pow(max(0, dot(R,view)), 250);

    }

return result/3;

}

void main()
{

    gl_FragColor = shading(P, N) + texture2D(tex, VaryingTexCoord0.st); //vec4(VaryingTexCoord0.st - floor(VaryingTexCoord0.st),0,1);  //

	
}
