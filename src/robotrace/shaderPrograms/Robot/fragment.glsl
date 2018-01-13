#version 120

varying vec3 P;
varying vec3 N;
varying vec3 V;

vec4 shading(vec3 P, vec3 N) {

    vec4 result = vec4(0,0,0,1);
    
    vec3 view = normalize(V);

    for (int light = 0; light < 1; light++) {
        vec3 L = normalize(gl_LightSource[light].position.xyz);
        vec3 R = reflect(-L, N);
        result += gl_FrontMaterial.diffuse * gl_LightSource[light].diffuse * max(0.35,dot(N, L));
        result += gl_FrontMaterial.specular * gl_LightSource[light].specular * pow(max(0, dot(R,view)), gl_FrontMaterial.shininess);

    }

return result;

}

void main()
{

    gl_FragColor = shading(P, N);

}