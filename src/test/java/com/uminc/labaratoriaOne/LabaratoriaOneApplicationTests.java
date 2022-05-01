package com.uminc.labaratoriaOne;

import com.uminc.labaratoriaOne.controller.FindMaxNumberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class LabaratoriaOneApplicationTests
{

	@Autowired
	FindMaxNumberController findMaxNumberController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(findMaxNumberController).build();
	}

	@Test
	void textCorrectRequest() throws Exception
	{
		MvcResult mvcResult = this.mockMvc.perform(get("/findmaxnumber?first=4&&second=5&&third=6")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.maxValue").value("6")).andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void testBadRequestWithWrongTypeParams() throws Exception
	{
		MvcResult mvcResult = this.mockMvc.perform(get("/findmaxnumber?first=4&&second=gdfgd&&third=6")).andDo(print()).andExpect(status().isBadRequest()).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

    @Test
    void testBadRequestWithMissingParams() throws Exception
    {
        MvcResult mvcResult = this.mockMvc.perform(get("/findmaxnumber?first=4&&wrongName=7&&third=6")).andDo(print()).andExpect(status().isBadRequest()).andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

	@Test
	void testCorrectResult() {
		int actual = findMaxNumberController.findMaxNumber(3,4,5).getMaxValue();
		assertEquals(5, actual);
	}
}
