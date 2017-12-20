#version 120
varying vec3 N;
varying vec3 P;

vec4 shading(vec3 P, vec3 N) {

	vec4 result = vec4(0,0,0,1);
	result += gl_LightSource[0].ambient;

	vec3 L = -normalize(gl_LightSource[0].position.xyz);
	result += gl_LightSource[0].diffuse * max(0,dot(N, L));

	vec3 V = normalize(-P);
	vec3 R = reflect(L, N);
	result += gl_LightSource[0].specular * pow(max(0, dot(R,V)), 10);

return result;

}

void main()
{

    gl_FragColor = shading(P, N);

	
}
