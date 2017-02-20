package dev.flash.tilegame.ui.objects;

import dev.flash.tilegame.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UIRectangleContainer extends UIObject {
	
	private Color hoverColor, noHoverColor;
	private ArrayList<UIObject> objects;
	private UIObject oldCursor;
	private BufferedImage[] portrait = null;
	private UIImageButton portraitObj;
	
	public UIRectangleContainer(Handler handler, float x, float y, int width, int height, Color hoverColor, Color noHoverColor) {
		super(handler, x, y, width, height);
		this.hoverColor = hoverColor;
		this.noHoverColor = noHoverColor;
		objects = new ArrayList<UIObject>();
	}
	
	public UIRectangleContainer(Handler handler, float x, float y, int width, int height, Color noHoverColor, BufferedImage[] image) {
		super(handler, x, y, width, height);
		this.hoverColor = noHoverColor;
		this.noHoverColor = noHoverColor;
		objects = new ArrayList<UIObject>();
		portrait = image;
	}
	
	@Override
	public void tick() {
		for (UIObject o : objects) {
			o.tick();
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		if (hovering) {
			g.setColor(hoverColor);
			g.fillRect((int) x, (int) y, width, height);
			
		} else {
			g.setColor(noHoverColor);
			g.fillRect((int) x, (int) y, width, height);
		}
		for (UIObject o : objects) {
			o.render(g);
		}
	}
	
	public void onMouseMove(MouseEvent e) {
		if (bounds.contains(e.getX(), e.getY())) {
			if (hovering == false) {
				oldCursor = handler.getMouseManager().getUIManager().getActiveCursor();
			}
			hovering = true;
			for (UIObject o : objects) {
				o.onMouseMove(e);
			}
			handler.getMouseManager().getUIManager().setActiveCursor(handler.getUserInterface().getDefaultCursor());
		} else {
			if (hovering == true) {
				handler.getMouseManager().getUIManager().setActiveCursor(oldCursor);
			}
			hovering = false;
		}
		
	}
	
	public void onMousePressed(MouseEvent e, int button) {
		if (hovering) {
			for (UIObject o : objects) {
				o.onMousePressed(e, button);
			}
		}
	}
	
	public void onMouseRelease(MouseEvent e, int button) {
		if (hovering) {
			for (UIObject o : objects) {
				o.onMouseRelease(e, button);
			}
		}
	}
	
	public UIObject getOldCursor() {
		return oldCursor;
	}
	
	public void setOldCursor(UIObject oldCursor) {
		this.oldCursor = oldCursor;
	}
	
	public void addObject(UIObject o) {
		objects.add(o);
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}
	
	@Override
	public void onClick(int button) {
	}
	
	@Override
	public void pressed(int button) {
	}
	
	public UIImageButton getPortraitObj() {
		return portraitObj;
	}
	
	public void setPortraitObj(UIImageButton portraitObj) {
		if (this.portraitObj == null) {
			objects.add(portraitObj);
			portraitObj.setImages(portrait);
		} else {
			objects.remove(this.portraitObj);
			objects.add(portraitObj);
			portraitObj.setImages(portrait);
			
		}
		this.portraitObj = portraitObj;
		
	}
}
