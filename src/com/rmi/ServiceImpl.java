package com.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceImpl extends UnicastRemoteObject implements IService {  
    /** 
     */  
    private static final long serialVersionUID = 682805210518738166L;  
  
    /** 
     * @throws RemoteException 
     */  
    public ServiceImpl() throws RemoteException {  
        super();  
    }  
  
    /* (non-Javadoc) 
     * @see com.suning.ebuy.wd.web.IService#queryName(java.lang.String) 
     */  
    @Override  
    public String queryName(String no) throws RemoteException {  
        return no; 
    }

      
}  
