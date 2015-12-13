#version 330 core

layout (location = 0) out vec4 color;

in DATA {
	vec2 tc;
	vec3 position;
} fs_in;

uniform sampler2D tex;
uniform int click = 0;
uniform vec2 cent;


void main() 
{
	color = texture(tex, fs_in.tc);
	color.w *= 2.0 / (length(cent - fs_in.position.xy) + 2.5) + 2.0 ;	


	if(click == 1)
	color *= 2.0;
	color.w = 1.0;
}