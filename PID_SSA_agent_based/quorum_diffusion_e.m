function dx=quorum_diffusion_e(x,p,qx,qu)

dx=zeros(2,1);

Qxe=x(1);
Que=x(2);

dx(1)=p.eta*sum(qx-Qxe);
dx(2)=p.eta*sum(qu-Que);


end