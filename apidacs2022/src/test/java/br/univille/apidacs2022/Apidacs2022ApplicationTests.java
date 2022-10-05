package br.univille.apidacs2022;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonpCharacterEscapes;

import br.univille.apidacs2022.api.CidadeControllerAPI;
import br.univille.apidacs2022.api.ConsultaControllerAPI;
import br.univille.apidacs2022.api.MedicoControllerAPI;
import br.univille.apidacs2022.api.PacienteControllerAPI;
import br.univille.apidacs2022.api.ProcedimentoControllerAPI;

@SpringBootTest
@AutoConfigureMockMvc
class Apidacs2022ApplicationTests {
	
	@Autowired
	private PacienteControllerAPI pacienteControllerAPI;
	
	@Autowired
	private MedicoControllerAPI medicoControllerAPI;
	
	@Autowired
	private ConsultaControllerAPI consultaControllerAPI;
	
	@Autowired
	private ProcedimentoControllerAPI procedimentoControllerAPI;
	
	@Autowired
	private CidadeControllerAPI cidadeControllerAPI;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
		assertThat(pacienteControllerAPI).isNotNull();
		assertThat(medicoControllerAPI).isNotNull();
		assertThat(consultaControllerAPI).isNotNull();
		assertThat(procedimentoControllerAPI).isNotNull();
		assertThat(cidadeControllerAPI).isNotNull();
	}

	//Teste paciente
	@Test
	void pacienteControllerAPIPOSTGETTest() throws Exception{
		MvcResult resultAuth = 
		mockMvc.perform(post("/api/v1/auth/signin")
			.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String jwtToken = resultAuth.getResponse().getContentAsString();

		MvcResult result = 
		mockMvc.perform(post("/api/v1/pacientes")
			.content("{\"nome\":\"Zezinho\",\"sexo\":\"Masculino\"}")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/pacientes/" + objJson.getString("id"))
			.header("Authorization", "Bearer " + jwtToken)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("Zezinho")))
			.andExpect(jsonPath("$.sexo", is("Masculino")));
	}

	//Teste medico
	@Test
	void medicoControllerAPIPOSTGETTest() throws Exception{
		MvcResult resultAuth = 
		mockMvc.perform(post("/api/v1/auth/signin")
			.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String jwtToken = resultAuth.getResponse().getContentAsString();

		MvcResult result = 
		mockMvc.perform(post("/api/v1/medicos")
			.content("{\"nome\":\"Zezinho\",\"crm\":\"1234\"}")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/medicos/" + objJson.getString("id"))
			.header("Authorization", "Bearer " + jwtToken)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("Zezinho")))
			.andExpect(jsonPath("$.crm", is("1234")));
	}

	//Teste procedimento
	@Test
	void procedimentoControllerAPIPOSTGETTest() throws Exception{
		MvcResult resultAuth = 
		mockMvc.perform(post("/api/v1/auth/signin")
			.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String jwtToken = resultAuth.getResponse().getContentAsString();

		MvcResult result = 
		mockMvc.perform(post("/api/v1/procedimentos")
			.content("{\"descricao\":\"vasectomia\"}")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/procedimentos/" + objJson.getString("id"))
			.header("Authorization", "Bearer " + jwtToken)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.descricao", is("vasectomia")));
	}

	// //Teste consulta
	// @Test
	// void consultaControllerAPIPOSTGETTest() throws Exception{
	// 	MvcResult resultAuth = 
	// 	mockMvc.perform(post("/api/v1/auth/signin")
	// 		.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
	// 		.contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(status().isOk()).andReturn();
	// 	String jwtToken = resultAuth.getResponse().getContentAsString();

	// 	MvcResult resultPac = 
	// 	mockMvc.perform(post("/api/v1/pacientes")
	// 		.content("{\"nome\":\"Joao\",\"sexo\":\"Masculino\"}")
	// 		.header("Authorization", "Bearer " + jwtToken)
	// 		.contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(status().isCreated()).andReturn();

	// 	String resultStrPac = resultPac.getResponse().getContentAsString();
	// 	JSONObject objJsonPac = new JSONObject(resultStrPac);

	// 	MvcResult resultMed = 
	// 	mockMvc.perform(post("/api/v1/medicos")
	// 		.content("{\"nome\":\"Leonardo\",\"crm\":\"1234\"}")
	// 		.header("Authorization", "Bearer " + jwtToken)
	// 		.contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(status().isCreated()).andReturn();

	// 	String resultStrMed = resultMed.getResponse().getContentAsString();
	// 	JSONObject objJsonMed = new JSONObject(resultStrMed);		


	// 	MvcResult result = mockMvc.perform(post("/api/v1/consultas")
	// 		.content("{\"paciente\":{\"id\": " + objJsonPac.getString("id") + 
	// 		"},\"medicoResponsavel\":{\"id\":" + objJsonMed.getString("id") + "}}")
	// 		.header("Authorization", "Bearer " + jwtToken)
	// 		.contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(status().isCreated()).andReturn();

	// 	String resultStr = result.getResponse().getContentAsString();
	// 	JSONObject objJson = new JSONObject(resultStr);

	// 	mockMvc.perform(get("/api/v1/consultas/" + objJson.getString("id"))
	// 		.header("Authorization", "Bearer " + jwtToken)
	// 	)
	// 		.andExpect(status().isOk())
	// 		.andExpect(jsonPath("$.paciente.nome", is("Joao")))
	// 		.andExpect(jsonPath("$.medicoResponsavel.nome", is("Leonardo")));
	// }

	@Test
	void consultaControllerAPIPOSTGETTest() throws Exception {
		MvcResult resultAuth = mockMvc.perform(post("/api/v1/auth/signin")
				.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String jwtToken = resultAuth.getResponse().getContentAsString();

		MvcResult resultPac = mockMvc.perform(post("/api/v1/pacientes")
				.content("{\"nome\":\"Leonardo\",\"sexo\":\"Masculino\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStrPac = resultPac.getResponse().getContentAsString();
		JSONObject objJsonPac = new JSONObject(resultStrPac);

		MvcResult resultMed = mockMvc.perform(post("/api/v1/medicos")
				.content("{\"nome\":\"Walter\",\"crm\":\"1234\"}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStrMed = resultMed.getResponse().getContentAsString();
		JSONObject objJsonMed = new JSONObject(resultStrMed);

		MvcResult result = mockMvc.perform(post("/api/v1/consultas")
				.content("{\"paciente\":{\"id\": " + objJsonPac.getString("id") + 
					"},\"medicoResponsavel\":{\"id\":" + objJsonMed.getString("id") + "}}")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/consultas/" + objJson.getString("id"))
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.paciente.nome", is("Leonardo")))
				.andExpect(jsonPath("$.medicoResponsavel.nome", is("Walter")));
	}

	//Teste cidade
	@Test
	void cidadeControllerAPIPOSTGETTest() throws Exception{
		MvcResult resultAuth = 
		mockMvc.perform(post("/api/v1/auth/signin")
			.content("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String jwtToken = resultAuth.getResponse().getContentAsString();

		MvcResult result = 
		mockMvc.perform(post("/api/v1/cidades")
			.content("{\"nome\":\"São Mateus do Sul\"}")
			.header("Authorization", "Bearer " + jwtToken)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated()).andReturn();

		String resultStr = result.getResponse().getContentAsString();
		JSONObject objJson = new JSONObject(resultStr);

		mockMvc.perform(get("/api/v1/cidades/" + objJson.getString("id"))
			.header("Authorization", "Bearer " + jwtToken)
		)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", is("São Mateus do Sul")));
	}

}