clc 
close all 
clear

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

N=120;


%control gains
bp=(8* g^4 *mu)/(bc* bu *bx* th);
bd = -1/2*(gm *(bp - (18* g^4* mu)/(bc* bu* bx* th)))/bm;
bi=0.0002;


ti=1;
tf=2000;
delta_t=20;  %time of cell division
t=1:1:2001;

% number of simulations

n=10;

% random parameters
CV=0.1;
bcr=bc+bc*CV*randn(1,10); 
bur=bu+bu*CV*randn(1,10);
bxr=bx+bx*CV*randn(1,10);

%% Proportional %%

% % CV=0.05 %
% 
% CV=0.1;
% 
% for j=1:n
% 
%     xP0=zeros(8,1);
% 
% %for i=1:delta_t:tf
% 
%     % g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     % gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tP,xP]=ode15s(@(tP,xP) P_fun(xP,bur,g1,gc,g,bcr,bxr,eta,bp,mu,th,g_e,Y,N), [ti,tf], xP0);
% 
% 
%     %xP(i:i+delta_t,:)=xiP;
% 
%     %xiP0=xiP(end,:);
% %end
%     fprintf("P: CV= %3f n= %3f \n",CV,j)
% 
%     xP_05(:,j)=xP(:,3);
% 
% 
%     ta_index_05_P=find(xP_05(:,j) >= 220 | xP_05(:,j) <= 180, 1, 'last');
% 
%     ta_05_P(j)=t(1,ta_index_05_P);
% 
% 
% if ta_05_P(j)<1441
% 
%     succ_05_P(j)=1;
%     insucc_05_P(j)=0;
% else 
% 
%     succ_05_P(j)=0;
%     insucc_05_P(j)=1;
% end
% end

% CV=0.1 %

CV=0.1;

for j=1:n

    xP0=zeros(8,1);

% for i=1:delta_t:tf

    % % g1r=g1+g1*CV*randn(1);  
    % bcr=bc+bc*CV*randn(1); 
    % % gcr=gc+gc*CV*randn(1); 
	% bur=bu+bu*CV*randn(1);
    % bxr=bx+bx*CV*randn(1);
	

    [tP,xP]=ode15s(@(tP,xP) P_fun(xP,bur(j),g1,gc,g,bcr(j),bxr(j),eta,bp,mu,th,g_e,Y,N), ti:tf, xP0);
    

    % xP(i:i+delta_t,:)=xiP;

    % xiP0=xiP(end,:);
% end
    fprintf("P: CV= %3f n= %3f \n",CV,j)
    xP_1(:,j)=xP(:,3);
  
    ta_index_1_P=find(xP_1(:,j) >= xP_1(end,j)+0.1*xP_1(end,j) | xP_1(:,j) <= xP_1(end,j)-0.1*xP_1(end,j), 1, 'last');
    ta_1_P(j)=t(1,ta_index_1_P);

    o_peak_P=xP_1(find(diff(xP_1(:,j))<0,1,'first'),j);   %finds the first peak

    if isempty(o_peak_P)

        o_P(j)=0

    else
        
        o_P(j)=(o_peak_P-xP_1(end,j))/xP_1(end,j)*100;

    end

% percentage error

e_1_P(j)=abs(mean(xP_1(round(1440):end,j)-200))/200*100;


if ta_1_P(j)<1441

    succ_1_P(j)=1;
    insucc_1_P(j)=0;
else 

    succ_1_P(j)=0;
    insucc_1_P(j)=1;
end
 end


% CV=0.15 %

