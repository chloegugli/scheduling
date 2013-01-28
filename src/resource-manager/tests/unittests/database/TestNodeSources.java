package unittests.database;

import java.io.File;
import java.util.Collection;

import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ow2.proactive.resourcemanager.authentication.Client;
import org.ow2.proactive.resourcemanager.core.properties.PAResourceManagerProperties;
import org.ow2.proactive.resourcemanager.db.NodeSourceData;
import org.ow2.proactive.resourcemanager.db.RMDBManager;
import org.ow2.proactive.resourcemanager.nodesource.infrastructure.DefaultInfrastructureManager;
import org.ow2.proactive.resourcemanager.nodesource.policy.StaticPolicy;


public class TestNodeSources {

    protected RMDBManager dbManager;

    @Before
    public void initDB() throws Exception {
        Configuration config = new Configuration().configure(new File(PAResourceManagerProperties
                .getAbsolutePath("src/resource-manager/tests/functionaltests/config/hibernate.cfg.xml")));
        dbManager = new RMDBManager(config, true, true);
    }

    @After
    public void cleanup() {
        if (dbManager != null) {
            dbManager.close();
        }
    }

    @Test
    public void emptyNodeSource() throws Exception {
        NodeSourceData nodeSourceData = new NodeSourceData();
        try {
            dbManager.addNodeSource(nodeSourceData);
            Assert.fail("Empty node source successfully added");
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void addNodeSource() throws Exception {

        NodeSourceData nodeSourceData = new NodeSourceData("ns1", DefaultInfrastructureManager.class
                .getName(), new String[] { "infrastructure" }, StaticPolicy.class.getName(),
            new String[] { "policy" }, new Client(null, false));

        dbManager.addNodeSource(nodeSourceData);
        Collection<NodeSourceData> sources = dbManager.getNodeSources();
        Assert.assertEquals(1, sources.size());

        NodeSourceData source = sources.iterator().next();
        Assert.assertEquals("ns1", source.getName());
        Assert.assertEquals(DefaultInfrastructureManager.class.getName(), source.getInfrastructureType());
        Assert.assertEquals("infrastructure", source.getInfrastructureParameters()[0]);
        Assert.assertEquals(StaticPolicy.class.getName(), source.getPolicyType());
        Assert.assertEquals("policy", source.getPolicyParameters()[0]);
    }

    @Test
    public void removeNodeSource() throws Exception {
        dbManager.removeNodeSource("ns1");
        Collection<NodeSourceData> sources = dbManager.getNodeSources();
        Assert.assertEquals(0, sources.size());
    }

}
