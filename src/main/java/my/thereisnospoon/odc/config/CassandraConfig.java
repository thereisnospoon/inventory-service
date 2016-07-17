package my.thereisnospoon.odc.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import my.thereisnospoon.odc.vo.InventoryRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CassandraConfig {

	@Bean
	public Cluster cluster(@Value("${cassandra.contactPoint}") final String contactPoint) {

		return Cluster.builder()
				.addContactPoint(contactPoint)
				.build();
	}

	@Bean
	public Session session(final Cluster cluster) {
		return cluster.connect();
	}

	@Bean
	public MappingManager mappingManager(final Session session) {
		return new MappingManager(session);
	}

	@Bean
	@Scope("prototype")
	public Mapper<InventoryRecord> inventoryRecordMapper(final MappingManager mappingManager) {
		return mappingManager.mapper(InventoryRecord.class);
	}
}