% CV=0.15;
% 
% for j=1:n
% 
%     xiP0=zeros(8,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiP,xiP]=ode15s(@(tiP,xiP) P_fun(xiP,bur,g1r,gcr,g,bcr,bxr,eta,bp,mu,th,g_e,Y,N), [i:1:i+delta_t], xiP0);
% 
% 
%     xP(i:i+delta_t,:)=xiP;
% 
%     xiP0=xiP(end,:);
% end
%     fprintf("P: CV= %3f n= %3f \n",CV,j)
%     xP_15(:,j)=xP(:,3);
% 
% 
%     ta_index_15_P=find(xP_15(:,j) >= 220 | xP_15(:,j) <= 180, 1, 'last');
% 
%     ta_15_P(j)=t(1,ta_index_15_P);
% 
% 
% if ta_15_P(j)<1441
% 
%     succ_15_P(j)=1;
%     insucc_15_P(j)=0;
% else 
% 
%     succ_15_P(j)=0;
%     insucc_15_P(j)=1;
% end
% end
% 
% % CV=0.2 %
% 
% CV=0.2;
% 
% for j=1:n
% 
%     xiP0=zeros(8,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiP,xiP]=ode15s(@(tiP,xiP) P_fun(xiP,bur,g1r,gcr,g,bcr,bxr,eta,bp,mu,th,g_e,Y,N), [i:1:i+delta_t], xiP0);
% 
% 
%     xP(i:i+delta_t,:)=xiP;
% 
%     xiP0=xiP(end,:);%% PD %%
% end
%     fprintf("P: CV= %3f n= %3f \n",CV,j)
%     xP_2(:,j)=xP(:,3);
% 
%     ta_index_2_P=find(xP_2(:,j) >= 220 | xP_2(:,j) <= 180, 1, 'last');
% 
%     ta_2_P(j)=t(1,ta_index_2_P);
% 
% 
% if ta_2_P(j)<1441
% 
%     succ_2_P(j)=1;
%     insucc_2_P(j)=0;
% else 
% 
%     succ_2_P(j)=0;
%     insucc_2_P(j)=1;
% end
% 
% end
% 

%% PD %%

% CV=0.05 %

% CV=0.05;
% 
% for j=1:n
% 
%     xiPD0=zeros(12,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPD,xiPD]=ode15s(@(tiPD,xiPD) PD_fun(xiPD,bur,bcr,g1r,gcr,g,bxr,eta,bp,mu,Y,th,ba,ga,ka,bm,gm,km,bd,N,g_e), [i:1:i+delta_t], xiPD0);
% 
% 
%     xPD(i:i+delta_t,:)=xiPD;
% 
%     xiPD0=xiPD(end,:);
% end
%     fprintf("PD: CV= %3f n= %3f \n",CV,j)
%     xPD_05(:,j)=xPD(:,3);
% 
% 
%     ta_index_05_PD=find(xPD_05(:,j) >= 220 | xPD_05(:,j) <= 180, 1, 'last');
% 
%     ta_05_PD(j)=t(1,ta_index_05_PD);
% 
% 
% if ta_05_PD(j)<1441
% 
%     succ_05_PD(j)=1;
%     insucc_05_PD(j)=0;
% else 
% 
%     succ_05_PD(j)=0;
%     insucc_05_PD(j)=1;
% end
% end

% CV=0.1 %

CV=0.1;

for j=1:n

    xPD0=zeros(12,1);

% for i=1:delta_t:tf

    % % g1r=g1+g1*CV*randn(1);  
    % bcr=bc+bc*CV*randn(1); 
    % % gcr=gc+gc*CV*randn(1); 
	% bur=bu+bu*CV*randn(1);
    % bxr=bx+bx*CV*randn(1);
	

    [tPD,xPD]=ode15s(@(tPD,xPD) PD_fun(xPD,bur(j),bcr(j),g1,gc,g,bxr(j),eta,bp,mu,Y,th,ba,ga,ka,bm,gm,km,bd,N,g_e), ti:tf, xPD0);
    

    % xPD(i:i+delta_t,:)=xiPD;
    % 
    % xiPD0=xiPD(end,:);
% end
    fprintf("PD: CV= %3f n= %3f \n",CV,j)
    xPD_1(:,j)=xPD(:,3);

        ta_index_1_PD=find(xPD_1(:,j) >= xPD_1(end,j)+0.1*xPD_1(end,j)| xPD_1(:,j) <= xPD_1(end,j)-0.1*xPD_1(end,j), 1, 'last');
        ta_1_PD(j)=t(1,ta_index_1_PD);

        o_peak_PD=xPD_1(find(diff(xPD_1(:,j))<0,1,'first'),j);   %finds the first peak
        

        if isempty(o_peak_PD)

            o_PD(j)=0;

        else

            o_PD(j)=(o_peak_PD-xPD_1(end,j))/xPD_1(end,j)*100;

        end


% percentage error
e_1_PD(j)=abs(mean(xPD_1(round(1440):end,j)-200))/200*100;

if ta_1_PD(j)<1441

    succ_1_PD(j)=1;
    insucc_1_PD(j)=0;
else 

    succ_1_PD(j)=0;
    insucc_1_PD(j)=1;
end
end


