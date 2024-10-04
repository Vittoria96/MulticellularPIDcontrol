function [t,x_stoc,tau]=gillespie(x,p,S,t)

% Calculate reaction propensities

a=zeros(p.Nt*7+p.Np*3+p.Ni*6+p.Nd*8+2,1);  % initilization of the propensiety functions

% Dimensions: number of target cells * targets' propensities dimension +
%             number of proportional cells * proportionals' propensities dimension +
%             number of integral cells * integrals' state propensities dimension +
%             number of derivative cells * derivatives' state propensities dimension +
%             number of external QS molecules

% compute targets propensities at each time step 

    for i=1:p.Nt

        r_idx_t=(i-1)*7+1:(i-1)*7+7;
        s_idx_t=(i-1)*4+1:(i-1)*4+4;


        a(r_idx_t)=targets_propensities(x(s_idx_t),p);


    end

% compute proportionals propensities at each time step

     for i=1:p.Np

        r_idx_p=p.Nt*7+((i-1)*3+1:(i-1)*3+3);
        s_idx_p=p.Nt*4+((i-1)*2+1:(i-1)*2+2);

        a(r_idx_p)=proportional_propensities(x(s_idx_p),p);

     end

% compute integrals propensities at each time step

     for i=1:p.Ni

        r_idx_i=p.Nt*7+p.Np*3+((i-1)*6+1:(i-1)*6+6);
        s_idx_i=p.Nt*4+p.Np*2+((i-1)*4+1:(i-1)*4+4);

        a(r_idx_i)=integral_propensities(x(s_idx_i),p);

     end

 % compute derivatives propensities at each time step

     for i=1:p.Nd

        r_idx_d=p.Nt*7+p.Np*3+p.Ni*6+((i-1)*8+1:(i-1)*8+8);
        s_idx_d=p.Nt*4+p.Np*2+p.Ni*4+((i-1)*4+1:(i-1)*4+4);

        a(r_idx_d)=derivative_propensities(x(s_idx_d),p);

     end

% compute external propensities at each time step

     a(end-1:end)=external_propensities(x(end-1:end),p);


% Sample earliest time-to-fire (tau)

    a0 = sum(a); 
    r = rand(1,2);
    tau = 1/a0*log(1/r(1)); 

% Sample identity of earliest reaction channel to fire (mu)

    j = find((cumsum(a) > r(2)*a0), 1,'first');
     
% Update time and carry out reaction mu
     t  = t  + tau;
     x_stoc = x + S(:,j);

end 