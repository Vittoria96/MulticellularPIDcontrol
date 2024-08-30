clc
close all
clear all

%parameters
bx=0.03;
g1=0.023;
bu=0.06;
bc=0.1;
gc=0.023;
eta=2;
g=0.023;
g_e=0.0023;
th=0.3;
mu=1; 
gz=0.01;


ba=1.5;
ga=1.5;
bm=5/12;
gm=1.5;
ka=1;
km=1;

%reference
Y=60;

N=120/4;

kd=0.01;

kp1=0.0002; 
ki1=0.00004;

kp2=kp1;
ki2=0.00004;

kp3=0.003;
ki3=0.00006;

kp4=1/3*(kd^2*th*bc+4*kd*g+g^2/(th*bc));
ki4=(2*g+th*bc*kd)^3/(27 *th* bc);

kp5=0.008;
ki5=0.5e-05;

kp_vec=[kp1,kp2,kp3,kp4,kp5];
kd_vec=kd*ones(5,1);
ki_vec=[ki1,ki2,ki3,ki4,ki5];


bp_vec=kp_vec*(bu* bx/(4 *(4* g)^2 *mu))^-1
bi_vec=ki_vec* (bu* bx/(4* g)^2)^-1
bd_vec=kd_vec* (bu* bx *ga/((4 *g)^2* ba *gm* th))^-1

colors=["#8ca3ff","#fc7dc2","#63e082","#d48fff"];

figure
for i=1:length(bi_vec)

    bi=bi_vec(i);
    bp=bp_vec(i);
    bd=bd_vec(i);

    fprintf(' bi: %3f \n',bi);

[t,x]=ode23s(@(t,x) PID_fun(x,g1,gc,g,bu,bc,eta,g_e,th,mu,gz,Y,bx,bi,bp,ba,ga,bm,gm,bd,ka,km,N), [0:2000], zeros(16,1));

 count = find(t>0);  % find all the t_values which is >1000 

    x = x(count,:);         % truncate the variables' vectors to select only the steady state
    t= t(count,:);

 

% figure

hold on
ref=200*ones(1,length(x(:,3)));
plot(t,ref,'--','LineWidth',1.5,'Color',"#808080")
xlabel("t [min]",FontSize=12)
ylabel("Q_x^t [nM]",FontSize=12)
grid on
hold on
plot(t,x(:,3),'LineWidth',2)
legend('ref',['\beta_P=',num2str(bp),',\beta_D=', num2str(bd),',\beta_I=',num2str(bi)],'FontSize',10)
%ylim([0 300])
% 
% figure
% plot3(x(:,1),x(:,2),x(:,3),'LineWidth',1.5,'Color',colors(i))
% box on
% xlabel("X_1 [nM]")
% ylabel("X_c [nM]")
% zlabel("Q_x^t [nM]")


end



