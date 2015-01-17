import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "file:src/main/webapp/WEB-INF/spring/applicationcontext.xml"),
		@ContextConfiguration(name = "child", locations = "file:src/main/webapp/WEB-INF/spring/mvc-config.xml") })
public class TestNormativeDocFilingController {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	WebClient webClient;

	@Test
	public void Test_ListNormativeDocFiling() throws Exception {
		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders
								.post("/normativeStatistical/listNormativeDocFiling")
								.param("fileName", "")
								.param("status", "ACCEPT")
								.param("startTime", "").param("endTime", "")
								.param("sort", "fileName")
								.param("order", "desc"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentType(
								MediaType.APPLICATION_JSON + ";charset=UTF-8"))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.start", 0).exists())
				.andDo(MockMvcResultHandlers.print()).andReturn();

	}

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		String contextPath = "";
		webClient = new WebClient();
		webClient.setWebConnection(new MockWebConnection());
	}
}
