package frostillicus.dtdl.app.model.issues;

import java.util.List;

import frostillicus.dtdl.app.model.info.InfoHolder;

public interface IssueProvider<T extends InfoHolder> {
	List<Issue> getIssues(T info);
}