% % CV=0.15 %
% 
% CV=0.15;
% 
% for j=1:n
% 
%     xiPD0=zeros(12,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPD,xiPD]=ode15s(@(tiPD,xiPD) PD_fun(xiPD,bur,bcr,g1r,gcr,g,bxr,eta,bp,mu,Y,th,ba,ga,ka,bm,gm,km,bd,N,g_e), [i:1:i+delta_t], xiPD0);
% 
% 
%     xPD(i:i+delta_t,:)=xiPD;
% 
%     xiPD0=xiPD(end,:);
% end
%     fprintf("PD: CV= %3f n= %3f \n",CV,j)
%     xPD_15(:,j)=xPD(:,3);
% 
%         ta_index_15_PD=find(xPD_15(:,j) >= 220 | xPD_15(:,j) <= 180, 1, 'last');
% 
%     ta_15_PD(j)=t(1,ta_index_15_PD);
% 
% 
% if ta_15_PD(j)<1441
% 
%     succ_15_PD(j)=1;
%     insucc_15_PD(j)=0;
% else 
% 
%     succ_15_PD(j)=0;
%     insucc_15_PD(j)=1;
% end
% end
% 
% % CV=0.2 %
% 
% CV=0.2;
% 
% for j=1:n
% 
%     xiPD0=zeros(12,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPD,xiPD]=ode15s(@(tiPD,xiPD)PD_fun(xiPD,bur,bcr,g1r,gcr,g,bxr,eta,bp,mu,Y,th,ba,ga,ka,bm,gm,km,bd,N,g_e), [i:1:i+delta_t], xiPD0);
% 
% 
%     xPD(i:i+delta_t,:)=xiPD;
% 
%     xiPD0=xiPD(end,:);
% end
%     fprintf("PD: CV= %3f n= %3f \n",CV,j)
%     xPD_2(:,j)=xPD(:,3);
% 
%         ta_index_2_PD=find(xPD_2(:,j) >= 220 | xPD_2(:,j) <= 180, 1, 'last');
% 
%     ta_2_PD(j)=t(1,ta_index_2_PD);
% 
% 
% if ta_2_PD(j)<1441
% 
%     succ_2_PD(j)=1;
%     insucc_2_PD(j)=0;
% else 
% 
%     succ_2_PD(j)=0;
%     insucc_2_PD(j)=1;
% end
% end


%% PI %%

% CV=0.05 %

% CV=0.05;
% 
% for j=1:n
% 
%     xiPI0=zeros(12,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPI,xiPI]=ode15s(@(tiPI,xiPI) PI_fun(xiPI,g1r,gcr,g,bur,bcr,eta,g_e,th,mu,gz,Y,bxr,bi,bp,N), [i:1:i+delta_t], xiPI0);
% 
% 
%     xPI(i:i+delta_t,:)=xiPI;
% 
%     xiPI0=xiPI(end,:);
% end
%     fprintf("PI: CV= %3f n= %3f \n",CV,j)
%     xPI_05(:,j)=xPI(:,3);
% 
%         ta_index_05_PI=find(xPI_05(:,j) >= 220 | xPI_05(:,j) <= 180, 1, 'last');
% 
%     ta_05_PI(j)=t(1,ta_index_05_PI);
% 
% 
% if ta_05_PI(j)<1441
% 
%     succ_05_PI(j)=1;
%     insucc_05_PI(j)=0;
% else 
% 
%     succ_05_PI(j)=0;
%     insucc_05_PI(j)=1;
% end
% end

% CV=0.1 %

CV=0.1;

 for j=1:n

    xPI0=zeros(12,1);

% for i=1:delta_t:tf

    % % g1r=g1+g1*CV*randn(1);  
    % bcr=bc+bc*CV*randn(1); 
    % % gcr=gc+gc*CV*randn(1); 
	% bur=bu+bu*CV*randn(1);
    % bxr=bx+bx*CV*randn(1);
	

    [tPI,xPI]=ode15s(@(tPI,xPI) PI_fun(xPI,g1,gc,g,bur(j),bcr(j),eta,g_e,th,mu,gz,Y,bxr(j),bi,bp,N), ti:tf, xPI0);
    

%     xPI(i:i+delta_t,:)=xiPI;
% 
%     xiPI0=xiPI(end,:);
% end
    fprintf("PI: CV= %3f n= %3f \n",CV,j)
    xPI_1(:,j)=xPI(:,3);

      ta_index_1_PI=find(xPI_1(:,j) >= xPI_1(end,j)+0.1*xPI_1(end,j) | xPI_1(:,j) <= xPI_1(end,j)-0.1*xPI_1(end,j), 1, 'last');
      ta_1_PI(j)=t(1,ta_index_1_PI);

      o_peak_PI=xPI_1(find(diff(xPI_1(:,j))<0,1,'first'),j);   %finds the first peak
      
      if isempty(o_peak_PI)

            o_PI(j)=0;

        else

            o_PI(j)=(o_peak_PI-xPI_1(end,j))/xPI_1(end,j)*100;

      end

      % percentage error
