package test;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.zookeeper.KeeperException;

public class Test1 {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("192.168.30.189", new Integer(8089));
		System.out.println(registry.lookup("rmi://192.168.30.33:8089/com.function.data.service.impl.RMIServiceImpl"));
		/*System.out.println(Naming.lookup("rmi://192.168.30.33:8089/com.function.data.service.impl.RMIServiceImpl"));*/
		/*System.out.println(RMIService.queryByAlias("233222456545554","sex", "男", "admin", "admin"));*/
	}
}//成功发布服务名为:rmi://localhost:8088/com.rmi.ServiceImpl
