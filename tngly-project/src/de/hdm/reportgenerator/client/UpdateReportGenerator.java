package de.hdm.reportgenerator.client;

import com.google.gwt.user.client.ui.*;

public abstract class UpdateReportGenerator extends VerticalPanel{

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


