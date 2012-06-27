// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.AppUser;
import intranet.AppUserDataOnDemand;
import intranet.AppUserIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AppUserIntegrationTest_Roo_IntegrationTest {
    
    declare @type: AppUserIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: AppUserIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: AppUserIntegrationTest: @Transactional;
    
    @Autowired
    private AppUserDataOnDemand AppUserIntegrationTest.dod;
    
    @Test
    public void AppUserIntegrationTest.testCountAppUsers() {
        Assert.assertNotNull("Data on demand for 'AppUser' failed to initialize correctly", dod.getRandomAppUser());
        long count = AppUser.countAppUsers();
        Assert.assertTrue("Counter for 'AppUser' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void AppUserIntegrationTest.testFindAppUser() {
        AppUser obj = dod.getRandomAppUser();
        Assert.assertNotNull("Data on demand for 'AppUser' failed to initialize correctly", obj);
        Integer id = obj.getUser();
        Assert.assertNotNull("Data on demand for 'AppUser' failed to provide an identifier", id);
        obj = AppUser.findAppUser(id);
        Assert.assertNotNull("Find method for 'AppUser' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'AppUser' returned the incorrect identifier", id, obj.getUser());
    }
    
    @Test
    public void AppUserIntegrationTest.testFindAllAppUsers() {
        Assert.assertNotNull("Data on demand for 'AppUser' failed to initialize correctly", dod.getRandomAppUser());
        long count = AppUser.countAppUsers();
        Assert.assertTrue("Too expensive to perform a find all test for 'AppUser', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<AppUser> result = AppUser.findAllAppUsers();
        Assert.assertNotNull("Find all method for 'AppUser' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AppUser' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void AppUserIntegrationTest.testFindAppUserEntries() {
        Assert.assertNotNull("Data on demand for 'AppUser' failed to initialize correctly", dod.getRandomAppUser());
        long count = AppUser.countAppUsers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<AppUser> result = AppUser.findAppUserEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'AppUser' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'AppUser' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void AppUserIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'AppUser' failed to initialize correctly", dod.getRandomAppUser());
        AppUser obj = dod.getNewTransientAppUser(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'AppUser' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'AppUser' identifier to be null", obj.getUser());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'AppUser' identifier to no longer be null", obj.getUser());
    }
    
    @Test
    public void AppUserIntegrationTest.testRemove() {
        AppUser obj = dod.getRandomAppUser();
        Assert.assertNotNull("Data on demand for 'AppUser' failed to initialize correctly", obj);
        Integer id = obj.getUser();
        Assert.assertNotNull("Data on demand for 'AppUser' failed to provide an identifier", id);
        obj = AppUser.findAppUser(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'AppUser' with identifier '" + id + "'", AppUser.findAppUser(id));
    }
    
}
