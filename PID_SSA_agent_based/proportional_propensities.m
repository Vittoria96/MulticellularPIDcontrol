function ap=proportional_propensities(x,p)

Qxp=x(1);
Qup=x(2);

ap(1)=p.g*Qxp;
ap(2)=p.bp*p.Y*p.mu*p.Y/(p.mu*p.Y+p.th*Qxp);
ap(3)=p.g*Qup;

end