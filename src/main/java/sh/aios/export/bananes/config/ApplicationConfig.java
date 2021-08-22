package sh.aios.export.bananes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import sh.aios.export.bananes.domain.port.OrderManagement;
import sh.aios.export.bananes.domain.port.OrderRepository;
import sh.aios.export.bananes.domain.port.RecipientManagement;
import sh.aios.export.bananes.domain.port.RecipientRepository;
import sh.aios.export.bananes.domain.service.OrderService;
import sh.aios.export.bananes.domain.service.RecipientService;

@Configuration
public class ApplicationConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Bananes Export API").description("Description des apis mises à disposition pour Bananes Export."));
	}

	@Bean
	public OrderManagement orderManagement(OrderRepository orderRepository) {
		return new OrderService(orderRepository);
	}

	@Bean
	public RecipientManagement recipientManagement(RecipientRepository recipientRepository) {
		return new RecipientService(recipientRepository);
	}
}