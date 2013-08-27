package org.iti.base;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;

public class Cache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7132045672518426869L;

	private static Map<String, CouchbaseClient> clientMap = null;

	public static final Integer SECOND = 1;
	public static final Integer MINUTE = 60 * SECOND;
	public static final Integer HOUR = 60 * MINUTE;
	public static final Integer DAY = 24 * HOUR;
	public static final Integer MONTH = 30 * HOUR;

	/**
	 * http://127.0.0.1:8091->userName/passwd
	 */
	private static String DEFAULT_BUCKET_NAME;

	/**
	 * key:auth:userName/passwd value:List<String>uriList
	 * defaultBucketName:Ä¬ÈÏ»º´æbucket
	 * 
	 * @param uriMap
	 */
	public Cache(Map<String, List<String>> uriMap, String defaultBucketName) {
		Cache.DEFAULT_BUCKET_NAME = defaultBucketName;
		clientMap = new ConcurrentHashMap<String, CouchbaseClient>();
		for (String auth : uriMap.keySet()) {
			List<URI> uriList = new ArrayList<URI>();
			for (String uriStr : uriMap.get(auth)) {
				try {
					URI uri = new URI(new StringBuilder(
							uriStr.endsWith("/") ? uriStr : (new StringBuilder(
									uriStr).append("/"))).append("pools")
							.toString());
					uriList.add(uri);
					String bucketName = auth.split("/")[0];
					String bucketPasswd = auth.split("/")[1];
					CouchbaseClient client = getCacheClient(init(uriList,
							bucketName, bucketPasswd));
					clientMap.put(bucketName, client);
				} catch (URISyntaxException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
	}

	private CouchbaseConnectionFactory init(List<URI> uriList,
			String bucketName, String bucketPasswd) {
		try {
			return new CouchbaseConnectionFactory(uriList, bucketName,
					bucketPasswd);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
			throw new RuntimeException(e);
		}
	}

	public CouchbaseClient getCacheClient(CouchbaseConnectionFactory ccf) {
		try {
			return new CouchbaseClient(ccf);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static CouchbaseClient getClient() {
		return Cache.clientMap.get(DEFAULT_BUCKET_NAME);
	}

	public static CouchbaseClient getClient(String bucketName) {
		if (bucketName.trim().equals(""))
			return getClient();
		else
			return Cache.clientMap.get(bucketName);
	}

	public static void shutdownClient(CouchbaseClient client) {
		if (client != null)
			client.shutdown();
	}

	public static String buildKey(Class<?> clazz, Serializable key) {
		return new StringBuilder(clazz.getName().trim()).append("##")
				.append(key.toString().trim()).toString().trim();
	}
}
