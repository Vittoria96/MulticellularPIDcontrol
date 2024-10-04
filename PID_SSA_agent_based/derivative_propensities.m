function a=derivative_propensities(x,p)

A=x(1);
M=x(2);
Qxd=x(3);
Qud=x(4);

a(1)=p.ba*M;
a(2)=p.ga*Qxd*A/(p.ka+A);
a(3)=p.g*A;
a(4)=p.bm*p.Y;
a(5)=p.gm*A*M/(p.km+M);
a(6)=p.g*Qxd;
a(7)=p.bd*A;
a(8)=p.g*Qud;

end