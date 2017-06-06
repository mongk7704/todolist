package kr.or.connect.todo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TodoApplication.class)
@WebAppConfiguration
public class DbTest {
	@Autowired
	WebApplicationContext wac;
	MockMvc mvc;
	
	private int id;
	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac).alwaysDo(print(System.out)).build();
	}
	@Test
	public void shouldDAO() throws Exception {

		String requestBody = "{\"id\":\"\",\"todo\":\"해야할일\",\"completed\":0,\"date\":\"2017-06-05T04:30:25.773Z\"}";
		MvcResult result= mvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.todo").value("해야할일")).andExpect(jsonPath("$.completed").value(0)).andReturn();
		id=Integer.parseInt(result.getResponse().getContentAsString().substring(6, 7));
		
		requestBody = "{\"completed\":1}";
		mvc.perform(put("/task/"+id).contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isNoContent());
		
		mvc.perform(delete("/task/"+id)).andExpect(status().isNoContent());
		
	}
}
