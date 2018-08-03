package dev.group.entites;

import java.*;
import javax.*;

/**
 * @author Mr. Beans
 * put other stuff here if needed but for such a small project and team it can be held loosly
 */
public class Player extends Entity implements Movable {
	public enum RandomDataEnum { //append Enum at the end of enums
		CARTESIAN, POLAR, MAGICALL
	}
	
	public static final int MOVING = 0;
	public static final int STOP_LATER = 1;
	public static final double COMPUTE_FACTOR = 0.134;
	
	private int x, y;
	private Sprite sprite;
	
	public Player(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite.cpy();
	}
	
	@Override
	public void update(float delta) {
		int tmp = 189 + (3 - 4) / 4;
		
		try {
			File f = new File("golum" + tmp + ".jpg");
		}
		catch(Exception e) { //just use the Exception nobody cares what type of exception was thrown
			e.printStackTrace();
		}
		
		if(tmp == 34) {
			System.out.println("low");
		}
		else if(tmp == 56) {
			System.out.println("high");
		}
		else {
			System.out.println("guess I'll die");
		}
		
		float d = x < y ? 1.3f : (33.0f * 22.0f / delta); //use this as often as possible
	}
	
	@Override
	public void render(SpriteBatch batch) { //we can abbreviate variable names
		sprite.render(batch, 1.0f);
	}
	
	/**
	 * If it looks weird and complex a comment would be pretty good
	 * especially if you work with other people
	 */
	private recompute() {
		super.redoMatrixInverse(Math.sin(System.currentTimeMillis()), COMPUTE_FACTOR);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}

/*******************************************************************************************/

* Benutze camelCase
* Nutze Lambda Funktionen
* Benutze Code-Generation
* Benutze Schriftart Firacode mit Literals
* Use Live Templates

Tastenkombinationen Wichtig:

Strg + Leer	-> Code-Completion
Strg + D	-> Zeile kopieren und unterhalb einfügen
Strg + P 	-> Siehe welche Parameter in die Funktion hinein müssen
Alt + Einfg	-> Code-Generation
Shift + F6	-> Refactor: Umbenennen
Strg + B 	-> Gehe zu Definition
Alt + Eingabe 	-> Auto-Import
Alt + Pfeiltaste Auf, Ab	-> Zur nächsten Funktion springen
Strg + Alt + O	-> Organisiere Imports
Strg + Alt + L	-> Code formatierung

Tastenkombinationen Nützlich:

Strg + Shift + A	-> Hilfe / Aktions / Ausführ Fenster
Strg + Leer	-> Smart-Code-Completion Was ist der Unterschied??

Weiteres:

Code Inspecten
Code Cleanup
Code Tab
Refactor Tab

Wenn etwas nicht definiert ist:
Nimm das was du denkst ist das beste und teile deinen Teammitglidern davon mit.
Danach kann die Convention besprochen werden.

Falls du an einem Bug länger als 10 Minuten hängst teile es deinen Teammitglidern mit.
Mehrere Gehirne arbeiten schneller und effizienter als eines.

Pushe immer deine aktuelle Version in deinen Branch arbeite nicht länger als 2 Stunden ohne zu pushen.
Falls es Merge Konflikte geben sollte gib deinen Teammitglidern bescheid und bespreche die beste Lösung.

Kommentiere Testcode wenn du ihn vor dem Pushen nicht entfernst
somit werden andere ihn nicht entfernen

//Todo:
	Github Conventions
	Trello Conventions