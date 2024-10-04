function x=quorum_diffusion_t(x,p,Qxe,Que)

% X1=x(1);
% Xc=x(2);
Qxt=x(3);
Qut=x(4);

x(1)=0;
x(2)=0;
x(3)=p.eta*(Qxe-Qxt);
x(4)=p.eta*(Que-Qut);

end