e_1_PI(j)=abs(mean(xPI_1(round(1440):end,j)-200))/200*100;

if ta_1_PI(j)<1441

    succ_1_PI(j)=1;
    insucc_1_PI(j)=0;
else 

    succ_1_PI(j)=0;
    insucc_1_PI(j)=1;
end
end


% % CV=0.15 %
% 
% CV=0.15;
% 
% for j=1:n
% 
%     xiPI0=zeros(12,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPI,xiPI]=ode15s(@(tiPI,xiPI) PI_fun(xiPI,g1r,gcr,g,bur,bcr,eta,g_e,th,mu,gz,Y,bxr,bi,bp,N), [i:1:i+delta_t], xiPI0);
% 
% 
%     xPI(i:i+delta_t,:)=xiPI;
% 
%     xiPI0=xiPI(end,:);
% end
%     fprintf("PI: CV= %3f n= %3f \n",CV,j)
%     xPI_15(:,j)=xPI(:,3);
% 
%       ta_index_15_PI=find(xPI_15(:,j) >= 220 | xPI_15(:,j) <= 180, 1, 'last');
% 
%     ta_15_PI(j)=t(1,ta_index_15_PI);
% 
% 
% if ta_15_PI(j)<1441
% 
%     succ_15_PI(j)=1;
%     insucc_15_PI(j)=0;
% else 
% 
%     succ_15_PI(j)=0;
%     insucc_15_PI(j)=1;
% end
% end
% 
% % CV=0.2 %
% 
% CV=0.2;
% 
% for j=1:n
% 
%     xiPI0=zeros(12,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPI,xiPI]=ode15s(@(tiPI,xiPI) PI_fun(xiPI,g1r,gcr,g,bur,bcr,eta,g_e,th,mu,gz,Y,bxr,bi,bp,N), [i:1:i+delta_t], xiPI0);
% 
% 
%     xPI(i:i+delta_t,:)=xiPI;
% 
%     xiPI0=xiPI(end,:);
% end
%     fprintf("PI: CV= %3f n= %3f \n",CV,j)
%     xPI_2(:,j)=xPI(:,3);
% 
%       ta_index_2_PI=find(xPI_2(:,j) >= 220 | xPI_2(:,j) <= 180, 1, 'last');
% 
%     ta_2_PI(j)=t(1,ta_index_2_PI);
% 
% 
% if ta_2_PI(j)<1441
% 
%     succ_2_PI(j)=1;
%     insucc_2_PI(j)=0;
% else 
% 
%     succ_2_PI(j)=0;
%     insucc_2_PI(j)=1;
% end
% end



%% PID %%

% CV=0.05 %

% CV=0.05;
% 
% for j=1:n
% 
%     xiPID0=zeros(16,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPID,xiPID]=ode15s(@(tiPID,xiPID) PID_fun(xiPID,g1r,gcr,g,bur,bcr,eta,g_e,th,mu,gz,Y,bxr,bi,bp,ba,ga,bm,gm,bd,ka,km,N), [i:1:i+delta_t], xiPID0);
% 
% 
%     xPID(i:i+delta_t,:)=xiPID;
% 
%     xiPID0=xiPID(end,:);
% end
%     fprintf("PID: CV= %3f n= %3f \n",CV,j)
%     xPID_05(:,j)=xPID(:,3);
% 
%       ta_index_05_PID=find(xPID_05(:,j) >= 220 | xPID_05(:,j) <= 180, 1, 'last');
% 
%     ta_05_PID(j)=t(1,ta_index_05_PID);
% 
% 
% if ta_05_PID(j)<1441
% 
%     succ_05_PID(j)=1;
%     insucc_05_PID(j)=0;
% else 
% 
%     succ_05_PID(j)=0;
%     insucc_05_PID(j)=1;
% end
% end

% CV=0.1 %

CV=0.1;

for j=1:n

    xPID0=zeros(16,1);

