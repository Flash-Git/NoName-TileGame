package dev.flash.tilegame.ui;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.states.State;
import dev.flash.tilegame.ui.objects.ClickListener;
import dev.flash.tilegame.ui.objects.UIImageButton;
import dev.flash.tilegame.ui.objects.UIManager;

public class WorldEditorUserInterface extends UserInterface {
	
	
	Handler handler;
	
	private UIManager uiManager;
	private UIManager worldEditorMenuUIManager;
	private UIManager worldEditorUIManager;
	
	
	public WorldEditorUserInterface(Handler handler) {
		super(handler);
		this.handler = handler;
		setupWorldEditorMenuUIManager();
		setupWorldEditorUIManager();
		setUIManager(worldEditorUIManager);
	}
	
	
	private void setupWorldEditorMenuUIManager() {
		worldEditorMenuUIManager = new UIManager(handler, getDefaultCursor());
		
		worldEditorMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 4 * 32, 256, 64, Assets.button_spriteViewer, new ClickListener() {
			@Override
			public void onClick(int button) {
				setUIManager(worldEditorUIManager);
				handler.getRuleManager().getRule("paused").setBoolVar(false);
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		worldEditorMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 6 * 32, 256, 64, Assets.button_menu, new ClickListener() {
			@Override
			public void onClick(int button) {
				State.setState(handler.getGame().getMenuState());
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		worldEditorMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 8 * 32, 256, 64, Assets.button_options, new ClickListener() {
			@Override
			public void onClick(int button) {
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
	}
	
	private void setupWorldEditorUIManager() {
		worldEditorUIManager = new UIManager(handler, getDefaultCursor());
		
		worldEditorUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 288 - 32, handler.getHeight() / 2 - 32, 2 * 32, 2 * 32, Assets.button_left, new ClickListener() {
			@Override
			public void onClick(int button) {
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
	
	
	public UIManager getWorldEditorMenuUIManager() {
		return worldEditorMenuUIManager;
	}
	
	
	public void setWorldEditorMenuUIManager(UIManager worldEditorMenuUIManager) {
		this.worldEditorMenuUIManager = worldEditorMenuUIManager;
	}
	
	
	public UIManager getWorldEditorUIManager() {
		return worldEditorUIManager;
	}
	
	
	public void setWorldEditorUIManager(UIManager worldEditorUIManager) {
		this.worldEditorUIManager = worldEditorUIManager;
	}
	
}
