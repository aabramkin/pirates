package nl.abramkin.pirates.storage;

import org.apache.commons.csv.*;
import org.slf4j.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by aabramkin on 05/03/16.
 */
public class PriceStorage implements IPriceStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceStorage.class);

    private final String path;
    private List<PriceSource> priceSources;

    public PriceStorage(String path) {
        this.path = path;
    }

    public void refresh(){
        if(path == null || path.isEmpty()) {
            throw new RuntimeException("The path for data sources should be set properly");
        }
        List<PriceSource> sources = new ArrayList<PriceSource>();
        priceSources = null;
        File cvsData = new File(path);
        try {
            LOGGER.info("Parse prices");
            CSVParser parser = CSVParser.parse(cvsData, Charset.defaultCharset(), CSVFormat.EXCEL.withDelimiter(';'));
            for(CSVRecord record : parser) {
                try {
                    if(record.getRecordNumber() == 1) {
                        continue;
                    }
                    PriceSource priceSource = new PriceSource.PriceSourceBuilder().
                            setName(record.get(0)).
                            setSize(Integer.parseInt(record.get(1))).
                            setAvgPrice(Double.parseDouble(record.get(2))).
                            setMinSize(Integer.parseInt(record.get(3))).
                            setStepSize(Integer.parseInt(record.get(4))).build();
                    sources.add(priceSource);
                    LOGGER.info(priceSource.toString());
                } catch (NumberFormatException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to parse file '%1$s'", path));
        }
        priceSources = Collections.unmodifiableList(sources);
    }

    public List<PriceSource> getPriceSources() {
        if(priceSources == null) {
            refresh();
        }
        return priceSources;
    }
}
