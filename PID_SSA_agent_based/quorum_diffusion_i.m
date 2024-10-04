function x=quorum_diffusion_i(x,p,Qxe,Que)

% Z1=x(1);
% Z2=x(2);
Qxi=x(3);
Qui=x(4);

x(1)=0;
x(2)=0;
x(3)=p.eta*(Qxe-Qxi);
x(4)=p.eta*(Que-Qui);


end