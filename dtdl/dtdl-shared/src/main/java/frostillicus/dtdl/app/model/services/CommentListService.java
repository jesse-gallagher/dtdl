package frostillicus.dtdl.app.model.services;

import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;
import javax.validation.constraints.NotNull;

import org.darwino.jnosql.diana.driver.EntityConverter;
import org.jnosql.artemis.Repository;
import org.jnosql.artemis.document.DocumentEntityConverter;

import com.darwino.commons.json.JsonObject;
import com.darwino.commons.services.AbstractHttpService;
import com.darwino.commons.services.HttpServiceContext;

import frostillicus.dtdl.app.model.Source;
import frostillicus.dtdl.app.model.info.InfoHolder;
import frostillicus.dtdl.app.model.issues.AbstractIssueProvider;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentListService extends AbstractHttpService {
	private final @NotNull String sourceId;
	private final @NotNull String issueId;
	
	@Override
	protected void doGet(HttpServiceContext context) throws Exception {
		@SuppressWarnings("unchecked")
		Repository<Source, Object> repository = (Repository<Source, Object>)ModelUtil.getRepository(Source.class);
		if(repository == null) {
			throw new NullPointerException("Could not find repository for class " + Source.class.getName()); //$NON-NLS-1$
		}

		DocumentEntityConverter documentEntityConverter = CDI.current().select(DocumentEntityConverter.class).get();
		
		Source source = repository.findById(sourceId).orElseThrow(() -> new RuntimeException("Could not find source for ID " + sourceId)); //$NON-NLS-1$
		AbstractIssueProvider<InfoHolder> issueProvider = source.getType().getIssueProvider();
		
		context.emitJson(JsonObject.of(
			"status", "success", //$NON-NLS-1$ //$NON-NLS-2$
			"payload", issueProvider.getComments(source.getInfoHolder(), issueId).stream() //$NON-NLS-1$
				.map(documentEntityConverter::toDocument)
				.map(EntityConverter::convert)
				.collect(Collectors.toList())
		));
	}
}
