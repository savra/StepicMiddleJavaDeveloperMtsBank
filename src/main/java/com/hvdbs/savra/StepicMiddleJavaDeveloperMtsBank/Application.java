package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class Application {
	private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<>() {};

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(Application.class, args);
		Javers javers = JaversBuilder.javers().build();
		ObjectMapper objectMapper = new ObjectMapper();

		String s = objectMapper.writeValueAsString("");

		UUID uuid = UUID.randomUUID();
		Entity1 entity1 = new Entity1();
		entity1.uuid = uuid;
		entity1.title = "title1";
		entity1.key = "key";
		entity1.key2 = "key2";
		entity1.key3 = "key3";

		Entity2 entity2 = new Entity2();
		entity2.uuid = uuid;
		entity2.title = "title1";
		entity2.key = "keyDIFF";

		Map<String, Object> old = objectMapper.convertValue(entity1, TYPE_REFERENCE);
		Map<String, Object> news = objectMapper.convertValue(entity2, TYPE_REFERENCE);
		List<Custom> diff = new ArrayList<>();

		for (Map.Entry<String, Object> entry : news.entrySet()) {
			Object oldValue = old.get(entry.getKey());

			if (entry.getValue() != null && !oldValue.equals(entry.getValue())) {
				diff.add(new Custom(entry.getKey(), (String) oldValue, (String) entry.getValue()));
			}
		}

	/*	Diff compare = javers.compare(entity1, entity2);
		List<ValueChange> list = compare.getChanges().stream()
				.filter(ValueChange.class::isInstance)
				.map(ValueChange.class::cast)
				.toList();*/

		int gg = 5;
	}

	static class Custom {
		String changeField;
		String oldValue;
		String newValue;

		public Custom(String changeField, String oldValue, String newValue) {
			this.changeField = changeField;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}
	}

	@Getter
	@Setter
	static class Entity1 {
		private UUID uuid;
		private String title;
		private String application;
		private String key;
		private String key2;
		private String key3;
		private String key4;
		private String key5;
	}

	@Getter
	@Setter
	static class Entity2 {
		private UUID uuid;
		private String title;
		private String application;
		private String key;
	}

}
