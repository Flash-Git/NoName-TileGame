package dev.flash.tilegame.ui;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;
import dev.flash.tilegame.entities.projectiles.Projectile;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.entities.units.creatures.Creature;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.states.State;
import dev.flash.tilegame.ui.objects.*;

import java.awt.*;
import java.util.ArrayList;

public class GameUserInterface extends UserInterface {
	
	private UIManager uiManager;
	private UIManager pauseUIManager;
	private UIManager hudUIManager;
	
	private UIMouseCursor commandCursor;
	private UIMouseCursor selectionCursor;
	private UIMouseCursor combatCursor;
	
	private UIRectangleContainer uiDefaultCommandBox, uiDevCommandBox, uiBarracksCommandBox, uiBarracksCommandBox2, uiBuilderCommandBox, uiBuilderCommandBox2, uiOffUnitCommandBox;
	private UIRectangleContainer uiWizardCommandBox;
	private UIRectangleContainer uiZombieCommandBox;
	private UIRectangleContainer uiMudcrabCommandBox;
	private UIRectangleContainer uiTowerCommandBox;
	private UIRectangleContainer uiMagetowerCommandBox;
	private UIRectangleContainer uiWallCommandBox;
	
	public GameUserInterface(Handler handler) {
		super(handler);
		handler.setGameUserInterface(this);
		
		setupUIMouseCursors();
		setupPauseUIManager();
		setupHUDUIManager();
		
		setUIManager(hudUIManager);
	}
	
