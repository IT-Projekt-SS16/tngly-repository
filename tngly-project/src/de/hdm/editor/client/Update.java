package de.hdm.editor.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class Update extends VerticalPanel{

	  @Override
	public void onLoad() {
	 
	    super.onLoad();

	    this.add(this.createHeadline(this.getHeadlineText()));

	    this.run();
	  }

	 
	  protected HTML createHeadline(String text) {
	    HTML content = new HTML(text);
	    content.setStylePrimaryName("tngly-headline");
	    return content;
	  }

	  
	  protected void append(String text) {
	    HTML content = new HTML(text);
	    content.setStylePrimaryName("tngly-simpletext");
	    this.add(content);
	  }

	  protected abstract String getHeadlineText();

	  protected abstract void run();
	}


