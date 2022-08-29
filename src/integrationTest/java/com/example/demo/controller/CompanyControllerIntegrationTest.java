package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.utils.FileUtil.readResourceAsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
public class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String OFFICERS_ENDPOINT = "/company";
    private static final String OFFICERS_ENDPOINT_PARAM = "onlyActive";

    @Test
    public void requestCompany() throws Exception {
        mockMvc
        .perform(
                get(OFFICERS_ENDPOINT)
                .contentType(APPLICATION_JSON_VALUE)
                .headers(generateHeaders())
                .param(OFFICERS_ENDPOINT_PARAM, "true")
                .content(generateBody("BBC LIMITED", "06500244"))
        )
        .andExpect(status().isOk())
        .andExpect(content().json(readResourceAsString("/responses/responseCompany06500244.json"))); //MockMvcResultMatchers static class to find the other methods
    }

    @Test
    public void requestCompanyWithOnlyActiveTrueAndInactiveCompany() throws Exception {
        String expectedResponse = "{\n    \"items\": [],\n    \"total_results\": 0\n}";

        mockMvc
                .perform(
                        get(OFFICERS_ENDPOINT)
                                .contentType(APPLICATION_JSON_VALUE)
                                .headers(generateHeaders())
                                .param(OFFICERS_ENDPOINT_PARAM, "true")
                                .content(generateBody("BBC LIMITED", "13171403"))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse)
                );
    }

    private HttpHeaders generateHeaders() {
        var headers = new HttpHeaders();
        headers.set("x-api-key", "IoVV8jaWtJX5CRRIYqyR542r9Q2SzvU17XCDZun8");
        return headers;
    }

    private String generateBody(String companyName, String companyNumber){
        return "{\n" +
                "    \"companyName\" : \"" + companyName + "\",\n" +
                "    \"companyNumber\" : \"" + companyNumber + "\"\n" +
                "}";

    }
}