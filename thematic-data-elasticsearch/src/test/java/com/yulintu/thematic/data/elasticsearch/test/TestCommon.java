package com.yulintu.thematic.data.elasticsearch.test;
//
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.junit.Test;

public class TestCommon {

    public final static String HOST = "192.168.20.10";
    public final static int PORT = 9300;

    @Test
    public void testCommon() {
//
//        TransportClient client = null;
//        try {
//
//            Settings settings = Settings.builder()
//                    .put("cluster.name", "elasticsearch_cluster")
//                    .build();
//
//            client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        GetResponse response = client.prepareGet("com.yulintu.tworegion.entities.sjzd", "com.yulintu.tworegion.entities.Sjzd", "1124").get();
//
//// on shutdown
//
//        client.close();
    }
}
