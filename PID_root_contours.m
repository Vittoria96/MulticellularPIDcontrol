clear all
close all
clc

th=0.3;
bc=0.1;
g=0.023;
ga=1.5;
mu=1;
ba=1.5;
bm=5/12; 

lim_kp=g^2/(2*th*bc);
im_inf=-sqrt(bc* lim_kp* th); %minimum imaginary part of the starting points
im_sup=sqrt(bc* lim_kp* th);  %maximum imaginary part of the starting points


y_bars_inf=[-0.035 im_inf];
y_bars_sup=[im_sup 0.035];

figure

% patch([-0.0235 -0.0225 -0.0225 -0.0235], [y_bars_inf(1) y_bars_inf(1), y_bars_inf(2) y_bars_inf(2)],[1.00,1.00,0.71],...
%     'EdgeColor','none','FaceAlpha',0.5)
% hold on
% patch([-0.0235 -0.0225 -0.0225 -0.0235], [y_bars_sup(1) y_bars_sup(1), y_bars_sup(2) y_bars_sup(2)],[1.00,1.00,0.71],...
%     'EdgeColor','none','FaceAlpha',0.5)
% hold on


s=tf('s');

% %% Proportional root locus %%
% 
%kp_vec=[0.00022,0.003,0.00618544,0.008];

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

kp_vec=[kp1,kp3,kp4,kp5];
kd_vec=kd*ones(5,1);
ki_vec=[ki1,ki2;ki3,ki3;ki4, ki4;ki5, ki5];

for i=1:length(kp_vec)

    kp=kp_vec(i);

G=th*bc/(s^2+2*g*s+g^2);
% G=th*bc/((g^2 + bc *kp* th)*s^2+2*g*s*sqrt(g^2 + bc* kp* th)+g^2);


[R,K]=rlocus(G);

% figure

for m = 1:numel(R)/length(R)
plot(R(m,:),'-.','Color',[0,0,0],'LineWidth',1)
% hold on
% plot(real(R(m,1)),imag(R(m,1)),'*','MarkerSize',10,'Color',[0,0,0],'LineWidth',1)
end

hold on
end 

%% PD normalized root locus %%

for i=1:length(kp_vec)

    kp=kp_vec(i);

    kd_lim=ga* mu* (g^2 - 2 *bc* kp* th)/(ba* bc *bm* th^2);

G=(bc*s* th)/(g^2 + bc* kp* th + 2* g* s + s^2);
% G=(bc*s*sqrt(g^2 + bc* kp* th)* th)/(g^2 + bc* kp* th + 2* g* s*sqrt(g^2 + bc* kp* th) + s^2*(g^2 + bc* kp* th));

[R,K]=rlocus(G);
[R_lim,K_lim]=rlocus(G,kd_lim);
% figure

for m = 1:numel(R)/length(R)
plot(real(R(m,:)),imag(R(m,:)),'-.','Color',[0,0,0],'LineWidth',1)
hold on
plot(real(R(m,1)),imag(R(m,1)),'*','MarkerSize',3,'Color',[0,0,0],'LineWidth',0.5)
hold on
plot(0,0,'o','MarkerSize',10,'Color',[0,0,0],'LineWidth',1)
hold on
% 
end

 end





%% PID normalized Root contours %%

kd=0.01;

 
colors=["#03b3b3","#59e0a6","#59e0a6","#d4ff00"];
sizes=[15,10,10,8];
widths=[2.5,2,2,1.5];
styles={'-','-.','-.','--'};
mark_styles={'x','*','*','x'};

hold on

for i=1:length(kp_vec)

    kp=kp_vec(i);
    
    G=th*bc/(s^3+(2*g+bc* kd* th)*s^2+(g^2+kp*th*bc)*s);
    % G=th*bc/(s^3*sqrt(g^2 + bc* kp* th)^(3/2)+(2*g+bc* kd* th)*(g^2 + bc* kp* th)*s^2+(g^2+kp*th*bc)^(3/2)*s);


[R,K]=rlocus(G);

style=styles{i};
color=colors(i);
width=widths(i);
mark_style=mark_styles{i};
size=sizes(i);

hold on

for m =  1:numel(R)/length(R)

p(i)=plot(real(R(m,:)),imag(R(m,:)),style,'Color',color,'LineWidth',width);

end

hold on 


plot(real(R(2:3,1)),imag(R(2:3,1)),'x','MarkerSize',10,'Color',color,'LineWidth',1.5)

hold on

plot(0,0,mark_style,'MarkerSize',size,'Color',color,'LineWidth',width)

% Selected gains

ki=ki_vec(i,:);
r=rlocus(G,ki);

plot(real(r),imag(r),'*','MarkerSize',10,'LineWidth',1.5)


end

set(gca,'Layer','top')

xline(0,':')
yline(0,':')

 %  xlim([-1.5 0.5])
 % ylim([-1.5 1.5])

xlim([-0.03 0.001])
ylim([-0.03 0.03])

xlabel('Real axis [min^-^1]','FontSize',12)
ylabel('Imaginary axis [min^-^1]','FontSize',12)

box on
set(gca,'LineWidth',1,'FontSize',11)

legend([p(1) p(2) p(3) p(4)],['k_P=',num2str(round(kp_vec(1),4))],['k_P=',num2str(round(kp_vec(2),4))],['k_P=',num2str(round(kp_vec(3),4))],['k_P=',num2str(round(kp_vec(4),4))],'FontSize',10,'Location','southwest')