% for i=1:delta_t:tf

    % % g1r=g1+g1*CV*randn(1);  
    % bcr=bc+bc*CV*randn(1); 
    % % gcr=gc+gc*CV*randn(1); 
	% bur=bu+bu*CV*randn(1);
    % bxr=bx+bx*CV*randn(1);
	

    [tPID,xPID]=ode15s(@(tPID,xPID) PID_fun(xPID,g1,gc,g,bur(j),bcr(j),eta,g_e,th,mu,gz,Y,bxr(j),bi,bp,ba,ga,bm,gm,bd,ka,km,N), ti:tf, xPID0);
    

%     xPID(i:i+delta_t,:)=xiPID;
% 
%     xiPID0=xiPID(end,:);
% end
    fprintf("PID: CV= %3f n= %3f \n",CV,j)
    xPID_1(:,j)=xPID(:,3);


      ta_index_1_PID=find(xPID_1(:,j) >= xPID_1(end,j)+0.1*xPID_1(end,j) | xPID_1(:,j) <= xPID_1(end,j)-0.1*xPID_1(end,j), 1, 'last');
      ta_1_PID(j)=t(1,ta_index_1_PID);

      o_peak_PID=xPID_1(find(diff(xPID_1(:,j))<0,1,'first'),j);   %finds the first peak
      
      if isempty(o_peak_PID)

          o_PID(j)=0;

      else

          o_PID(j)=(o_peak_PID-xPID_1(end,j))/xPID_1(end,j)*100;

      end

% percentage error
e_1_PID(j)=abs(mean(xPID_1(round(1440):end,j)-200))/200*100;

if ta_1_PID(j)<1441

    succ_1_PID(j)=1;
    insucc_1_PID(j)=0;
else 

    succ_1_PID(j)=0;
    insucc_1_PID(j)=1;
end
end


% % CV=0.15 %
% 
% CV=0.15;
% 
% for j=1:n
% 
%     xiPID0=zeros(16,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPID,xiPID]=ode15s(@(tiPID,xiPID) PID_fun(xiPID,g1r,gcr,g,bur,bcr,eta,g_e,th,mu,gz,Y,bxr,bi,bp,ba,ga,bm,gm,bd,ka,km,N), [i:1:i+delta_t], xiPID0);
% 
% 
%     xPID(i:i+delta_t,:)=xiPID;
% 
%     xiPID0=xiPID(end,:);
% end
%     fprintf("PID: CV= %3f n= %3f \n",CV,j)
%     xPID_15(:,j)=xPID(:,3);
% 
% 
%       ta_index_15_PID=find(xPID_15(:,j) >= 220 | xPID_15(:,j) <= 180, 1, 'last');
% 
%     ta_15_PID(j)=t(1,ta_index_15_PID);
% 
% 
% if ta_15_PID(j)<1441
% 
%     succ_15_PID(j)=1;
%     insucc_15_PID(j)=0;
% else 
% 
%     succ_15_PID(j)=0;
%     insucc_15_PID(j)=1;
% end
% end
% 
% % CV=0.2 %
% 
% CV=0.2;
% 
% for j=1:n
% 
%     xiPID0=zeros(16,1);
% 
% for i=1:delta_t:tf
% 
%     g1r=g1+g1*CV*randn(1);  
%     bcr=bc+bc*CV*randn(1); 
%     gcr=gc+gc*CV*randn(1); 
% 	bur=bu+bu*CV*randn(1);
%     bxr=bx+bx*CV*randn(1);
% 
% 
%     [tiPID,xiPID]=ode15s(@(tiPID,xiPID) PID_fun(xiPID,g1r,gcr,g,bur,bcr,eta,g_e,th,mu,gz,Y,bxr,bi,bp,ba,ga,bm,gm,bd,ka,km,N), [i:1:i+delta_t], xiPID0);
% 
% 
%     xPID(i:i+delta_t,:)=xiPID;
% 
%     xiPID0=xiPID(end,:);
% end
%     fprintf("PID: CV= %3f n= %3f \n",CV,j)
%     xPID_2(:,j)=xPID(:,3);
% 
% 
%       ta_index_2_PID=find(xPID_2(:,j) >= 220 | xPID_2(:,j) <= 180, 1, 'last');
% 
%     ta_2_PID(j)=t(1,ta_index_2_PID);
% 
% 
% if ta_2_PID(j)<1441
% 
%     succ_2_PID(j)=1;
%     insucc_2_PID(j)=0;
% else 
% 
%     succ_2_PID(j)=0;
%     insucc_2_PID(j)=1;
% end
% end


%% plots
 ref=200*ones(1,2000);
t=ti:tf;

