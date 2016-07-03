package de.hdm.editor.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diese Klasse ist die Basisklasse aller Views. Jede View ist ein VerticalPanel
 * und laesst sich somit unter GWT entsprechend anordnen.
 * 
 * @author Peter Thies
 */
public abstract class Update extends VerticalPanel {

	/**
	 * Jedes GWT Widget muss diese Methode implementieren. Sie gibt an, was
	 * geschehen soll, wenn eine Widget-Instanz zur Anzeige gebracht wird.
	 */
	@Override
	public void onLoad() {
		super.onLoad();
		this.add(this.createHeadline(this.getHeadlineText()));
		this.run();
	}

	/**
	 * Mit Hilfe dieser Methoden erstellen wir aus einem String ein mittels CSS
	 * formatierbares HTML-Element.
	 * 
	 * @param text
	 *            der String, den wir als andernorts HTML setzen wollen
	 * @return GWT HTML Widget
	 */
	protected HTML createHeadline(String text) {
		HTML content = new HTML(text);
		content.setStylePrimaryName("tngly-headline");
		return content;
	}

	/**
	 * Mit Hilfe dieser Methoden erstellen wir aus einem String ein mittels CSS
	 * formatierbares HTML-Element, das an das Ende der bisherigen Ausgabe
	 * dieser View angehaengt wird.
	 * 
	 * @param text
	 *            der String, den wir als HTML an die bisherige View-Ausgabe
	 *            anhaengen wollen
	 */
	protected void append(String text) {
		HTML content = new HTML(text);
		content.setStylePrimaryName("tngly-simpletext");
		this.add(content);
	}

	/**
	 * Abstrakte Einschubmethode, die in den Subklassen zu realisieren ist.
	 * 
	 * @return der Text, den wir als Headline setzen wollen.
	 */
	protected abstract String getHeadlineText();

	/**
	 * Abstrakte Einschubmethode, die in den Subklassen zu realisieren ist.
	 */
	protected abstract void run();
}
