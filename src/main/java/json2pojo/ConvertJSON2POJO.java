package json2pojo;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class ConvertJSON2POJO {

	public static void main(String[] args) throws IOException {
		JCodeModel codeModel = new JCodeModel();

		URL source = ConvertJSON2POJO.class.getResource("/test.json");

		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}
		};

		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, "Predicate", "test.pojo", source);

		codeModel.build(Files.createTempDirectory("required").toFile());
	}

}
