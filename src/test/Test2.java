	package test;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import com.rmi.IService;
import com.rmi.ServiceImpl;
import com.rmi.ServiceProvider;

public class Test2 {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		IService serviceImpl=new ServiceImpl();
    	ServiceProvider serviceProvider= new ServiceProvider();
    	serviceProvider.publish(serviceImpl,"172.16.1.18",8089);
	}
}
