package tests;

import static org.junit.Assert.*;

import models.AppFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/applicationContext.xml"})
public class AppFilterTest{

	//private static ClassPathXmlApplicationContext context;
	//@Autowired   
	//private AppFilter filter = null;
	
	/*@Before
	public void setUp() throws Exception {
		//context = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    //filter = (AppFilter)context.getBean("AppFilter");
		
		/*user = AppUser.create("test", "test", true);
		user.addGroup(AppGroup.findByIdent("ADMIN"));
	}
	@Test
	public void create() {
		
		
		/*try {
			f = AppFilter.create("test42", "this is a test", "string");
			assertTrue(f != null);				
		} catch (AccessNotAllowedException e) {
			fail();
		} catch (NotEmptyException e) {
			fail();
		} catch (DataLengthException e) {
			fail();
		}
	}*/
	@Test
	public void find() {
		fail("Not yet implemented");
	}
	@Test
	public void edit_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void edit_2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void edit_3() {
		fail("Not yet implemented");
	}
	
	@Test
	public void delete() {
		fail("Not yet implemented");
	}
	
	@Test
	public void add_1_to_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void add_2_to_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void add_3_to_1() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void add_4_to_1() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void add_5_to_1() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void count_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void count_2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void has_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void has_2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void bad_right_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void bad_right_2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void bad_group_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void bad_group_2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void too_long_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void too_long_2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void null_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void null_2() {
		fail("Not yet implemented");
		
	}

}
