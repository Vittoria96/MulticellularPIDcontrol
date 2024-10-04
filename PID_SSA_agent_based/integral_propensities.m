function a=integral_propensities(x,p)

Z1=x(1);
Z2=x(2);
Qxi=x(3);
Qui=x(4);

a(1)=p.mu*p.Y;
a(2)=p.gz*Z1*Z2;
a(3)=p.th*Qxi;
a(4)=p.g*Qxi;
a(5)=p.bi*Z1;
a(6)=p.g*Qui;


end