	private void setupUIMouseCursors() {
		combatCursor = new UIMouseCursor(handler, 0, 0, 16, 16, Assets.selection_cross_red, "combatCursor", new ClickListener() {
			@Override
			public void onClick(int button) {
				if (button == 1) {
				}
				if (button == 3) {
					Unit u = handler.getEntityManager().getCommanded();
					if (u != null) {
						if (u.isAlive()) {
							u.doCommand(0);
						}
					}
				}
			}
			
			@Override
			public void pressed(int button) {
				if (button == 1) {
					//System.out.println("combatCursor pressed() Left");
					
					Unit u = handler.getEntityManager().getCommanded();
					if (u.isAlive()) {
						int x = (int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset());
						int y = (int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset());
						u.attackCoords(x + 8, y + 8);//why do i need to add this? TODO
					}
				}
			}
		}) {
			@Override
			public void doTick() {
				this.setX(handler.getMouseManager().getMouseX() - this.width / 2);
				this.setY(handler.getMouseManager().getMouseY() - this.height / 2);
			}
		};
		
		commandCursor = new UIMouseCursor(handler, 0, 0, 32, 32, Assets.selection_cross_green, "commandCursor", new ClickListener() {
			@Override
			public void onClick(int button) {
				if (button == 1) {
					Unit u = handler.getEntityManager().getCommanded();
					if (u.isAlive()) {
						u.doCommand(commandCursor.getCommandNum());
					}
					if (u.isControlled()) {
						uiManager.setActiveCursor(combatCursor);
						return;
					}
					uiManager.setActiveCursor(selectionCursor);
					return;
				}
				if (button == 3) {
					Unit u = handler.getEntityManager().getCommanded();
					if (u != null) {
						if (u.isAlive()) {
							u.doCommand(0);
						}
					}
				}
			}
			
			@Override
			public void pressed(int button) {
			}
		}) {
			@Override
			public void doTick() {
				this.setX((int) ((handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset() % 32) / 32) * 32 - (int) handler.getGameCamera().getxOffset() % 32);
				this.setY((int) ((handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset() % 32) / 32) * 32 - (int) handler.getGameCamera().getyOffset() % 32);
			}
		};
		
		selectionCursor = new UIMouseCursor(handler, 0, 0, 32, 32, Assets.selection_cross_grey, "selectionCursor", new ClickListener() {
			@Override
			public void onClick(int button) {
				if (button == 1) {
					int x = (int) (selectionCursor.getX() + handler.getGameCamera().getxOffset());
					int y = (int) (selectionCursor.getY() + handler.getGameCamera().getyOffset());
					int width = selectionCursor.getWidth();
					int height = selectionCursor.getHeight();
					
					for (Entity e : handler.getEntityManager().getEntitiesInRect(x, y, width, height)) {
						e.select();
						
					}
				}
				if (button == 3) {
					Unit u = handler.getEntityManager().getCommanded();
					if (u != null) {
						if (u.isAlive()) {
							u.doCommand(0);
						}
					}
					
				}
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}) {
			@Override
			public void doTick() {
				this.setX((int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset() - handler.getGameCamera().getxOffset() - 16));
				this.setY((int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset() - handler.getGameCamera().getyOffset() - 16));
			}
		};
	}
	
	private void setupPauseUIManager() {
		pauseUIManager = new UIManager(handler, getDefaultCursor());
		
		//START
		pauseUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 4 * 32, 256, 64, Assets.button_start, new ClickListener() {
			@Override
			public void onClick(int button) {
				setUIManager(hudUIManager);
				handler.getRuleManager().getRule("paused").setBoolVar(false);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		//MENU
		pauseUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 6 * 32, 256, 64, Assets.button_menu, new ClickListener() {
			@Override
			public void onClick(int button) {
				State.setState(handler.getGame().getMenuState());
			}
			
			@Override
			public void pressed(int button) {
			}
		}));
		
		//OPTIONS
		pauseUIManager.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 128, 8 * 32, 256, 64, Assets.button_options, new ClickListener() {
			@Override
			public void onClick(int button) {
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
	}
	
	
	private void setupHUDUIManager() {
		hudUIManager = new UIManager(handler, selectionCursor);
		
		addStatBox();
		setupUICommandBoxes();
		hudUIManager.setUICommandBox(uiDefaultCommandBox);
		
		addFPSObject();
		addGoldObject();
		addHealthObject();
		addExpObject();
	}
	
	private void setupUICommandBoxes() {
		
		setupDefaultBox();
		setupDevBox();
		
		setupBarracksBox();
		setupBuilderBox();
		
		setupOffensiveCreatureBox();
		setupOffensiveBuildingBox();
		
	}
	
	private void addStatBox() {
		hudUIManager.addObject(new UITextRectangle(handler, 16, handler.getHeight() - 166, 256, 150, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				Entity e = handler.getEntityManager().getSelected();
				String hp = "null";
				String dmg = "null";
				String hpRegen = "null";
				String attRange = "null";
				String detRange = "null";
				String speed = "null";
				String attSpeed = "null";
				if (e instanceof Unit) {
					Unit c = (Unit) e;
					hp = Double.toString((double) c.getHealth());
					dmg = Double.toString((double) c.getDamage());
					hpRegen = Double.toString((double) c.getHealthRegen());
					attRange = Double.toString((double) c.getAttackRange());
					detRange = Double.toString((double) c.getDetectionRange());
					speed = Double.toString((double) c.getSpeed());
					attSpeed = Double.toString((double) c.getAttackSpeed());
				}
				if (e instanceof Projectile) {
					Projectile p = (Projectile) e;
					hp = Double.toString((double) p.getHealth());
					dmg = Double.toString((double) p.getDamage());
					speed = Double.toString((double) p.getSpeed());
				}
				
				if (this.getTexts().isEmpty()) {
					this.centerText = false;
					this.addText(new Text("Health: " + hp, 8, 32, Color.BLACK, 12));
					this.addText(new Text("Damage: " + dmg, 8, 64, Color.BLACK, 12));
					this.addText(new Text("Health Regen: " + hpRegen, 8, 96, Color.BLACK, 12));
					this.addText(new Text("Attack Range: " + attRange, 8, 128, Color.BLACK, 12));
					this.addText(new Text("Detection Range: " + detRange, this.getWidth() / 2 + 8, 32, Color.BLACK, 12));
					this.addText(new Text("Speed: " + speed, this.getWidth() / 2 + 8, 64, Color.BLACK, 12));
					this.addText(new Text("Attack Speed: " + attSpeed, this.getWidth() / 2 + 8, 96, Color.BLACK, 12));
				} else {
					if (hp.length() > 4) {
						hp = hp.substring(0, 4);
					}
					this.getTexts().get(0).setString("Health: " + hp);
					
					if (dmg.length() > 4) {
						dmg = dmg.substring(0, 4);
					}
					this.getTexts().get(1).setString("Damage: " + dmg);
					
					if (hpRegen.length() > 4) {
						hpRegen = hpRegen.substring(0, 4);
					}
					this.getTexts().get(2).setString("Health Regen: " + hpRegen);
					
					if (attRange.length() > 4) {
						attRange = attRange.substring(0, 4);
					}
					this.getTexts().get(3).setString("Attack Range: " + attRange);
					
					if (detRange.length() > 4) {
						detRange = detRange.substring(0, 4);
					}
					this.getTexts().get(4).setString("Detection Range: " + detRange);
					
					if (speed.length() > 4) {
						speed = speed.substring(0, 4);
					}
					this.getTexts().get(5).setString("Speed: " + speed);
					
					if (attSpeed.length() > 4) {
						attSpeed = attSpeed.substring(0, 4);
					}
					this.getTexts().get(6).setString("Attack Speed: " + attSpeed);
				}
			}
		});
		
		hudUIManager.addObject(new UITextRectangle(handler, 16, handler.getHeight() - 166 - 16, 256, 16, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2 + 16, Color.BLACK, 32));
				}
				for (Text t : this.getTexts()) {
					Entity e = handler.getEntityManager().getSelected();
					if (e != null) {
						t.setString(e.getType());
					}
				}
			}
		});
	}
	
	private void setupDefaultBox() {
		//DEFAULT UI BOX
		uiDefaultCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Color.GRAY);
