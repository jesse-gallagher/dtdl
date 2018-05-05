package frostillicus.dtdl.app.beans;

import javax.enterprise.context.ApplicationScoped;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@ApplicationScoped
public class MarkdownBean {

	private Parser markdown = Parser.builder().build();
	private HtmlRenderer markdownHtml = HtmlRenderer.builder().build();
	
	public String toHtml(String text) {
		Node parsed = markdown.parse(text);
		return markdownHtml.render(parsed);
	}
}
