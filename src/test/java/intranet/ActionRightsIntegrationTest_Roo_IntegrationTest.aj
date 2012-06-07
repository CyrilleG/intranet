// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.ActionRights;
import intranet.ActionRightsDataOnDemand;
import intranet.ActionRightsIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ActionRightsIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ActionRightsIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ActionRightsIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: ActionRightsIntegrationTest: @Transactional;
    
    @Autowired
    private ActionRightsDataOnDemand ActionRightsIntegrationTest.dod;
    
    @Test
    public void ActionRightsIntegrationTest.testCountActionRightses() {
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to initialize correctly", dod.getRandomActionRights());
        long count = ActionRights.countActionRightses();
        Assert.assertTrue("Counter for 'ActionRights' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ActionRightsIntegrationTest.testFindActionRights() {
        ActionRights obj = dod.getRandomActionRights();
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to initialize correctly", obj);
        Integer id = obj.getIdactionRights();
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to provide an identifier", id);
        obj = ActionRights.findActionRights(id);
        Assert.assertNotNull("Find method for 'ActionRights' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ActionRights' returned the incorrect identifier", id, obj.getIdactionRights());
    }
    
    @Test
    public void ActionRightsIntegrationTest.testFindAllActionRightses() {
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to initialize correctly", dod.getRandomActionRights());
        long count = ActionRights.countActionRightses();
        Assert.assertTrue("Too expensive to perform a find all test for 'ActionRights', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ActionRights> result = ActionRights.findAllActionRightses();
        Assert.assertNotNull("Find all method for 'ActionRights' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ActionRights' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ActionRightsIntegrationTest.testFindActionRightsEntries() {
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to initialize correctly", dod.getRandomActionRights());
        long count = ActionRights.countActionRightses();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ActionRights> result = ActionRights.findActionRightsEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'ActionRights' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ActionRights' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ActionRightsIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to initialize correctly", dod.getRandomActionRights());
        ActionRights obj = dod.getNewTransientActionRights(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ActionRights' identifier to be null", obj.getIdactionRights());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'ActionRights' identifier to no longer be null", obj.getIdactionRights());
    }
    
    @Test
    public void ActionRightsIntegrationTest.testRemove() {
        ActionRights obj = dod.getRandomActionRights();
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to initialize correctly", obj);
        Integer id = obj.getIdactionRights();
        Assert.assertNotNull("Data on demand for 'ActionRights' failed to provide an identifier", id);
        obj = ActionRights.findActionRights(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'ActionRights' with identifier '" + id + "'", ActionRights.findActionRights(id));
    }
    
}