% figure
% tcl = tiledlayout(2,2);
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xP_05,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('P')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xPD_05,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PD')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xPI_05,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PI')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xPID_05,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PID')
% 
% title(tcl,'CV=0.05')
% xlabel(tcl,'t [min]','FontSize',12)
% ylabel(tcl,'Q_x^t [nM]','FontSize',12)

%%

colors=["#4770e6","#cb41e0","#fa40a6","#49c932"];
alpha_colors=["#d9e2fa","#f1d3f5", "#ffcfea", "#daf5d5" ];

figP=figure;
fontsize(figP, 11, "points")
%tcl = tiledlayout(2,2);

%nexttile
plot(ref,'--','LineWidth',2,'Color','k')
hold on
plot(t,xP_1,'LineWidth',2,'Color',alpha_colors(1))
hold on
p_P=plot(t,mean(xP_1,2),'LineWidth',2.5,'Color',colors(1));
axis([0 2000 0 280])
% yline(180)
% yline(220)
%title('P')
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')
grid on
xlabel('t [min]','FontSize',14)
ylabel( 'Q_x_,_P(t) [nM]','FontSize',14)
legend(p_P, 'P', 'FontSize',11)

figPD=figure;
fontsize(figPD, 11, "points")
%nexttile
plot(ref,'--','LineWidth',2,'Color','k')
grid on
hold on
plot(t,xPD_1,'LineWidth',2,'Color',alpha_colors(2))
hold on
p_PD=plot(t,mean(xPD_1,2),'LineWidth',2.5,'Color',colors(2));
axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PD')
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')
grid on
xlabel('t [min]','FontSize',14)
ylabel( 'Q_x_,_P_D(t) [nM]','FontSize',14)
legend(p_PD, 'PD', 'FontSize',11)

figPI=figure;
fontsize(figPI, 11, "points")
%nexttile
plot(ref,'--','LineWidth',2,'Color','k')
grid on
hold on
plot(t,xPI_1,'LineWidth',2,'Color',alpha_colors(3))
hold on
p_PI=plot(t,mean(xPI_1,2),'LineWidth',2.5,'Color',colors(3));
axis([0 2000 0 280])
% yline(180)
% yline(220)
%title('PI')
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')
grid on
xlabel('t [min]','FontSize',14)
ylabel( 'Q_x_,_P_I(t) [nM]','FontSize',14)
legend(p_PI, 'PI', 'FontSize',11)

figPID=figure;
fontsize(figPID, 11, "points")
%nexttile
plot(ref,'--','LineWidth',2,'Color','k')
grid on
hold on
plot(t,xPID_1,'LineWidth',2,'Color',alpha_colors(4))
hold on
p_PID=plot(t,mean(xPID_1,2),'LineWidth',2.5,'Color',colors(4));
axis([0 2000 0 280])
% yline(180)
% yline(220)
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')
grid on
xlabel('t [min]','FontSize',14)
ylabel( 'Q_x_,_P_I_D(t) [nM]','FontSize',14)
legend(p_PID, 'PID', 'FontSize',11)


% title(tcl,'CV=0.1')
% xlabel(tcl,'t [min]','FontSize',12)
% ylabel(tcl,'Q_x^t [nM]','FontSize',12)


% %%
% figure
% tcl = tiledlayout(2,2);
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xP_15,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('P')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xPD_15,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PD')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xPI_15,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PI')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,xPID_15,'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PID')
% 
% title(tcl,'CV=0.15')
% xlabel(tcl,'t [min]','FontSize',12)
% ylabel(tcl,'Q_x^t [nM]','FontSize',12)
% 
% 
% 
% %%
% figure
% tcl = tiledlayout(2,2);
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,movmean(xP_2,60),'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('P')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,movmean(xPD_2,60),'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PD')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,movmean(xPI_2,60),'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PI')
% 
% nexttile
% plot(ref,'--','LineWidth',1,'Color',[0.5,0.5,0.5])
% grid on
% hold on
% plot(t,movmean(xPID_2,60),'LineWidth',1.5)
% axis([0 2000 0 280])
% yline(180)
% yline(220)
% title('PID')
% 
% title(tcl,'CV=0.2')
% xlabel(tcl,'t [min]','FontSize',12)
% ylabel(tcl,'Q_x^t [nM]','FontSize',12)

%% Boxcharts

figure('pos',[488,65.8,680.2,596.2])

