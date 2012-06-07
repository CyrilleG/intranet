// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Right;
import intranet.RightDataOnDemand;
import intranet.RightIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect RightIntegrationTest_Roo_IntegrationTest {
    
    declare @type: RightIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: RightIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: RightIntegrationTest: @Transactional;
    
    @Autowired
    private RightDataOnDemand RightIntegrationTest.dod;
    
    @Test
    public void RightIntegrationTest.testCountRights() {
        Assert.assertNotNull("Data on demand for 'Right' failed to initialize correctly", dod.getRandomRight());
        long count = Right.countRights();
        Assert.assertTrue("Counter for 'Right' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void RightIntegrationTest.testFindRight() {
        Right obj = dod.getRandomRight();
        Assert.assertNotNull("Data on demand for 'Right' failed to initialize correctly", obj);
        Integer id = obj.getIdright();
        Assert.assertNotNull("Data on demand for 'Right' failed to provide an identifier", id);
        obj = Right.findRight(id);
        Assert.assertNotNull("Find method for 'Right' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Right' returned the incorrect identifier", id, obj.getIdright());
    }
    
    @Test
    public void RightIntegrationTest.testFindAllRights() {
        Assert.assertNotNull("Data on demand for 'Right' failed to initialize correctly", dod.getRandomRight());
        long count = Right.countRights();
        Assert.assertTrue("Too expensive to perform a find all test for 'Right', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Right> result = Right.findAllRights();
        Assert.assertNotNull("Find all method for 'Right' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Right' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void RightIntegrationTest.testFindRightEntries() {
        Assert.assertNotNull("Data on demand for 'Right' failed to initialize correctly", dod.getRandomRight());
        long count = Right.countRights();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Right> result = Right.findRightEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Right' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Right' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void RightIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Right' failed to initialize correctly", dod.getRandomRight());
        Right obj = dod.getNewTransientRight(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Right' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Right' identifier to be null", obj.getIdright());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Right' identifier to no longer be null", obj.getIdright());
    }
    
    @Test
    public void RightIntegrationTest.testRemove() {
        Right obj = dod.getRandomRight();
        Assert.assertNotNull("Data on demand for 'Right' failed to initialize correctly", obj);
        Integer id = obj.getIdright();
        Assert.assertNotNull("Data on demand for 'Right' failed to provide an identifier", id);
        obj = Right.findRight(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Right' with identifier '" + id + "'", Right.findRight(id));
    }
    
}
