package com.rmi;

import java.io.File;
import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.function.data.service.RMIService;
import com.util.PropertiesUtil;

public class ServiceConsumer {
	public static String ZK_CONNECTION_STRING;
	public static final int ZK_SESSION_TIMEOUT = 10000;
	public static final String ZK_REGISTRY_PATH = "/registry";
	public static final String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";

	// 用于等待 SyncConnected 事件触发后继续执行当前线程
	private CountDownLatch latch = new CountDownLatch(1);

	// 定义一个 volatile 集合的成员变量，用于保存最新的
	// 所有RMI服务地址（考虑到该变量或许会被其它线程所修改，一旦修改后，该变量的值会影响到所有线程）
	private volatile List<String> urlList = new ArrayList<>();

	// 在构造器中连接ZooKeeper服务器并且拿到服务器中注册的rmi服务地址
	public ServiceConsumer() throws IOException, KeeperException, InterruptedException {
		// 配置文件的相对路径
		File file = new File(this.getClass().getResource("/").getPath() + "\\heavenpool-rmi.properties");
		ZK_CONNECTION_STRING = PropertiesUtil.getPropertiesValue(file, "zooKeeperIp");

		ZooKeeper zk = connectServer(); // 连接 ZooKeeper 服务器并获取 ZooKeeper 对象
		if (zk != null) {
			watchNode(zk); // 监听/registry 节点的所有子节点 ，并更新 urlList 中的rmi服务地址
		}
	}

	// 查找 RMI 服务 使用泛型
	public <T extends Remote> T lookup() throws Exception {
		T service = null;
		int size = urlList.size();// 拿到rmi服务集合，判断其长度
		if (size > 0) {
			String url;
			if (size == 1) {
				url = urlList.get(0); // 若 urlList 中只有一个服务地址，则直接获取该元素
			} else {
				url = urlList.get(ThreadLocalRandom.current().nextInt(size)); // 若
																				// urlList
																				// 中存在多个服务地址，则随机获取一个元素
			}
			service = lookupService(url); // 从 JNDI 中查找 RMI 服务
		}
		return service;
	}

	// 观察 /registry 节点下所有子节点是否有变化
	private void watchNode(final ZooKeeper zk) throws KeeperException, InterruptedException {

		List<String> nodeList = zk.getChildren(ZK_REGISTRY_PATH, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getType() == Event.EventType.NodeChildrenChanged) {
					try {
						watchNode(zk);
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} // 若子节点有变化，则重新调用该方法（为了获取最新子节点中的数据）
				}
			}
		});
		List<String> dataList = new ArrayList<>(); // 用于存放 /registry
													// 所有子节点中的数据
		for (String node : nodeList) {
			byte[] data = zk.getData(ZK_REGISTRY_PATH + "/" + node, false, null); // 获取
																					// /registry
																					// 的子节点中的数据
			dataList.add(new String(data));
		}
		System.out.println("dataList" + dataList);
		urlList = dataList; // 更新最新的 RMI 地址

	}

	public Integer number = 3;

	// 在 JNDI 中查找 RMI 远程服务对象，通过绑定的服务名
	@SuppressWarnings("unchecked")
	private <T> T lookupService(String url) throws Exception {
		int beginIndex = url.indexOf("//");// 拿到双引号的角标
		StringBuffer sb = new StringBuffer(url);
		int endIndex = url.lastIndexOf("/", sb.length());// 拿到url和端口号的结束角标
		String newUrl = sb.substring(beginIndex + 2, endIndex);// 拿到url和端口号的字符串
		int colonIndex = newUrl.indexOf(":");// 拿到url和端口号之间的冒号将他们分隔
		String newPort = newUrl.substring(colonIndex + 1, new StringBuffer(newUrl).length());
		String newIp = newUrl.substring(0, colonIndex);

		T remote = null;
		try {
			// 获取服务注册管理器1. 域名或 IP 地址（host）
			Registry registry = LocateRegistry.getRegistry(newIp, new Integer(newPort));
			remote = (T) registry.lookup(url);
		} catch (NotBoundException | RemoteException e) {
			if (e instanceof ConnectException) {
				// 若连接中断，则使用 urlList 中第一个 RMI 地址来查找（这是一种简单的重试方式，确保不会抛出异常）
				if (number != 0) {
					if (urlList.size() != 0) {
						number--;
						url = urlList.get(0);
						return lookupService(url);
					}
				}
			} else {
				number=3;
				throw e;
			}
		}
		return remote;
	}

	// 连接 ZooKeeper 服务器
	private ZooKeeper connectServer() throws IOException, InterruptedException {
		ZooKeeper zk = null;
		// 指定连接服务器的超时时间，和服务器的地址
		zk = new ZooKeeper(ZK_CONNECTION_STRING, ZK_SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == Event.KeeperState.SyncConnected) {
					latch.countDown(); // 唤醒当前正在执行的线程
				}
			}
		});
		Thread.sleep(10000);
		/*latch.await(); // 使当前线程处于等待状态	
*/		return zk;
	}

	public RMIService getRMIService() throws Exception {
		return lookup();
	}

	public String queryOne(RequestConfig requestConfig) throws Exception {
		RMIService rmiService = lookup();
		return rmiService.queryOne(JsonUtil.queryOrDeleteJson(requestConfig));
	}

	public String queryAll(RequestConfig requestConfig) throws Exception {
		RMIService rmiService = lookup();
		return rmiService.queryAll(JsonUtil.queryOrDeleteJson(requestConfig));
	}

	public String conditionQuery(RequestConfig requestConfig) throws Exception {
		RMIService rmiService = lookup();
		return rmiService.conditionQuery(JsonUtil.queryOrDeleteJson(requestConfig));
	}

	public String saveService(RequestConfig requestConfig, Object bean, Map<String, String> pipelineForContent,
			Map<String, String> featureCodeForContent) throws Exception {
		List<ContentInfo> contentInfos = JsonUtil.getContentInfo(bean, pipelineForContent, featureCodeForContent);
		RMIService rmiService = lookup();
		String json=JsonUtil.saveOrUpdateJson(requestConfig, contentInfos);
		System.err.println(json);
		return rmiService.insertService(json);
	}

	public String insertService(RequestConfig requestConfig, List<ContentInfo> contentInfos) throws Exception {
		RMIService rmiService = lookup();
		return rmiService.insertService(JsonUtil.insertOrUpdateJson(requestConfig, contentInfos));
	}

	public String updateService(RequestConfig requestConfig, Object bean, Map<String, String> pipelineForContent,
			Map<String, String> featureCodeForContent) throws Exception {
		List<ContentInfo> contentInfos = JsonUtil.getContentInfo(bean, pipelineForContent, featureCodeForContent);
		RMIService rmiService = lookup();
		return rmiService.updateService(JsonUtil.saveOrUpdateJson(requestConfig, contentInfos));
	}

	public String queryByAlias(String componentID, String alias, String value, String username, String password)
			throws Exception {
		RMIService rmiService = lookup();
		return rmiService.queryByAlias(componentID, alias, value, username, password);
	}
}