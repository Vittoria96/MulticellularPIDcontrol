function xp=PD_fun(x,bu,bc,g1,gc,g,bx,eta,bp,mu,Y,th,ba,ga,ka,bm,gm,km,bd,N,g_e)

xp=zeros(12,1);

%target
xp(1)=-g1*x(1)+bu*x(4);  %x(1)=x1
xp(2)= bc*x(1)-gc*x(2);  %x(2)=xc

%target quorum sensing
xp(3)=bx*x(2)+eta*(x(11)-x(3))-g*x(3); %x(3)=qxt
xp(4)=eta*(x(12)-x(4))-g*x(4);         %x(4)=qut

%P quorum sensing
xp(5)=eta*(x(11)-x(5))-g*x(5);                                     %x(5)=qxp
xp(6)=bp*Y*mu*Y/(mu*Y+th*x(5))+eta*(x(12)-x(6))-g*x(6);         %x(6)=qup


%controller D
xp(7)=ba*x(8)-ga*x(9)*x(7)/(ka+x(7))-g*x(7);        %x(7)=a
xp(8)=bm*Y-gm*x(7)*x(8)/(km+x(8));                  %x(8)=m

%D quorum sensing
xp(9)=eta*(x(11)-x(9))-g*x(9);                    %x(9)=qxd
xp(10)=bd*x(7)+eta*(x(12)-x(10))-g*x(10);         %x(10)=qud

%external environment quorum sensing
xp(11)=N*(eta*(x(3)-x(11))+eta*(x(5)-x(11))+eta*(x(9)-x(11)))-g_e*x(11);      %x(11)=qxe
xp(12)=N*(eta*(x(4)-x(12))+eta*(x(6)-x(12))+eta*(x(10)-x(12)))-g_e*x(12);     %x(12)=que



end
