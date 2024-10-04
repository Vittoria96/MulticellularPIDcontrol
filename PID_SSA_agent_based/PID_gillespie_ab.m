clc; clear; close;

%% %%%%%%  System parameters  %%%%%%%%%

run("Parameters.m")

% number of cells in each population

p.Nt=30;
p.Np=30;
p.Ni=30;
p.Nd=30;


%% %%%%%% Initialization  %%%%%%%%%

t0=0;       % initial simulation time
tf=2000;    % final simulation time


x0_t=ones(4*p.Nt,1)*1;  % initial targets'state
x0_p=ones(2*p.Np,1)*1;  % initial proportionals' state
x0_i=ones(4*p.Ni,1)*1;  % initial proportionals' state
x0_d=ones(4*p.Nd,1)*1;  % initial derivatives' state
x0_e=ones(2,1)*1;       % initial external QS' state

% States' first dimension: number of cells * cells' state vector dimension


%% %%%%%% Stoichiometrix matrix  %%%%%%%%%

run("Stoichiometric_matrix.m")


%% Stochastic simulations 


n=2;   % number of simulations

for k=1:n
    
% initial state

x=[x0_t;x0_p;x0_i;x0_d;x0_e];

% output state vector of each simulation containing the state vectors of all
% cells in the consortium 

x_out=NaN(100000,p.Nt*4+p.Np*2+p.Ni*4+p.Nd*4+2);  % initialization
x_out(1,:)=x;

% initial time 
t=t0;

% output time vector of each simulation 

t_out=NaN(100000,1);   % initialization
t_out(1)=t;

cnt=2;                   % counter

% initialization for the displayed messages

maxLength=0;
msg='';
tp=0;

% select random targets' parameters  with CV=0.1

p.bc=0.1;
p.bu=0.06;
p.bx=0.03;

% random parameters

CV=0.1;

p.bc=p.bc+p.bc*CV*randn(1,1); 
p.bu=p.bu+p.bu*CV*randn(1,1);
p.bx=p.bx+p.bx*CV*randn(1,1);



fprintf('Simulation number %d \n',k)


while t < tf   

    maxLength = length(msg); % Update the max length with the current message length
    fprintf([repmat('\b', 1, maxLength)]); % Erase the previous line using backspaces
    msg = sprintf('Time step %.2f \n', t);
    fprintf('%s', msg); % Print the new message 

    
    %%%%%% Stochastic part %%%%%%
    
    [t,x_stoc,tau]=gillespie(x,p,S,t);

    %%%%%% Deterministic part %%%%%%
  
    x_det=deterministic(x,p);
        
    % Update the state and the counter
  
     x=x_stoc+x_det*tau;

    % Update the output state vector at each dt=0.1
     
     if t>=tp+0.1 
   
     x_out(cnt,:)=x';
     t_out(cnt)=t;
     cnt=cnt+1;
     tp=t;

     end

end

% save the time and ouput vectors of each simulation 

fieldName = sprintf('PID_x_out_%d', k);

Xn.(fieldName) = x_out;
Tn.(fieldName) = t_out;

end