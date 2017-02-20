package dev.flash.tilegame.ui;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.states.State;
import dev.flash.tilegame.ui.objects.*;

import java.awt.*;

public class SpriteViewerUserInterface extends UserInterface {
	
	
	Handler handler;
	
	private UIManager uiManager;
	private UIManager spriteMenuUIManager;
	private UIManager spriteSelectorUIManager;
	
	
	public SpriteViewerUserInterface(Handler handler) {
		super(handler);
		this.handler = handler;
		setupSpriteMenuUIManager();
		setupSpriteSelectorUIManager();
		setUIManager(spriteSelectorUIManager);
	}
	
	
	private void setupSpriteMenuUIManager() {
		spriteMenuUIManager = new UIManager(handler, getDefaultCursor());
		
		spriteMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 4 * 32, 256, 64, Assets.button_spriteViewer, new ClickListener() {
			@Override
			public void onClick(int button) {
				setUIManager(spriteSelectorUIManager);
				handler.getRuleManager().getRule("paused").setBoolVar(false);
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		spriteMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 6 * 32, 256, 64, Assets.button_menu, new ClickListener() {
			@Override
			public void onClick(int button) {
				State.setState(handler.getGame().getMenuState());
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		spriteMenuUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 8 * 32, 256, 64, Assets.button_options, new ClickListener() {
			@Override
			public void onClick(int button) {
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
	}
	
	private void setupSpriteSelectorUIManager() {
		spriteSelectorUIManager = new UIManager(handler, getDefaultCursor());
		
		spriteSelectorUIManager.addObject(new UITextRectangle(handler, 64, handler.getHeight() / 2 - 256, handler.getWidth() - 128, 64, Color.GREEN, Color.GREEN) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.BLACK, 32));
				}
				for (Text t : this.getTexts()) {
					t.setString(handler.getSpriteViewerWorld().getSprite().getName());
				}
			}
		});
		
		spriteSelectorUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 288 - 32, handler.getHeight() / 2 - 32, 2 * 32, 2 * 32, Assets.button_left, new ClickListener() {
			@Override
			public void onClick(int button) {
				handler.getSpriteViewerWorld().lastSprite();
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		spriteSelectorUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 + 288 - 32, handler.getHeight() / 2 - 32, 2 * 32, 2 * 32, Assets.button_right, new ClickListener() {
			@Override
			public void onClick(int button) {
				handler.getSpriteViewerWorld().nextSprite();
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		spriteSelectorUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 32, handler.getHeight() / 2 + 128, 2 * 32, 2 * 32, Assets.button_zoom, new ClickListener() {
			@Override
			public void onClick(int button) {
				handler.getSpriteViewerWorld().zoom();
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		spriteSelectorUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 32, handler.getHeight() / 2 + 128 + 64, 2 * 32, 2 * 32, Assets.button_color, new ClickListener() {
			@Override
			public void onClick(int button) {
				handler.getSpriteViewerWorld().changeBColor();
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
	
	public UIManager getSpriteMenuUIManager() {
		return spriteMenuUIManager;
	}
	
	
	public void setSpriteMenuUIManager(UIManager spriteMenuUIManager) {
		this.spriteMenuUIManager = spriteMenuUIManager;
	}
	
	
	public UIManager getSpriteSelectorUIManager() {
		return spriteSelectorUIManager;
	}
	
	
	public void setSpriteSelectorUIManager(UIManager spriteSelectorUIManager) {
		this.spriteSelectorUIManager = spriteSelectorUIManager;
	}
	
	
}
