function x_det=deterministic(x,p)

  x_det=zeros(p.Nt*4+p.Np*2+p.Ni*4+p.Nd*4+2,1);

  for i=1:p.Nt

       s_idx_t=(i-1)*4+1:(i-1)*4+4;
       x_det(s_idx_t)=quorum_diffusion_t(x(s_idx_t),p,x(end-1),x(end));

  end

  for i=1:p.Np

       s_idx_p=p.Nt*4+((i-1)*2+1:(i-1)*2+2);
       x_det(s_idx_p)=quorum_diffusion_p(x(s_idx_p),p,x(end-1),x(end));

  end

  for i=1:p.Ni

       s_idx_i=p.Nt*4+p.Np*2+((i-1)*4+1:(i-1)*4+4);
       x_det(s_idx_i)=quorum_diffusion_i(x(s_idx_i),p,x(end-1),x(end));

  end


  for i=1:p.Nd

       s_idx_d=p.Nt*4+p.Np*2+p.Ni*4+((i-1)*4+1:(i-1)*4+4);
       x_det(s_idx_d)=quorum_diffusion_d(x(s_idx_d),p,x(end-1),x(end));

  end

    qxt=x(3:4:p.Nt*4);
    qxp=x(p.Nt*4+1:2:p.Nt*4+p.Np*2);
    qxi=x(p.Nt*4+p.Np*2+3:4:p.Nt*4+p.Np*2+p.Ni*4);
    qxd=x(p.Nt*4+p.Np*2+p.Ni*4+3:4:end-2);

    qut=x(4:4:p.Nt*4);
    qup=x(p.Nt*4+2:2:p.Nt*4+p.Np*2);
    qui=x(p.Nt*4+p.Np*2+4:4:end-2);
    qud=x(p.Nt*4+p.Np*2+p.Ni*4+4:4:end-2);

    qx=[qxt;qxp;qxi;qxd];
    qu=[qut;qup;qui;qud];

    x_det(end-1:end)=quorum_diffusion_e(x(end-1:end),p,qx,qu);

end