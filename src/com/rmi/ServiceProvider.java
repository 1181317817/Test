package com.rmi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.util.PropertiesUtil;

public class ServiceProvider {
	public static String ZK_CONNECTION_STRING;
	public static final int ZK_SESSION_TIMEOUT = 5000;
	public static final String ZK_REGISTRY_PATH = "/registry";
	public static final String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
	// 用于等待 SyncConnected 事件触发后(连接到zookeeper并创建对象才继续执行当前线程)
	// 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
	// 构造参数在线程能通过 await() 之前，必须调用 countDown() 的次数
	private CountDownLatch latch = new CountDownLatch(1);

	public ServiceProvider() throws IOException {
		// 配置文件的相对路径
		File file = new File(this.getClass().getResource("/").getPath() + "\\heavenpool-rmi.properties");
		ZK_CONNECTION_STRING = PropertiesUtil.getPropertiesValue(file, "zooKeeperIp");
	}

	// 发布 RMI 服务并注册 RMI 地址到 ZooKeeper 中，传入服务对象，和端口号
	public void publish(Remote remote) throws IOException, KeeperException, InterruptedException {
		File file = new File(this.getClass().getResource("/").getPath() + "\\heavenpool-rmi.properties");
		// 配置文件的相对路径
		String host = PropertiesUtil.getPropertiesValue(file, "rmiIp");
		int port = new Integer(PropertiesUtil.getPropertiesValue(file, "rmiPort"));
		String url = publishService(remote, host, port); // 发布 RMI 服务并返回 RMI 地址
		if (url != null) {
			ZooKeeper zk = connectServer(); // 连接 ZooKeeper 服务器并获取 ZooKeeper 对象
			if (zk != null) {
				createNode(zk, url); // 创建 ZNode(节点) 并将 RMI 地址放入 ZNode 上
			}
		}
	}

	// 发布 RMI 服务并注册 RMI 地址到 ZooKeeper 中，传入服务对象，和端口号
	public void publish(Remote remote, String host, int port)
			throws IOException, KeeperException, InterruptedException {
		// 配置文件的相对路径
		String url = publishService(remote, host, port); // 发布 RMI 服务并返回 RMI 地址
		if (url != null) {
			ZooKeeper zk = connectServer(); // 连接 ZooKeeper 服务器并获取 ZooKeeper 对象
			if (zk != null) {
				createNode(zk, url); // 创建 ZNode(节点) 并将 RMI 地址放入 ZNode 上
			}
		}
	}

	// 发布 RMI 服务,并返回服务绑定的命名
	private String publishService(Remote remote, String host, int port) throws RemoteException, MalformedURLException {
		String url = null;
		url = String.format("rmi://%s:%d/%s", host, port, remote.getClass().getName());
		/*
		 * url =
		 * String.format(remote.getClass().getName());//以服务对象的全限定名作为服务对象的命名
		 */
		
		 Registry registry = LocateRegistry.createRegistry(port);
		 registry.rebind(url, remote);// 对服务对象绑定命名
		 
		/*LocateRegistry.createRegistry(port);
		Naming.rebind(url, remote);*/
		System.out.println("成功发布服务名为:" + url);
		return url;
	}

	// 连接 ZooKeeper 服务器斌获得zookeeper对象
	public ZooKeeper connectServer() throws IOException, InterruptedException {
		ZooKeeper zk = null;
		zk = new ZooKeeper(ZK_CONNECTION_STRING, ZK_SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == Event.KeeperState.SyncConnected) {
					System.out.println(event.getState());
					System.out.println(Event.KeeperState.SyncConnected);
					latch.countDown(); // 唤醒当前正在执行的线程
				}
			}
		});
		latch.await(); // 使当前线程处于等待状态,在构造参数中设置了一个countDown执行的次数，当满足次数，就可以通过该方法
		return zk;
	}

	// 创建 ZNode
	private void createNode(ZooKeeper zk, String url) throws KeeperException, InterruptedException {
		byte[] data = url.getBytes();
		String path = zk.create(ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL); // 创建一个临时性且有序的
	}
}
