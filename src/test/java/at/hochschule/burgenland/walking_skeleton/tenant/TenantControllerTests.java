package at.hochschule.burgenland.walking_skeleton.tenant;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TenantControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsTenantWithValidName() throws Exception {
        mockMvc
                .perform(
                        post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Tenant123\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/tenants/[0-9a-f-]{36}")))
                .andExpect(jsonPath("$.id").value(matchesPattern("[0-9a-f-]{36}")))
                .andExpect(jsonPath("$.name").value("Tenant123"));
    }

    @Test
    void allowsDuplicateTenantNames() throws Exception {
        mockMvc
                .perform(
                        post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Tenant123\"}"))
                .andExpect(status().isCreated());

        mockMvc
                .perform(
                        post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Tenant123\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void rejectsMissingTenantName() throws Exception {
        mockMvc
                .perform(post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsNullTenantRequest() throws Exception {
        mockMvc
                .perform(post("/tenants").contentType(MediaType.APPLICATION_JSON).content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsTooShortTenantName() throws Exception {
        mockMvc
                .perform(post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"AB\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsTooLongTenantName() throws Exception {
        mockMvc
                .perform(
                        post("/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"ABCDEFGHIJKLMNOPQRSTU\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsTenantNameWithSpecialCharacters() throws Exception {
        mockMvc
                .perform(
                        post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Tenant-1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsTenantNameWithUmlauts() throws Exception {
        mockMvc
                .perform(
                        post("/tenants").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Müller\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsTenantNameWithSpaces() throws Exception {
        mockMvc
                .perform(
                        post("/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"Tenant 1\"}"))
                .andExpect(status().isBadRequest());
    }
}