subplot(3,1,1)
boxchart([e_1_P',e_1_PD', e_1_PI', e_1_PID'],'BoxFaceColor',colors(1),'MarkerStyle','*','MarkerColor',colors(1))
ax=gca;
ax.YAxis.FontSize=11;
xticklabels({})
ylh=ylabel( {'percentage error';' [%]'},'FontSize',14);
ylh.Position(1)=-0.349907925774379;
%ylim([y_inf y_sup])
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')

subplot(3,1,2)
boxchart([ta_1_P', ta_1_PD', ta_1_PI', ta_1_PID'],'BoxFaceColor',colors(4),'MarkerStyle','*','MarkerColor',colors(4))
ax=gca;
ax.YAxis.FontSize=11;
xticklabels({})
ylh=ylabel({'settling time';' [min]'},'FontSize',14);
ylh.Position(1)=-0.349907925774379;
%ylim([y_inf y_sup])
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')

subplot(3,1,3)
boxchart([o_P',o_PD',o_PI',o_PID'],'BoxFaceColor',colors(2),'MarkerStyle','*','MarkerColor',colors(2))
ax=gca;
ax.YAxis.FontSize=11;
xticklabels({})
ylh=ylabel({'overshoot';' [%]'},'FontSize',14);
ylh.Position(1)=-0.349907925774379;
%ylim([y_inf y_sup])
box("on")
set(gca, 'LineWidth', 1, 'GridLineWidth', 0.5, 'Layer', 'top')

xticklabels({'P' 'PD' 'PI' 'PID'})
set(gca, 'FontSize', 12)




% %% Percentage of successful simulations
% 
% % number of success and insuccess
% 
% num_succ_05_P=sum(succ_05_P ~=0)/n*100;
% num_succ_1_P=sum(succ_1_P ~=0)/n*100;
% num_succ_15_P=sum(succ_15_P ~=0)/n*100;
% num_succ_2_P=sum(succ_2_P ~=0)/n*100;
% 
% num_succ_05_PD=sum(succ_05_PD ~=0)/n*100;
% num_succ_1_PD=sum(succ_1_PD ~=0)/n*100;
% num_succ_15_PD=sum(succ_15_PD ~=0)/n*100;
% num_succ_2_PD=sum(succ_2_PD ~=0)/n*100;
% 
% num_succ_05_PI=sum(succ_05_PI ~=0)/n*100;
% num_succ_1_PI=sum(succ_1_PI ~=0)/n*100;
% num_succ_15_PI=sum(succ_15_PI ~=0)/n*100;
% num_succ_2_PI=sum(succ_2_PI ~=0)/n*100;
% 
% num_succ_05_PID=sum(succ_05_PID ~=0)/n*100;
% num_succ_1_PID=sum(succ_1_PID ~=0)/n*100;
% num_succ_15_PID=sum(succ_15_PID ~=0)/n*100;
% num_succ_2_PID=sum(succ_2_PID ~=0)/n*100;
% 
% fig=figure
% subplot(4,1,1)
%   bar_succ_P=bar([0.05,0.1,0.15,0.2],...
%         [num_succ_05_P,num_succ_1_P,num_succ_15_P,num_succ_2_P],'BarWidth',0.4,'FaceColor',"#d1daff");
%   legend('P','Fontsize',11)
%   ylim([0 120])
%   xticklabels('')
%   box on
%   set(gca,'LineWidth',1)
% 
%   subplot(4,1,2)
%   hold on
%   bar_succ_PD= bar([0.05,0.1,0.15,0.2],...
%         [num_succ_05_PD,num_succ_1_PD,num_succ_15_PD,num_succ_2_PD],'BarWidth',0.4,'FaceColor',"#deabff");
%   legend('PD','Fontsize',11)
%   ylim([0 120])
%   xticklabels('')
%   box on
%   set(gca,'LineWidth',1)
% 
%   subplot(4,1,3)
%   hold on
%   bar_succ_PI= bar([0.05,0.1,0.15,0.2],...
%         [num_succ_05_PI,num_succ_1_PI,num_succ_15_PI,num_succ_2_PI],'BarWidth',0.4,'FaceColor',"#ffbde0");
%   legend('PI','Fontsize',11)
%   ylim([0 120])
%   xticklabels('')
%   box on
%   set(gca,'LineWidth',1)
% 
%   subplot(4,1,4)
%   hold on
%   bar_succ_PID=bar([0.05,0.1,0.15,0.2],...
%         [num_succ_05_PID,num_succ_1_PID,num_succ_15_PID,num_succ_2_PID],'BarWidth',0.4,'FaceColor',"#b0ffbd");
%   ylim([0 120])
%   legend(bar_succ_PID,'PID','FontSize',11)
%   box on
%   set(gca,'LineWidth',1)
%   han=axes(fig,'visible','off'); 
%   han.XLabel.Visible='on';
%   han.YLabel.Visible='on';
%   ylh=ylabel(han,'successful simulations [%]','FontSize',12);
%   ylh.Position(1)=-0.08;
%   xlabel(han,'CV','FontSize',12);

