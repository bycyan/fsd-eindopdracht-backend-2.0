import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MyIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEndpoint1() throws Exception {
        // Schrijf een integratietest voor je eerste endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/endpoint1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Voeg andere verwachtingen toe, indien nodig
                .andReturn();
    }

    @Test
    public void testEndpoint2() throws Exception {
        // Schrijf een integratietest voor je tweede endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/endpoint2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Voeg andere verwachtingen toe, indien nodig
                .andReturn();
    }
}
