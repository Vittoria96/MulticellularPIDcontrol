function ae=external_propensities(x,p)

ae=zeros(2,1);

Qxe=x(1); 
Que=x(2);

ae(1)=p.ge*Qxe;               
ae(2)=p.ge*Que;           

end