% %% barplots
% 
% % nominal simulations
% [tP_0,xP_0]=ode15s(@(tP_0,xP_0) P_fun(xP_0,bu,g1,gc,g,bc,bx,eta,bp,mu,th,g_e,Y,N), ti:tf, xP0);
% [tPD_0,xPD_0]=ode15s(@(tPD_0,xPD_0) PD_fun(xPD_0,bu,bc,g1,gc,g,bx,eta,bp,mu,Y,th,ba,ga,ka,bm,gm,km,bd,N,g_e), ti:tf, xPD0);
% [tPI_0,xPI_0]=ode15s(@(tPI_0,xPI_0) PI_fun(xPI_0,g1,gc,g,bu,bc,eta,g_e,th,mu,gz,Y,bx,bi,bp,N), ti:tf, xPI0);
% [tPID_0,xPID_0]=ode15s(@(tPID_0,xPID_0) PID_fun(xPID_0,g1,gc,g,bu,bc,eta,g_e,th,mu,gz,Y,bx,bi,bp,ba,ga,bm,gm,bd,ka,km,N), ti:tf, xPID0);
% 
% %nominal percentage error
% e_0_P=abs(mean(xP_0(round(1440):end,3)-200))/200*100;
% e_0_PD=abs(mean(xPD_0(round(1440):end,3)-200))/200*100;
% e_0_PI=abs(mean(xPI_0(round(1440):end,3)-200))/200*100;
% e_0_PID=abs(mean(xPID_0(round(1440):end,3)-200))/200*100;
% 
% 
% % mean and standard deviation of the relative error (with respect to CV=0)
% 
% e_1_per_P=mean(abs((e_1_P-e_0_P)./e_0_P),"omitnan");
% s_1_per_P=std(abs((e_1_P-e_0_P)./e_0_P),1,"omitnan");
% 
% e_1_per_PD=mean(abs((e_1_PD-e_0_PD)./e_0_PD),"omitnan");
% s_1_per_PD=std(abs((e_1_PD-e_0_PD)./e_0_PD),1,"omitnan");
% 
% e_1_per_PI=mean(abs((e_1_PI-e_0_PI)./e_0_PI),"omitnan");
% s_1_per_PI=std(abs((e_1_PI-e_0_PI)./e_0_PI),1,"omitnan");
% 
% e_1_per_PID=mean(abs((e_1_PID-e_0_PID)./e_0_PID),"omitnan");
% s_1_per_PID=std(abs((e_1_PD-e_0_PID)./e_0_PID),1,"omitnan");
% 
% 
%    %%% percentage variation of the error %%% 
% 
%     figure
% 
%     bar_P=bar(-0.3,e_1_per_P,'BarWidth',0.2,'FaceColor',colors(1));
%     hold on
%     errorbar(-0.3,e_1_per_P,s_1_per_P, 'LineStyle','none','Color',[0,0,0],'LineWidth',0.8,'YNegativeDelta',[])
%     hold on
%     bar_PD=bar(-0.1,e_1_per_PD,'BarWidth',0.2,'FaceColor',colors(2));
%     hold on
%     errorbar(-0.1,e_1_per_PD,s_1_per_PD, 'LineStyle','none','Color',[0,0,0],'LineWidth',0.8,'YNegativeDelta',[])
%     hold on
%     bar_PI=bar(0.1,e_1_per_PI,'BarWidth',0.2,'FaceColor',colors(3));
%     hold on
%     errorbar(0.1,e_1_per_PI,s_1_per_PI, 'LineStyle','none','Color',[0,0,0],'LineWidth',0.8,'YNegativeDelta',[])
%     hold on
%     bar_PID=bar(0.3,e_1_per_PID,'BarWidth',0.2,'FaceColor',colors(4));
%     hold on
%     errorbar(0.3,e_1_per_PID,s_1_per_PID, 'LineStyle','none','Color',[0,0,0],'LineWidth',0.8,'YNegativeDelta',[])
% 
