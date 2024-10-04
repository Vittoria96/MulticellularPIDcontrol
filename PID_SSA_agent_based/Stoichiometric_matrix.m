%% compute the stoichiometric matrix of each type of cell %%

% targets stoichiometric matrix

St=zeros(4,7);

St(1,1)=1; St(1,2)=-1;
St(2,3)=1; St(2,4)=-1;
St(3,5)=1; St(3,6)=-1;
St(4,7)=-1;

% proportionals stoichiometric matrix

Sp=zeros(2,3);

Sp(1,1)=-1;
Sp(2,2)=1; Sp(2,3)=-1;

% integrals stoichiometric matrix

Si=zeros(4,6);

Si(1,1)=1; Si(1,2)=-1;  
Si(2,3)=1; Si(2,2)=-1;
Si(3,4)=-1;
Si(4,5)=1; Si(4,6)=-1;

% derivatives stoichiometric matrix

Sd=zeros(4,8);

Sd(1,1)=1; Sd(1,2)=-1; Sd(1,3)=-1; 
Sd(2,4)=1; Sd(2,5)=-1;
Sd(3,6)=-1;
Sd(4,7)=1; Sd(4,8)=-1;

% External Qs' stoichiometric matrix

Se=zeros(2,2);

Se(1,1)=-1;
Se(2,2)=-1;

%% total stoichiometric matrix

S=zeros(p.Nt*4+p.Np*2+p.Ni*4+p.Nd*4+2,p.Nt*7+p.Np*3+p.Ni*6+p.Nd*8+2);

% Dimension 1: number of target cells * targets' state vector dimension +
%             number of proportional cells * proportionals' state vector dimension +
%             number of integral cells * integrals' state vector dimension +
%             number of derivative cells * derivatives' state vector dimension +
%             number of external QS molecules

% Dimension 2: number of target cells * targets' propensities dimension +
%             number of proportional cells * proportionals' propensities dimension +
%             number of integral cells * integrals' state propensities dimension +
%             number of derivative cells * derivatives' state propensities dimension +
%             number of external QS molecules


% Assigning the values to the total stoichiometric matrix

% Target values 

for i=1:p.Nt

    r_idx_t=(i-1)*4+1:(i-1)*4+4;
    c_idx_t=(i-1)*7+1:(i-1)*7+7;

    S(r_idx_t,c_idx_t)=St;

end

% Proportionals values

for i=1:p.Np
    
    r_idx_p=p.Nt*4+((i-1)*2+1:(i-1)*2+2);
    c_idx_p=p.Nt*7+((i-1)*3+1:(i-1)*3+3);

    S(r_idx_p,c_idx_p)=Sp;

end

% Integrals values  

for i=1:p.Ni
    
    r_idx_i=p.Nt*4+p.Np*2+((i-1)*4+1:(i-1)*4+4);
    c_idx_i=p.Nt*7+p.Np*3+((i-1)*6+1:(i-1)*6+6);

    S(r_idx_i,c_idx_i)=Si;

end

% Derivatives values

for i=1:p.Nd
    
    r_idx_d=p.Nt*4+p.Np*2+p.Ni*4+((i-1)*4+1:(i-1)*4+4);
    c_idx_d=p.Nt*7+p.Np*3+p.Ni*6+((i-1)*8+1:(i-1)*8+8);

    S(r_idx_d,c_idx_d)=Sd;

end

% External QS values 

S(end-1:end,end-1:end)=Se;
