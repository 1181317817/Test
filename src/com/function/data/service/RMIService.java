package com.function.data.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIService extends Remote{

	/**
	 * 单条业务查询（根据组件号和业务号）
	 * @param json
	 * @return
	 * @throws RemoteException
	 */
	String queryOne(String json) throws RemoteException;
	/**
	 * 根据组件号批量查询业务
	 * @param json
	 * @return
	 * @throws RemoteException
	 */
	String queryAll(String json) throws RemoteException;/**
	 * 条件查询
	 * @param json
	 * @return
	 * @throws RemoteException
	 */
	String conditionQuery(String json) throws RemoteException;
	
	/**
	 * 插入业务
	 * @param json
	 * @return
	 * @throws RemoteException
	 */
	String insertService(String json) throws RemoteException;
	
	/**
	 * 更新业务
	 * @param json
	 * @return
	 * @throws RemoteException
	 */
	String updateService(String json) throws RemoteException;
	/**
	 * 根据别名值查询
	 * @param componentID
	 * @param alias
	 * @param value
	 * @return
	 * @throws RemoteException
	 */
	String queryByAlias(String componentID,String alias,String value,String username,String password) throws RemoteException;
}
