// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Privacities;
import intranet.PrivacitiesDataOnDemand;
import intranet.PrivacitiesIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PrivacitiesIntegrationTest_Roo_IntegrationTest {
    
    declare @type: PrivacitiesIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: PrivacitiesIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: PrivacitiesIntegrationTest: @Transactional;
    
    @Autowired
    private PrivacitiesDataOnDemand PrivacitiesIntegrationTest.dod;
    
    @Test
    public void PrivacitiesIntegrationTest.testCountPrivacitieses() {
        Assert.assertNotNull("Data on demand for 'Privacities' failed to initialize correctly", dod.getRandomPrivacities());
        long count = Privacities.countPrivacitieses();
        Assert.assertTrue("Counter for 'Privacities' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void PrivacitiesIntegrationTest.testFindPrivacities() {
        Privacities obj = dod.getRandomPrivacities();
        Assert.assertNotNull("Data on demand for 'Privacities' failed to initialize correctly", obj);
        Integer id = obj.getIdprivacity();
        Assert.assertNotNull("Data on demand for 'Privacities' failed to provide an identifier", id);
        obj = Privacities.findPrivacities(id);
        Assert.assertNotNull("Find method for 'Privacities' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Privacities' returned the incorrect identifier", id, obj.getIdprivacity());
    }
    
    @Test
    public void PrivacitiesIntegrationTest.testFindAllPrivacitieses() {
        Assert.assertNotNull("Data on demand for 'Privacities' failed to initialize correctly", dod.getRandomPrivacities());
        long count = Privacities.countPrivacitieses();
        Assert.assertTrue("Too expensive to perform a find all test for 'Privacities', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Privacities> result = Privacities.findAllPrivacitieses();
        Assert.assertNotNull("Find all method for 'Privacities' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Privacities' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void PrivacitiesIntegrationTest.testFindPrivacitiesEntries() {
        Assert.assertNotNull("Data on demand for 'Privacities' failed to initialize correctly", dod.getRandomPrivacities());
        long count = Privacities.countPrivacitieses();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Privacities> result = Privacities.findPrivacitiesEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Privacities' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Privacities' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void PrivacitiesIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Privacities' failed to initialize correctly", dod.getRandomPrivacities());
        Privacities obj = dod.getNewTransientPrivacities(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Privacities' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Privacities' identifier to be null", obj.getIdprivacity());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Privacities' identifier to no longer be null", obj.getIdprivacity());
    }
    
    @Test
    public void PrivacitiesIntegrationTest.testRemove() {
        Privacities obj = dod.getRandomPrivacities();
        Assert.assertNotNull("Data on demand for 'Privacities' failed to initialize correctly", obj);
        Integer id = obj.getIdprivacity();
        Assert.assertNotNull("Data on demand for 'Privacities' failed to provide an identifier", id);
        obj = Privacities.findPrivacities(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Privacities' with identifier '" + id + "'", Privacities.findPrivacities(id));
    }
    
}
