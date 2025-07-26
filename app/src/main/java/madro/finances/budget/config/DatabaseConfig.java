package madro.finances.budget.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // print logs for debugging
        System.out.println("Initializing DatabaseConfig...");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String credentialsJson = environment.getProperty("DB_CREDENTIALS");

        if (credentialsJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode credentials = mapper.readTree(credentialsJson);
                
                Map<String, Object> properties = new HashMap<>();
                properties.put("database.credentials.username", credentials.get("username").asText());
                properties.put("database.credentials.password", credentials.get("password").asText());

                MapPropertySource propertySource = new MapPropertySource("databaseCredentials", properties);
                environment.getPropertySources().addFirst(propertySource);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse DB_CREDENTIALS", e);
            }
        }
    }
}
