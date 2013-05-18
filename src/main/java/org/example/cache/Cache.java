package org.example.cache;

import java.util.List;

import java.io.Serializable;

/**
 * Cache base
 * @author chengxinsun
 * @date 2012-5-9
 */
public interface Cache {
	public void put(String key,Serializable value);
	public Object get(String key);
	
	public void putToRedis(String key,Serializable  value);
	public Object getFromRedis(String key);
	
	public void putStringToRedis(String key,String value);
	public String getStringFromRedis(String key);
	
	public void putToRedis(String key,Serializable  value,int seconds);
	public void putStringToRedis(String key,String value,int seconds);
	
	public void remove(String key);
	
	public List mgetFromRedis(byte[] key,byte[]... fields);
}
