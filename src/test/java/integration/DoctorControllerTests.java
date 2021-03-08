package integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.umurcan.sesame.ApplicationCongfiguration;
import com.umurcan.sesame.SesameApplication;
import com.umurcan.sesame.controller.DoctorController;

@SpringBootTest(classes = { SesameApplication.class,
		ApplicationCongfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DoctorControllerTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	DoctorController controller;

	@BeforeEach
	public void setup() throws Exception {
	}

	@Test
	void testGetDoctors() throws Exception {
		this.mockMvc.perform(get("/doctors")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.doctors").exists())
				.andExpect(jsonPath("$.doctors").isArray())
				.andExpect(jsonPath("$.doctors[0].appointmentsByLocation").exists())
				.andExpect(jsonPath("$.doctors[0].appointmentsByLocation").isArray())
				.andExpect(jsonPath("$.doctors[0].appointmentsByLocation[0].appointments").exists())
				.andExpect(jsonPath("$.doctors[0].appointmentsByLocation[0].appointments").isArray())
				.andReturn();
	}

}
