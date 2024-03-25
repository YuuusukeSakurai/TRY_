package jp.co.nexus;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

public class CsvDataSetLoader extends AbstractDataSetLoader {
	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		return new CsvDataSet(resource.getFile());
	}
}
