function at=targets_propensities(x,p)

at=zeros(7,1);

X1=x(1);
Xc=x(2);
Qxt=x(3);
Qut=x(4);


at(1)=p.bu*Qut;
at(2)=p.g*X1;
at(3)=p.bc*X1;
at(4)=p.g*Xc;
at(5)=p.bx*Xc;
at(6)=p.g*Qxt;
at(7)=p.g*Qut;

end