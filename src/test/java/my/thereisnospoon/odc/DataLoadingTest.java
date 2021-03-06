package my.thereisnospoon.odc;

import com.datastax.driver.mapping.Mapper;
import com.google.common.collect.ImmutableSet;
import my.thereisnospoon.odc.vo.InventoryRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DataLoadingTest {

	private static final Set<String> STORE_IDS = ImmutableSet.of("3109", "7109", "484", "494");

	@Autowired
	private Mapper<InventoryRecord> mapper;

	@Value("${data.pathToCsv}")
	private String pathToCsv;

	private int counter;

	@Test
	public void loadData() throws IOException {

		Files.lines(Paths.get(pathToCsv))
				.flatMap(line -> Stream.of(line.split("\\W+"))).
				forEach(sku ->
						STORE_IDS.forEach(storeId -> mapper.save(InventoryRecord.builder()
								.sku(sku)
								.storeId(storeId)
								.value(99)
								.threshold(88)
								.build())));
	}

	@Test
	public void load1MRecords() {

		for (int i = 1_000_000; i < 3_000_000; i++) {

			if (i % 10_000 == 0) {
				System.out.println("Storing sku: " + i);
			}

			for (String storeId: STORE_IDS) {
				mapper.save(InventoryRecord.builder()
						.sku("" + i)
						.storeId(storeId)
						.value(99)
						.threshold(77)
						.build());
			}
		}
	}
}