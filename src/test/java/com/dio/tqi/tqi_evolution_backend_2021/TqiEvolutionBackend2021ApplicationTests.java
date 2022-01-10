package com.dio.tqi.tqi_evolution_backend_2021;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TqiEvolutionBackend2021ApplicationTests {
	@Test
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}

}
