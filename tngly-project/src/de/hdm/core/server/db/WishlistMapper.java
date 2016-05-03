package de.hdm.core.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm.core.shared.bo.Wishlist;
import de.hdm.core.shared.bo.Profile;

public class WishlistMapper {

	

	/**
	 * Mapper-Klasse, die <code>Customer</code>-Objekte auf eine relationale
	 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
	 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
	 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
	 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
	 * <p>
	 * 
	 * <b>Hinweis:</b> Diese Klasse ist analog zur Klasse <code>AccountMapper</code>
	 * implementiert.
	 * 
	 * @see AccountMapper, TransactionMapper
	 * @author Thies
	 */
	  public static WishlistMapper getWishlistMapper() {
			return wishlistMapper;
		}

		public static void setWishlistMapper(WishlistMapper wishlistMapper) {
			WishlistMapper.wishlistMapper = wishlistMapper;
		}

	/**
	   * Die Klasse CustomerMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @see accountMapper()
	   */
		  private static WishlistMapper wishlistMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit new neue
	   * Instanzen dieser Klasse zu erzeugen.
	   * 
	   */
	  protected WishlistMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>CustomerMapper.customerMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>CustomerMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> CustomerMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>CustomerMapper</code>-Objekt.
	   * @see customerMapper
	   */
	  public static WishlistMapper wishlistMapper() {
	    if (wishlistMapper == null) {
	    	wishlistMapper = new WishlistMapper();
	    }

	    return wishlistMapper;
	  }

	  /**
	   * Suchen eines Kunden mit vorgegebener Kundennummer. Da diese eindeutig ist,
	   * wird genau ein Objekt zur�ckgegeben.
	   * 
	   * @param id Primärschlüsselattribut (->DB)
	   * @return Kunden-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	   *         nicht vorhandenem DB-Tupel.
	   */
	}

