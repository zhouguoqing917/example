package org.example.cache;

import org.example.cache.Cache;
import org.example.util.SerializationUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class CacheImpl implements Cache {

    private ShardedJedisPool shardedJedisPool;
    private Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();

    @Autowired
    public void setShardedJedisPool(@Qualifier("shardedJedisPool") ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    @Override
    public void put(String key, Serializable value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            localCache.put(key, value);
            jedis.set(key.getBytes(), SerializationUtil.object2Bytes(value));

        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }

    }

    @Override
    public Object get(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            Object _obj = localCache.get(key);
            if (null != _obj) {
                return _obj;
            }
            byte[] obj = jedis.get(key.getBytes());

            _obj = SerializationUtil.object2Bytes(obj);
            if (_obj != null) {
                localCache.put(key, _obj);
            }
            return _obj;
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }
        return null;
    }

    @Override
    public void putToRedis(String key, Serializable value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(key.getBytes(), SerializationUtil.object2Bytes(value));
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }

    }

    @Override
    public void putToRedis(String key, Serializable value, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(key.getBytes(), SerializationUtil.object2Bytes(value));
            jedis.expire(key.getBytes(), seconds);// 设置过期时间
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }

    }

    @Override
    public Object getFromRedis(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            byte[] obj = jedis.get(key.getBytes());
            Object _obj = SerializationUtil.bytes2Object(obj);
            return _obj;
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }
        return null;

    }
    
    public List mgetFromRedis(byte[] key,byte[]... fields) {
        List returnList = new ArrayList();
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            List<byte[]> list = jedis.hmget(key, fields);
            if(null != list && list.size()>0){
                for(byte[] b:list){
                    returnList.add(SerializationUtil.bytes2Object(b));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }
        return returnList;

    }

    @Override
    public void putStringToRedis(String key, String value) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(key, value);
     
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }

    }

    @Override
    public void putStringToRedis(String key, String value, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, seconds);// 设置过期时间
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }

    }

    @Override
    public String getStringFromRedis(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            String value = jedis.get(key);

            return value;
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }
        return null;
    }

    public void remove(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = shardedJedisPool.getResource();
            jedis.del(key);
           
        } catch (Exception e) {
            e.printStackTrace();
            shardedJedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != shardedJedisPool) {
                shardedJedisPool.returnResource(jedis);
            }
        }
    }

}
