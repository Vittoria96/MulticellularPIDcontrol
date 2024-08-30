function xp=PI_fun(x,g1,gc,g,bu,bc,eta,g_e,th,mu,gz,Y,bx,bi,bp,N)

xp=zeros(12,1);

%target
xp(1)=-g1*x(1)+bu*x(4);  %x(1)=x1
xp(2)= bc*x(1)-gc*x(2); %x(2)=xc

%target quorum sensing
xp(3)=bx*x(2)+eta*(x(11)-x(3))-g*x(3); %x(3)=qxt
xp(4)=eta*(x(12)-x(4))-g*x(4);         %x(4)=qut

%P quorum sensing
xp(5)=eta*(x(11)-x(5))-g*x(5);         %x(5)=qxp
xp(6)=bp*Y*mu*Y/(mu*Y+th*x(5))+eta*(x(12)-x(6))-g*x(6);         %x(6)=qup

%controller I
xp(7)=mu*Y-gz*x(7)*x(8);                            %x(7)=z1
xp(8)=th*x(9)-gz*x(7)*x(8);                            %x(8)=z2

%I quorum sensing
xp(9)=eta*(x(11)-x(9))-g*x(9);         %x(9)=qxi
xp(10)=bi*x(7)+eta*(x(12)-x(10))-g*x(10);         %x(10)=qui

%external environment quorum sensing
xp(11)=N*eta*((x(3)-x(11))+(x(5)-x(11))+(x(9)-x(11)))-g_e*x(11);      %x(11)=qxe
xp(12)=N*eta*((x(4)-x(12))+(x(6)-x(12))+(x(10)-x(12)))-g_e*x(12);        %x(12)=que



end