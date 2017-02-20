package dev.flash.tilegame.ui.objects;

import dev.flash.tilegame.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {
	
	private Handler handler;
	private ArrayList<UIObject> objects;
	private UIObject activeCursor;
	private UIObject uiCommandRectangle;
	
	private UIObject nextActiveCursor;
	private boolean updateActiveCursor = false;
	
	private UIObject nextUICommandRectangle;
	private boolean updateUICommandRectangle = false;
	
	public UIManager(Handler handler, UIObject activeCursor) {
		this.handler = handler;
		this.activeCursor = activeCursor;
		objects = new ArrayList<UIObject>();
	}
	
	public void tick() {
		if (updateActiveCursor) {
			activeCursor = nextActiveCursor;
			updateActiveCursor = false;
		}
		if (updateUICommandRectangle) {
			removeObject(uiCommandRectangle);
			uiCommandRectangle = nextUICommandRectangle;
			addObject(uiCommandRectangle);
			updateUICommandRectangle = false;
		}
		for (UIObject o : objects) {
			o.tick();
		}
		activeCursor.tick();
	}
	
	public void render(Graphics g) {
		for (UIObject o : objects) {
			o.render(g);
		}
		activeCursor.render(g);
	}
	
	public void onMouseMove(MouseEvent e) {
		for (UIObject o : objects) {
			o.onMouseMove(e);
		}
		activeCursor.onMouseMove(e);
	}
	
	public void onMousePressed(MouseEvent e, int button) {
		for (UIObject o : objects) {
			o.onMousePressed(e, button);
		}
		activeCursor.onMousePressed(e, button);
	}
	
	public void onMouseRelease(MouseEvent e, int button) {
		for (UIObject o : objects) {
			o.onMouseRelease(e, button);
		}
		activeCursor.onMouseRelease(e, button);
	}
	
	public void addObject(UIObject o) {
		objects.add(o);
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public ArrayList<UIObject> getObjects() {
		return objects;
	}
	
	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
	
	public UIObject getActiveCursor() {
		return activeCursor;
	}
	
	public void setActiveCursor(UIObject activeCursor) {
		if (this.activeCursor == null) {
			this.activeCursor = activeCursor;
		} else {
			updateActiveCursor = true;
			nextActiveCursor = activeCursor;
		}
	}
	
	public UIObject getUiCommandRectangle() {
		return uiCommandRectangle;
	}
	
	public void setUICommandBox(UIObject uiCommandRectangle) {
		if (this.uiCommandRectangle == null) {
			addObject(uiCommandRectangle);
			this.uiCommandRectangle = uiCommandRectangle;
		} else {
			updateUICommandRectangle = true;
			nextUICommandRectangle = uiCommandRectangle;
		}
		
	}
}