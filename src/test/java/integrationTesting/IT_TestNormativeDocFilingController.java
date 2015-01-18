package integrationTesting;

import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.WebClient;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

public class IT_TestNormativeDocFilingController {

	private WebDriver page;

	@Test
	public void Test_ListNormativeDocFiling() throws Exception {

		page.get("http://localhost:8080/myactiviti/normativeStatistical/viewNormativeDocFiling?id=33");
		assertEquals(
				"http://localhost:8080/myactiviti/normativeStatistical/viewNormativeDocFiling?id=33",
				page.getCurrentUrl());
		WebElement element = page.findElement(By.id("fileName"));
		assertEquals("测试enum002",element.getAttribute("value"));
		// page.findElement(By.id("fileName")).sendKeys("1");
		// page.findElement(By.id("organizationName")).sendKeys("政府办");
		// form1.findElement(By.cssSelector("input[type=button]")).click();
		// assertEquals("http://localhost/test2", page.getCurrentUrl());

	}

	@Before
	public void setUp() {

		page = new HtmlUnitDriver();
	}
}
