package dev.flash.tilegame.ui;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.states.State;
import dev.flash.tilegame.ui.objects.ClickListener;
import dev.flash.tilegame.ui.objects.UIImageButton;
import dev.flash.tilegame.ui.objects.UIManager;

public class MenuUserInterface extends UserInterface {
	
	Handler handler;
	
	private UIManager uiManager;
	private UIManager mainMenuUIManager;
	
	
	public MenuUserInterface(Handler handler) {
		super(handler);
		this.handler = handler;
		setupMainMenuUIManager();
		setUIManager(mainMenuUIManager);
	}
	
	private void setupMainMenuUIManager() {
		mainMenuUIManager = new UIManager(handler, getDefaultCursor());
		
		mainMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 4 * 32, 256, 64, Assets.button_start, new ClickListener() {
			@Override
			public void onClick(int button) {
				State.setState(handler.getGame().getGameState());
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		mainMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 6 * 32, 256, 64, Assets.button_spriteViewer, new ClickListener() {
			@Override
			public void onClick(int button) {
				State.setState(handler.getGame().getSpriteViewerState());
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		mainMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 8 * 32, 256, 64, Assets.button_options, new ClickListener() {
			@Override
			public void onClick(int button) {
				State.setState(handler.getGame().getMenuState());
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
	}
	
	
	public UIManager getUIManager() {
		return uiManager;
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
		handler.getMouseManager().setUIManager(uiManager);
	}
	
}
