package my.thereisnospoon.odc;

import com.datastax.driver.mapping.Mapper;
import my.thereisnospoon.odc.dto.InventoryRequest;
import my.thereisnospoon.odc.dto.InventoryResponse;
import my.thereisnospoon.odc.dto.RequestedInventory;
import my.thereisnospoon.odc.dto.Store;
import my.thereisnospoon.odc.vo.InventoryRecord;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.inject.Provider;
import java.math.BigInteger;
import java.util.Optional;

@RestController
public class InventoryEndpoint {

	@Inject
	private Provider<Mapper<InventoryRecord>> inventoryRecordMapperProvider;

	@RequestMapping(method = RequestMethod.POST, path = "/bystore")
	public InventoryResponse getInventory(@RequestBody final InventoryRequest inventoryRequest) {

		final Mapper<InventoryRecord> inventoryRecordMapper = inventoryRecordMapperProvider.get();
		final InventoryResponse inventoryResponse = new InventoryResponse();

		inventoryRequest.getStoreIds().getStoreId().forEach(storeId -> {

			Store store = new Store();
			store.setStoreId(storeId);

			inventoryRequest.getProductIds().getProductId().forEach(sku -> {

				Optional<InventoryRecord> inventoryRecord = Optional.ofNullable(inventoryRecordMapper.get(sku, storeId));

				store.getRequestedInventory().add(
						getRequestedInventory(
								inventoryRecord.orElseGet(() -> InventoryRecord.builder()
										.sku(sku)
										.storeId(storeId)
										.value(0)
										.threshold(0)
										.build())));
			});

			inventoryResponse.getStore().add(store);
		});

		return inventoryResponse;
	}

	private RequestedInventory getRequestedInventory(final InventoryRecord inventoryRecord) {

		RequestedInventory requestedInventory = new RequestedInventory();
		requestedInventory.setProductId(inventoryRecord.getSku());
		requestedInventory.setValue(BigInteger.valueOf(inventoryRecord.getValue()));
		requestedInventory.setThreshold(BigInteger.valueOf(inventoryRecord.getThreshold()));
		return requestedInventory;
	}
}
