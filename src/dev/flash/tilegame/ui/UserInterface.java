package dev.flash.tilegame.ui;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.ui.objects.ClickListener;
import dev.flash.tilegame.ui.objects.UIManager;
import dev.flash.tilegame.ui.objects.UIMouseCursor;

import java.awt.*;

public abstract class UserInterface {
	
	private static UserInterface currentUserInterface = null;
	
	private static boolean updateUserInterface;
	
	private static UserInterface nextUserInterface;
	
	protected Handler handler;
	
	private UIMouseCursor defaultCursor;
	
	public UserInterface(Handler handler) {
		this.handler = handler;
		setupUIMouseCursors();
	}
	
	public void tick() {
		if (updateUserInterface) {
			currentUserInterface = nextUserInterface;
			currentUserInterface.setUIManager(currentUserInterface.getUIManager());//refreshes mouse manager uiManager
			updateUserInterface = false;
		}
		currentUserInterface.getUIManager().tick();
	}
	
	public void render(Graphics g) {
		currentUserInterface.getUIManager().render(g);
	}
	
	private void setupUIMouseCursors() {
		setDefaultCursor(new UIMouseCursor(handler, 0, 0, 22, 22, Assets.selection_cross_green, "defaultCursor", new ClickListener() {
			@Override
			public void onClick(int button) {
			}
			
			@Override
			public void pressed(int button) {
			}
		}) {
			@Override
			public void doTick() {
				this.setX(handler.getMouseManager().getMouseX() - 11);
				this.setY(handler.getMouseManager().getMouseY() - 11);
			}
		});
	}
	
	public static void setUserInterface(UserInterface userInterface) {
		if (currentUserInterface == null) {
			currentUserInterface = userInterface;
			currentUserInterface.setUIManager(currentUserInterface.getUIManager());//refreshes mouse manager uiManager
		} else {
			updateUserInterface = true;
			nextUserInterface = userInterface;
		}
	}
	
	public static UserInterface getUserInterface() {
		return currentUserInterface;
	}
	
	public abstract UIManager getUIManager();
	
	public abstract void setUIManager(UIManager uiManager);
	
	public UIMouseCursor getDefaultCursor() {
		return defaultCursor;
	}
	
	public void setDefaultCursor(UIMouseCursor defaultCursor) {
		this.defaultCursor = defaultCursor;
	}
	
}
