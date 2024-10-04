function x=quorum_diffusion_p(x,p,Qxe,Que)

Qxp=x(1);
Qup=x(2);

x(1)=p.eta*(Qxe-Qxp);
x(2)=p.eta*(Que-Qup);


end