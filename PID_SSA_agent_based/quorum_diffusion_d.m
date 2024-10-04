function x=quorum_diffusion_d(x,p,Qxe,Que)

A=x(1);
M=x(2);
Qxd=x(3);
Qud=x(4);

x(1)=0;
x(2)=0;
x(3)=p.eta*(Qxe-Qxd);
x(4)=p.eta*(Que-Qud);


end