//				uiDefaultCommandBox.addObject(new UIImageButton(handler, handler.getWidth()/2-256+53+16, handler.getHeight()-80-32, 64, 64, Assets.player_walking_down, new ClickListener(){
//					@Override
//					public void onClick(int button) {
//						((Creature) handler.getEntityManager().getSelected()).gainExp(1000);
//					}
//
//					@Override
//					public void pressed(int button) {
//
//					}}));
		
		uiDefaultCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 128, handler.getHeight() - 80 - 32, 64, 64, Assets.selection_cross_green, new ClickListener() {
			@Override
			public void onClick(int button) {
				if (uiDefaultCommandBox.getOldCursor() == selectionCursor) {
					uiDefaultCommandBox.setOldCursor(combatCursor);
				} else if (uiDefaultCommandBox.getOldCursor() == combatCursor) {
					uiDefaultCommandBox.setOldCursor(commandCursor);
				} else if (uiDefaultCommandBox.getOldCursor() == commandCursor) {
					uiDefaultCommandBox.setOldCursor(selectionCursor);
				}
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));

//				uiDefaultCommandBox.addObject(new UITextRectangle(handler, handler.getWidth()/2-256+53+16+256, handler.getHeight()-80-32, 64, 64, Color.GRAY, Color.GRAY){
//					@Override
//					public void doTick(){
//						if(this.getTexts().isEmpty()){
//							this.addText(new Text("", this.getWidth()/2, this.getHeight()/2, Color.BLACK, 32));
//						}
////						for(Text t : this.getTexts()){
////							t.setString(handler.getEntityManager().getSelected().getType());
////						}
//					}});
		
		uiDefaultCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384, handler.getHeight() - 80 - 32, 64, 64, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.BLACK, 16));
				}
				for (Text t : this.getTexts()) {
					t.setString(uiManager.getActiveCursor().getName());
				}
			}
		});
	}
	
	private void setupDevBox() {
		//Developper UI BOX
		uiDevCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Color.GRAY);
		uiDevCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, Assets.player_down, new ClickListener() {
			@Override
			public void onClick(int button) {
				((Creature) handler.getEntityManager().getSelected()).gainExp(1000);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiDevCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 128, handler.getHeight() - 80 - 32, 64, 64, Assets.selection_cross_green, new ClickListener() {
			@Override
			public void onClick(int button) {
				System.out.println(uiManager.getActiveCursor().getName());
				if (uiManager.getActiveCursor() == selectionCursor) {
					uiManager.setActiveCursor(combatCursor);
				} else if (uiManager.getActiveCursor() == combatCursor) {
					uiManager.setActiveCursor(commandCursor);
				} else if (uiManager.getActiveCursor() == commandCursor) {
					uiManager.setActiveCursor(selectionCursor);
				}
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));

//						uiDevCommandBox.addObject(new UITextRectangle(handler, handler.getWidth()/2-256+53+16+256, handler.getHeight()-80-32, 64, 64, Color.GRAY, Color.GRAY){
//							@Override
//							public void doTick(){
//								if(this.getTexts().isEmpty()){
//									this.addText(new Text("", this.getWidth()/2, this.getHeight()/2, Color.BLACK, 32));
//								}
////								for(Text t : this.getTexts()){
////									t.setString(handler.getEntityManager().getSelected().getType());
////								}
//							}});
		
		uiDevCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384, handler.getHeight() - 80 - 32, 64, 64, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.BLACK, 16));
				}
				for (Text t : this.getTexts()) {
					t.setString(uiManager.getActiveCursor().getName());
				}
			}
		});
	}
	
	private void setupBarracksBox() {
		//BARRACKS UI BOX
		uiBarracksCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Color.GRAY);
		uiBarracksCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, Assets.barracks_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				
				if (!u.isControlled()) {
					handler.getEntityManager().setControlled(u);
					
					uiBuilderCommandBox.setOldCursor(combatCursor);
				} else {
					Unit nullUnit = null;
					handler.getEntityManager().setControlled(nullUnit);//TODO
					uiBuilderCommandBox.setOldCursor(selectionCursor);
				}
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		
		//WIZARD
		uiBarracksCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32, 64, 64, Assets.wizard_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				if (u.isAlive()) {
					u.doCommand(1);
				}
				
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBarracksCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("75");
				}
			}
		});
		
		//BUILDER
		uiBarracksCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32, 64, 64, Assets.builder_down, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				if (u.isAlive()) {
					u.doCommand(2);
				}
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBarracksCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("100");
				}
			}
		});
		
		uiBarracksCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 288, handler.getHeight() - 80 - 32, 64, 64, Assets.selection_cross_black, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				u.die();
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		//SWITCH
		uiBarracksCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32, 16, 16, Assets.button_left, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBarracksCommandBox2);
				uiBarracksCommandBox2.setOldCursor(selectionCursor);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBarracksCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32 + 64, 16, 16, Assets.button_right, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBarracksCommandBox2);
				uiBarracksCommandBox2.setOldCursor(selectionCursor);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBarracksCommandBox2 = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Color.GRAY);
		uiBarracksCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, Assets.barracks_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				
				if (!u.isControlled()) {
					handler.getEntityManager().setControlled(u);
					
					uiBuilderCommandBox.setOldCursor(combatCursor);
				} else {
					Unit nullUnit = null;
					handler.getEntityManager().setControlled(nullUnit);//TODO
					uiBuilderCommandBox.setOldCursor(selectionCursor);
				}
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		
		//WIZARD
		uiBarracksCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32, 64, 64, Assets.wizard_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				if (u.isAlive()) {
					u.doCommand(1);
				}
				
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBarracksCommandBox2.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("75");
				}
			}
		});
		
		//KILL
		uiBarracksCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32, 64, 64, Assets.selection_cross_black, new ClickListener() {
			@Override
			public void onClick(int button) {
				Unit u = handler.getEntityManager().getCommanded();
				u.die();
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		//SWITCH
		uiBarracksCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32, 16, 16, Assets.button_left, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBarracksCommandBox);//TODO
				uiBarracksCommandBox.setOldCursor(selectionCursor);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBarracksCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32 + 64, 16, 16, Assets.button_right, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBarracksCommandBox);
				uiBarracksCommandBox.setOldCursor(selectionCursor);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
	}
	
	private void setupBuilderBox() {
		//BUILDER UI BOX
		uiBuilderCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Color.GRAY);
		
		
		//PORTRAIT
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, Assets.builder_down, new ClickListener() {
			@Override
			public void onClick(int button) {
				
				
				Unit u = handler.getEntityManager().getCommanded();
				
				if (!u.isControlled()) {
					handler.getEntityManager().setControlled(u);
					
					uiBuilderCommandBox.setOldCursor(combatCursor);
				} else {
					Unit nullUnit = null;
					handler.getEntityManager().setControlled(nullUnit);//TODO
					uiBuilderCommandBox.setOldCursor(selectionCursor);
				}
				
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		
		//TOWER
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32, 64, 64, Assets.tower_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				
				//Unit u = handler.getEntityManager().getCommanded();
				
				uiBuilderCommandBox.setOldCursor(commandCursor);
				commandCursor.setCommandNum(1);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("150");
				}
			}
		});
		
		//BARRACKS
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32, 64, 64, Assets.barracks_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				//barracks
				uiBuilderCommandBox.setOldCursor(commandCursor);
				commandCursor.setCommandNum(2);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("250");
				}
			}
		});
		
		
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 288, handler.getHeight() - 80 - 32, 64, 64, Assets.mageTower_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				//barracks
				uiBuilderCommandBox.setOldCursor(commandCursor);
				commandCursor.setCommandNum(3);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 288, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("300");
				}
			}
		});
		
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384, handler.getHeight() - 80 - 32, 64, 64, Assets.mageTower_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				//barracks
				uiBuilderCommandBox.setOldCursor(commandCursor);
				commandCursor.setCommandNum(4);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("200");
				}
			}
		});
		
		//SWITCH
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32, 16, 16, Assets.button_left, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBuilderCommandBox2);
				uiBuilderCommandBox2.setOldCursor(selectionCursor);
				
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32 + 64, 16, 16, Assets.button_right, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBuilderCommandBox2);
				uiBuilderCommandBox2.setOldCursor(selectionCursor);
				
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		//BUILDER UI BOX
		uiBuilderCommandBox2 = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Color.GRAY);
		
		
		//PORTRAIT
		uiBuilderCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, Assets.builder_down, new ClickListener() {
			@Override
			public void onClick(int button) {
				
				Unit u = handler.getEntityManager().getCommanded();
				
				if (!u.isControlled()) {
					handler.getEntityManager().setControlled(u);
					
					uiBuilderCommandBox2.setOldCursor(combatCursor);
				} else {
					Unit nullUnit = null;
					handler.getEntityManager().setControlled(nullUnit);//TODO
					uiBuilderCommandBox2.setOldCursor(selectionCursor);
				}
				
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		
		//
		uiBuilderCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32, 64, 64, Assets.tower_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				
				//Unit u = handler.getEntityManager().getCommanded();
				
				uiBuilderCommandBox2.setOldCursor(commandCursor);
				commandCursor.setCommandNum(1);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox2.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 96, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("150");
				}
			}
		});
		
		//
		uiBuilderCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32, 64, 64, Assets.barracks_idle, new ClickListener() {
			@Override
			public void onClick(int button) {
				//barracks
				uiBuilderCommandBox.setOldCursor(commandCursor);
				commandCursor.setCommandNum(2);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox2.addObject(new UITextRectangle(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 192, handler.getHeight() - 80 - 32 + 64, 64, 32, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("", this.getWidth() / 2, this.getHeight() / 2, Color.YELLOW, 22));
				}
				for (Text t : this.getTexts()) {
					t.setString("250");
				}
			}
		});
		
		//SWITCH
		uiBuilderCommandBox2.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32, 16, 16, Assets.button_left, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBuilderCommandBox);
				uiBuilderCommandBox.setOldCursor(selectionCursor);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
		
		uiBuilderCommandBox.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384 + 80, handler.getHeight() - 80 - 32 + 64, 16, 16, Assets.button_right, new ClickListener() {
			@Override
			public void onClick(int button) {
				hudUIManager.setUICommandBox(uiBuilderCommandBox);
				uiBuilderCommandBox2.setOldCursor(selectionCursor);
			}
			
			@Override
			public void pressed(int button) {
				
			}
		}));
	}
	
	private void setupOffensiveCreatureBox() {
		ArrayList<UIRectangleContainer> boxes = new ArrayList<UIRectangleContainer>();
		
		uiWizardCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Assets.wizard_down);
		uiZombieCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Assets.sicky_down);
		uiMudcrabCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Assets.mudcrab_down);
		
		boxes.add(uiWizardCommandBox);
		boxes.add(uiZombieCommandBox);
		boxes.add(uiMudcrabCommandBox);
		
		for (UIRectangleContainer box : boxes) {
			ClickListener clicker = new ClickListener() {
				@Override
				public void onClick(int button) {
					Unit u = handler.getEntityManager().getCommanded();
					if (!u.isControlled()) {
						handler.getEntityManager().setControlled(u);
						box.setOldCursor(combatCursor);
					} else {
						Unit nullUnit = null;
						handler.getEntityManager().setControlled(nullUnit);//TODO
						box.setOldCursor(selectionCursor);
					}
				}
				
				@Override
				public void pressed(int button) {
					
				}
			};
			
			box.setPortraitObj(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, null, clicker));
			box.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384, handler.getHeight() - 80 - 32, 64, 64, Assets.selection_cross_black, new ClickListener() {
				@Override
				public void onClick(int button) {
					Unit u = handler.getEntityManager().getCommanded();
					u.die();
				}
				
				@Override
				public void pressed(int button) {
					
				}
			}));
		}
	}
	
	private void setupOffensiveBuildingBox() {
		ArrayList<UIRectangleContainer> boxes = new ArrayList<UIRectangleContainer>();
		
		uiTowerCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Assets.tower_idle);
		uiMagetowerCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Assets.mageTower_idle);
		uiWallCommandBox = new UIRectangleContainer(handler, handler.getWidth() / 2 - 256 + 53, handler.getHeight() - 128, 512, 96, Color.GRAY, Assets.mageTower_idle);
		
		boxes.add(uiTowerCommandBox);
		boxes.add(uiMagetowerCommandBox);
		boxes.add(uiWallCommandBox);
		
		for (UIRectangleContainer box : boxes) {
			ClickListener clicker = new ClickListener() {
				@Override
				public void onClick(int button) {
					Unit u = handler.getEntityManager().getCommanded();
					if (!u.isControlled()) {
						handler.getEntityManager().setControlled(u);
						box.setOldCursor(combatCursor);
					} else {
						Unit nullUnit = null;
						handler.getEntityManager().setControlled(nullUnit);//TODO
						box.setOldCursor(selectionCursor);
					}
				}
				
				@Override
				public void pressed(int button) {
					
				}
			};
			
			box.setPortraitObj(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16, handler.getHeight() - 80 - 32, 64, 64, null, clicker));
			box.addObject(new UIImageButton(handler, handler.getWidth() / 2 - 256 + 53 + 16 + 384, handler.getHeight() - 80 - 32, 64, 64, Assets.selection_cross_black, new ClickListener() {
				@Override
				public void onClick(int button) {
					Unit u = handler.getEntityManager().getCommanded();
					u.die();
				}
				
				@Override
				public void pressed(int button) {
					
				}
			}));
		}
	}
	
	
	private void addFPSObject() {
		hudUIManager.addObject(new UITextRectangle(handler, handler.getWidth() - 50, 16, 42, 24, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text(Integer.toString(handler.getFPS()), this.getWidth() / 2, this.getHeight() / 2, Color.BLACK, 16));
				}
				for (Text t : this.getTexts()) {
					t.setString(Integer.toString(handler.getFPS()));
				}
			}
		});
	}
	
	private void addGoldObject() {
		hudUIManager.addObject(new UITextRectangle(handler, handler.getWidth() - 50, 42, 42, 24, Color.YELLOW, Color.YELLOW) {
			@Override
			public void doTick() {
				if (this.getTexts().isEmpty()) {
					this.addText(new Text(Integer.toString(handler.getWorld().getGold()), this.getWidth() / 2, this.getHeight() / 2, Color.BLACK, 16));
				}
				for (Text t : this.getTexts()) {
					t.setString(Integer.toString(handler.getWorld().getGold()));
				}
			}
		});
	}
	
	private void addHealthObject() {
		hudUIManager.addObject(new UITextRectangle(handler, handler.getWidth() - 166, handler.getHeight() - 166, 150, 150, Color.RED, Color.RED) {
			@Override
			public void doTick() {
				Unit c = handler.getEntityManager().getCommanded();
				if (c == null) {
					return;
				}
				if (this.getTexts().isEmpty()) {
					this.addText(new Text(Integer.toString((int) c.getHealth()), this.getWidth() / 2, this.getHeight() / 2, Color.BLACK, 42));
				}
				for (Text t : this.getTexts()) {
					t.setString(Integer.toString((int) c.getHealth()));
				}
				this.setHeight((int) ((c.getHealth() * 150) / c.getMaxHealth()));
				this.setY(handler.getHeight() - 16 - (c.getHealth() * 150) / c.getMaxHealth());
			}
		});
	}
	
	private void addExpObject() {
		hudUIManager.addObject(new UITextRectangle(handler, handler.getWidth() - 166, handler.getHeight() - 166 - 16, 150, 12, Color.GRAY, Color.GRAY) {
			@Override
			public void doTick() {
				Unit u = handler.getEntityManager().getCommanded();
				if (u == null) {
					return;
				}
				if (this.getTexts().isEmpty()) {
					this.addText(new Text("EXP", this.getWidth() / 2, this.getHeight() / 2 + 2, Color.BLACK, 10));
				}
				if (u.hasExp()) {
					for (Text t : this.getTexts()) {
						t.setString(Integer.toString(u.getExp()));
					}
					this.setWidth((u.getExp() * 150) / u.getExpToLvl());
				}
			}
		});
	}
	
	public UIManager getPauseUIManager() {
		return pauseUIManager;
	}
	
	public void setPauseUIManager(UIManager pauseUIManager) {
		this.pauseUIManager = pauseUIManager;
	}
	
	public UIManager getUIManager() {
		return uiManager;
	}
	
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
		handler.getMouseManager().setUIManager(uiManager);
	}
	
	public UIManager getHudUIManager() {
		return hudUIManager;
	}
	
	public void setHudUIManager(UIManager hudUIManager) {
		this.hudUIManager = hudUIManager;
	}
	
	public UIRectangleContainer getUiDefaultCommandBox() {
		return uiDefaultCommandBox;
	}
	
	public void setUiDefaultCommandBox(UIRectangleContainer uiDefaultCommandBox) {
		this.uiDefaultCommandBox = uiDefaultCommandBox;
	}
	
	public UIRectangleContainer getUiDevCommandBox() {
		return uiDevCommandBox;
	}
	
	public void setUiDevCommandBox(UIRectangleContainer uiDevCommandBox) {
		this.uiDevCommandBox = uiDevCommandBox;
	}
	
	public UIRectangleContainer getUiBarracksCommandBox() {
		return uiBarracksCommandBox;
	}
	
	public void setUiBarracksCommandBox(UIRectangleContainer uiBarracksCommandBox) {
		this.uiBarracksCommandBox = uiBarracksCommandBox;
	}
	
	public UIRectangleContainer getUiBuilderCommandBox() {
		return uiBuilderCommandBox;
	}
	
	public void setUiBuilderCommandBox(UIRectangleContainer uiBuilderCommandBox) {
		this.uiBuilderCommandBox = uiBuilderCommandBox;
	}
	
	public UIRectangleContainer getUiOffUnitCommandBox() {
		return uiOffUnitCommandBox;
	}
	
	public void setUiOffUnitCommandBox(UIRectangleContainer uiOffUnitCommandBox) {
		this.uiOffUnitCommandBox = uiOffUnitCommandBox;
	}
	
	public UIRectangleContainer getUiWizardCommandBox() {
		return uiWizardCommandBox;
	}
	
	public void setUiWizardCommandBox(UIRectangleContainer uiWizardCommandBox) {
		this.uiWizardCommandBox = uiWizardCommandBox;
	}
	
	public UIRectangleContainer getUiZombieCommandBox() {
		return uiZombieCommandBox;
	}
	
	public void setUiZombieCommandBox(UIRectangleContainer uiZombieCommandBox) {
		this.uiZombieCommandBox = uiZombieCommandBox;
	}
	
	public UIRectangleContainer getUiMudcrabCommandBox() {
		return uiMudcrabCommandBox;
	}
	
	public void setUiMudcrabCommandBox(UIRectangleContainer uiMudcrabCommandBox) {
		this.uiMudcrabCommandBox = uiMudcrabCommandBox;
	}
	
	public UIRectangleContainer getUiTowerCommandBox() {
		return uiTowerCommandBox;
	}
	
	public void setUiTowerCommandBox(UIRectangleContainer uiTowerCommandBox) {
		this.uiTowerCommandBox = uiTowerCommandBox;
	}
	
	public UIRectangleContainer getUiMagetowerCommandBox() {
		return uiMagetowerCommandBox;
	}
	
	public void setUiMagetowerCommandBox(UIRectangleContainer uiMagetowerCommandBox) {
		this.uiMagetowerCommandBox = uiMagetowerCommandBox;
	}
	
	public UIRectangleContainer getUiWallCommandBox() {
		return uiWallCommandBox;
	}
	
	public void setUiWallCommandBox(UIRectangleContainer uiWallCommandBox) {
		this.uiWallCommandBox = uiWallCommandBox;
	}
}
