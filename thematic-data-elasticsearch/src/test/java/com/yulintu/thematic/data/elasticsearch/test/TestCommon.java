package com.yulintu.thematic.data.elasticsearch.test;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.indices.mapping.PutMapping;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestCommon {

    private final static String HOST = "http://192.168.20.10:9200";
    private static JestClient client = null;

    @Before
    public void before() {

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(HOST)
                .multiThreaded(true)
                .build());

        client = factory.getObject();
    }

    @Test
    public void test() {
    }

    //    @Test
    public void testIndex() throws IOException {
        PutMapping putMapping = new PutMapping.Builder(
                "my_index",
                "my_type",
                "{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }"
        ).build();
        client.execute(putMapping);
    }

    //    @Test
    public void testCreation() {
//        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
//        builder.setConfigureFilePath("hibernate.postgresql.cfg.xml");
//        builder.setMappingClasses(new Class[]{Sjzd.class});
//        ProviderPersistenceImpl provider = new ProviderPersistenceImpl(builder.getConnectionString());
//
//        Object o = provider.jpaInTransaction(em -> {
//
//            FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
//
//            QueryBuilder qb = fullTextEm.getSearchFactory()
//                    .buildQueryBuilder().forEntity(Sjzd.class).get();
//
//            Query query = qb.all().createQuery();
//
//            org.apache.lucene.search.Query luceneQuery = qb
//                    .keyword()
//                    .onFields("mc", "fzmc")
//                    .matching("方式")
//                    .createQuery();
//
//            javax.persistence.Query jpaQuery =
//                    fullTextEm.createFullTextQuery(query, Sjzd.class);
//
//            List result = jpaQuery.getResultList();
//
//
//            return result;
//        });
    }


    //    @Test
    public void testAdd() throws IOException {

        Map<String, String> source = new LinkedHashMap<>();
        source.put("user", "kimchy");
        source.put("gender", "female");
        source.put("age", "29");

        Index index = new Index.Builder(source).index("person").type("person").id("1").build();
        client.execute(index);
    }

//    @Test
    public void testSearch() throws IOException {
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchQuery("mc", "方式"));
//
//        Search search = new Search.Builder(searchSourceBuilder.toString())
//                // multiple index or types can be added.
//                .addIndex("sjzd")
//                .addType("com.yulintu.tworegion.entities.Sjzd")
//                .build();
//
//        SearchResult result = client.execute(search);
    }
}
