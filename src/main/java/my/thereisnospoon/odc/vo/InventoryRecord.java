package my.thereisnospoon.odc.vo;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Table(keyspace = "odc", name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InventoryRecord {

	@PartitionKey(0)
	private String sku;

	@PartitionKey(1)
	private String storeId;

	private int value;
	private int threshold;
}
