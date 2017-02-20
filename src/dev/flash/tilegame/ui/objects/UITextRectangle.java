package dev.flash.tilegame.ui.objects;

import dev.flash.tilegame.Handler;

import java.awt.*;
import java.util.ArrayList;

public class UITextRectangle extends UIObject {
	private Color hoverColor, noHoverColor;
	private ArrayList<Text> texts;
	protected boolean centerText = true;
	
	public UITextRectangle(Handler handler, float x, float y, int width, int height, Color hoverColor, Color noHoverColor) {
		super(handler, x, y, width, height);
		this.hoverColor = hoverColor;
		this.noHoverColor = noHoverColor;
		texts = new ArrayList<Text>();
	}
	
	@Override
	public void tick() {
		doTick();
	}
	
	//Method created to be overwritten
	public void doTick() {
		//template method, is empty
	}
	
	@Override
	public void render(Graphics g) {
		if (hovering) {
			g.setColor(hoverColor);
			g.fillRect((int) x, (int) y, width, height);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		} else {
			g.setColor(noHoverColor);
			g.fillRect((int) x, (int) y, width, height);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
		for (Text t : texts) {
			g.setColor(t.getColor());
			Font currentFont = g.getFont();
			Font newFont = currentFont.deriveFont(t.getSize());
			g.setFont(newFont);
			if (centerText) {
				g.drawString(t.getString(), bounds.x + t.getX() - g.getFontMetrics().stringWidth(t.getString()) / 2, (int) (bounds.y + t.getY() + t.getSize() / 4));
			} else {
				g.drawString(t.getString(), bounds.x + t.getX(), (int) (bounds.y + t.getY() + t.getSize() / 4));
				
			}
		}
	}
	
	public ArrayList<Text> getTexts() {
		return texts;
	}
	
	public void setTexts(ArrayList<Text> texts) {
		this.texts = texts;
	}
	
	@Override
	public void onClick(int button) {
	}
	
	@Override
	public void pressed(int button) {
	}
	
	public void addText(Text text) {
		texts.add(text);
	}
	
	public void removeText(Text text) {
		texts.remove(text);
	}
	
	
}
