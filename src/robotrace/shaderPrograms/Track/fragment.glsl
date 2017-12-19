#version 120
varying vec3 N;
varying vec3 P;

vec4 shading(vec3 P, vec3 N) {

	vec4 result = vec4(0,0,0,1);
	//result += vec4(0.0, 0.4, 0.3, 1.0);}

	vec3 L = normalize(P - gl_LightSource[0].position.xyz);
	result += gl_LightSource[0].diffuse * max(0,dot(N, L));

	vec3 E = vec3(0);
	vec3 V = normalize(E - P);
	vec3 R = reflect(-L, N);
	result += gl_LightSource[0].specular * max(0, dot(R,V));

return result;

}

void main()
{

    gl_FragColor = shading(P, N);

	
}
