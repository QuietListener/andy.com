package andy.com.internet.theories.consistentHash;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ConsistentHash {
    //用于存放节点
    protected SortedMap<Integer,String> sortedMap = new TreeMap<>();


    public ConsistentHash(){}

    public ConsistentHash(List<String> initialNodes){

        for (String node:initialNodes)
        {
                addNode(node);
        }

        System.out.println(sortedMap);
    }

    public void addNode(String node){
        if(StringUtils.isBlank(node))
            return;

        int hash = HashUtils.getHash(node);
        System.out.println("[" + node + "]加入集合中, 其Hash值为" + hash);
        sortedMap.put(hash, node);
    }

    public void remoteNode(String node){
        if(StringUtils.isBlank(node))
            return;

        int hash = HashUtils.getHash(node);
        System.out.println("[" + node + "] 移除, 其Hash值为" + hash);
        sortedMap.remove(hash);
    }

    //得到应当路由到的结点
    public  String getNode(String node)
    {
        // 得到带路由的结点的Hash值
        int hash = HashUtils.getHash(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap =
                sortedMap.tailMap(hash);

        // 第一个Key就是顺时针过去离node最近的那个结点
        if(subMap.size() == 0){
            subMap = sortedMap;
        }

        Integer i = subMap.firstKey();
        // 返回对应的服务器名称
        return subMap.get(i);
    }


    public static void main(String [] args){
        List<String> servers = Arrays.asList("192.168.0.0:111",
                "192.168.0.1:111", "192.168.0.2:111",
                "192.168.0.3:111", "192.168.0.4:111");

        ConsistentHash ch = new ConsistentHash(servers);


        Map<String,Integer> map = new HashMap<>();

        int size = 10000;
        for(int i = 0; i< size; i++)
        {
            String node = ch.getNode(i+"");
            Integer count = map.get(node);
            if(count == null){
                count = 0;
            }

            count += 1;

            map.put(node,count);
        }

        System.out.println("hash结果:");
        for(Map.Entry<String,Integer> entry: map.entrySet()){
           System.out.println(entry.getKey()+":"+entry.getValue()*100/size+"%");
        }


        //移除一个node
        String rmNode = servers.get(0);
        System.out.println("移除node:" + rmNode);
        map.clear();
        ch.remoteNode(rmNode);
        for(int i = 0; i< size; i++)
        {
            String node = ch.getNode(i+"");
            Integer count = map.get(node);
            if(count == null){
                count = 0;
            }

            count += 1;

            map.put(node,count);
        }

        System.out.println("hash结果:");
        for(Map.Entry<String,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue()*100/size+"%");
        }

    }

}
