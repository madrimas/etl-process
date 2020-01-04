package com.etl.process.load;

import com.etl.process.load.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EtlProcessLoadApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void givenAnnotatedRecord_thenHasGettersAndSetters() {
		Product product = new Product();
		product.setId(1L);
		assertEquals(product.getId(), 1L);
	}

}
