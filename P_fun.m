function xp=P_fun(x,bu,g1,gc,g,bc,bx,eta,bp,mu,th,g_e,Y,N)

xp=zeros(8,1);


%target
xp(1)= bu*x(4)-g1*x(1);  %x(1)=x1
xp(2)= bc*x(1)-gc*x(2); %x(2)=xc

%target quorum sensing
xp(3)=bx*x(2)+eta*(x(7)-x(3))-g*x(3); %x(3)=qxt
xp(4)=eta*(x(8)-x(4))-g*x(4);         %x(4)=qut

%P quorum sensing
xp(5)=eta*(x(7)-x(5))-g*x(5);         %x(5)=qxp
xp(6)=bp*Y*mu*Y/(mu*Y+th*x(5))+eta*(x(8)-x(6))-g*x(6);         %x(6)=qup

%external environment quorum sensing
xp(7)=N*(eta*(x(3)-x(7))+eta*(x(5)-x(7)))-g_e*x(7);      %x(7)=qxe
xp(8)=N*(eta*(x(4)-x(8))+eta*(x(6)-x(8)))-g_e*x(8);        %x(8)=que




end
