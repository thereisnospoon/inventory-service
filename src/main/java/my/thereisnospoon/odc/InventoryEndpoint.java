package my.thereisnospoon.odc;

import my.thereisnospoon.odc.vo.InventoryRequest;
import my.thereisnospoon.odc.vo.InventoryResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryEndpoint {

	@RequestMapping(method = RequestMethod.POST, path = "/bystore")
	public InventoryResponse getInventory(@RequestBody final InventoryRequest inventoryRequest) {
		return InventoryResponse.builder().build();
	}
}
