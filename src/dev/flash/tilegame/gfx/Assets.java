package dev.flash.tilegame.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {
	
	private static final int width = 16, height = 16;
	private static final int spriteWidth = 32, spriteHeight = 32;
	
	
	public static BufferedImage player_idle_up, player_idle_down, player_idle_right, player_idle_left;
	public static BufferedImage builder_idle_up, builder_idle_down, builder_idle_right, builder_idle_left;
	public static BufferedImage sicky_idle_up, sicky_idle_down, sicky_idle_right, sicky_idle_left;
	
	public static BufferedImage human_dead, human_splat1, human_splat2;
	public static BufferedImage heart;
	public static BufferedImage grass1, rock1, tree1, wall1, wall2, stone1;
	
	
	public static BufferedImage[] tower_dead, tower_idle, barracks_dead, barracks_idle, mageTower_dead, mageTower_idle;
	public static BufferedImage[] player_up, player_down, player_right, player_left;
	public static BufferedImage[] builder_up, builder_down, builder_right, builder_left;
	public static BufferedImage[] sicky_up, sicky_down, sicky_right, sicky_left;
	public static BufferedImage[] wizard_idle, wizard_down, wizard_left, wizard_right, wizard_up;
	public static BufferedImage[] mudcrab_idle, mudcrab_down, mudcrab_left, mudcrab_right, mudcrab_up;
	
	
	public static BufferedImage[] projectile_horizontal, projectile_vertical;
	public static BufferedImage[] projectile_fireball_up, projectile_fireball_down, projectile_fireball_left, projectile_fireball_right;
	
	
	public static BufferedImage[] button_start, button_menu, button_options, button_spriteViewer;
	
	public static BufferedImage[] button_left, button_right, button_zoom, button_color;
	
	
	public static BufferedImage[] selection_cross_red, selection_cross_green, selection_cross_grey, selection_cross_black;
	
	private static ArrayList<Sprite> sprites;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/SpriteSheet.png"));
		sprites = new ArrayList<Sprite>();
		//CREATURES
		sprites.add(new Sprite(player_up = new BufferedImage[2], "player_up", spriteWidth, spriteHeight));
		sprites.add(new Sprite(player_down = new BufferedImage[2], "player_down", spriteWidth, spriteHeight));
		sprites.add(new Sprite(player_right = new BufferedImage[2], "player_right", spriteWidth, spriteHeight));
		sprites.add(new Sprite(player_left = new BufferedImage[2], "player_left", spriteWidth, spriteHeight));
		
		sprites.add(new Sprite(builder_up = new BufferedImage[2], "builder_up", spriteWidth, spriteHeight));
		sprites.add(new Sprite(builder_down = new BufferedImage[2], "builder_down", spriteWidth, spriteHeight));
		sprites.add(new Sprite(builder_right = new BufferedImage[2], "builder_right", spriteWidth, spriteHeight));
		sprites.add(new Sprite(builder_left = new BufferedImage[2], "builder_left", spriteWidth, spriteHeight));
		
		sprites.add(new Sprite(sicky_up = new BufferedImage[2], "sicky_up", spriteWidth, spriteHeight));
		sprites.add(new Sprite(sicky_down = new BufferedImage[2], "sicky_down", spriteWidth, spriteHeight));
		sprites.add(new Sprite(sicky_right = new BufferedImage[2], "sicky_right", spriteWidth, spriteHeight));
		sprites.add(new Sprite(sicky_left = new BufferedImage[2], "sicky_left", spriteWidth, spriteHeight));
		
		sprites.add(new Sprite(mudcrab_up = new BufferedImage[4], "mudcrab_up", spriteWidth, spriteHeight));
		sprites.add(new Sprite(mudcrab_down = new BufferedImage[4], "mudcrab_down", spriteWidth, spriteHeight));
		sprites.add(new Sprite(mudcrab_right = new BufferedImage[4], "mudcrab_right", spriteWidth, spriteHeight));
		sprites.add(new Sprite(mudcrab_left = new BufferedImage[4], "mudcrab_left", spriteWidth, spriteHeight));
		
		
		sprites.add(new Sprite(wizard_idle = new BufferedImage[4], "wizard_idle", spriteWidth, spriteHeight));
		sprites.add(new Sprite(wizard_up = new BufferedImage[4], "wizard_up", spriteWidth, spriteHeight));
		sprites.add(new Sprite(wizard_down = new BufferedImage[4], "wizard_down", spriteWidth, spriteHeight));
		sprites.add(new Sprite(wizard_right = new BufferedImage[2], "wizard_right", spriteWidth, spriteHeight));
		sprites.add(new Sprite(wizard_left = new BufferedImage[2], "wizard_left", spriteWidth, spriteHeight));
		
		
		//PROJECTILES
		sprites.add(new Sprite(projectile_vertical = new BufferedImage[2], "projectile_vertical", (int) (spriteWidth * 0.5), (int) (spriteHeight * 0.5)));
		sprites.add(new Sprite(projectile_horizontal = new BufferedImage[2], "projectile_horizontal", (int) (spriteWidth * 0.5), (int) (spriteHeight * 0.5)));
		
		sprites.add(new Sprite(projectile_fireball_up = new BufferedImage[2], "projectile_fireball_up", (int) (spriteWidth * 0.5), (int) (spriteHeight * 0.5)));
		sprites.add(new Sprite(projectile_fireball_down = new BufferedImage[2], "projectile_fireball_down", (int) (spriteWidth * 0.5), (int) (spriteHeight * 0.5)));
		sprites.add(new Sprite(projectile_fireball_right = new BufferedImage[2], "projectile_fireball_right", (int) (spriteWidth * 0.5), (int) (spriteHeight * 0.5)));
		sprites.add(new Sprite(projectile_fireball_left = new BufferedImage[2], "projectile_fireball_left", (int) (spriteWidth * 0.5), (int) (spriteHeight * 0.5)));
		
		
		//BUTTONS
		sprites.add(new Sprite(button_start = new BufferedImage[2], "button_start", 5 * spriteWidth, (int) (1.5 * spriteHeight)));
		sprites.add(new Sprite(button_menu = new BufferedImage[2], "button_menu", 5 * spriteWidth, (int) (1.5 * spriteHeight)));
		sprites.add(new Sprite(button_options = new BufferedImage[2], "button_options", 5 * spriteWidth, (int) (1.5 * spriteHeight)));
		sprites.add(new Sprite(button_spriteViewer = new BufferedImage[2], "button_spriteViewer", 5 * spriteWidth, (int) (1.5 * spriteHeight)));
		
		sprites.add(new Sprite(button_left = new BufferedImage[2], "button_left", 3 * spriteWidth, (int) (1.5 * spriteHeight)));
		sprites.add(new Sprite(button_right = new BufferedImage[2], "button_right", 3 * spriteWidth, (int) (1.5 * spriteHeight)));
		sprites.add(new Sprite(button_zoom = new BufferedImage[2], "button_zoom", 3 * spriteWidth, (int) (1.5 * spriteHeight)));
		sprites.add(new Sprite(button_color = new BufferedImage[2], "button_color", 3 * spriteWidth, (int) (1.5 * spriteHeight)));
		
		sprites.add(new Sprite(selection_cross_grey = new BufferedImage[2], "selection_cross_grey", spriteWidth, spriteHeight));
		sprites.add(new Sprite(selection_cross_red = new BufferedImage[2], "selection_cross_red", spriteWidth, spriteHeight));
		sprites.add(new Sprite(selection_cross_green = new BufferedImage[2], "selection_cross_green", spriteWidth, spriteHeight));
		sprites.add(new Sprite(selection_cross_black = new BufferedImage[2], "selection_cross_black", spriteWidth, spriteHeight));
		
		
		//BUILDINGS
		sprites.add(new Sprite(tower_idle = new BufferedImage[2], "tower_idle", spriteWidth, spriteHeight));
		sprites.add(new Sprite(tower_dead = new BufferedImage[4], "tower_dead", spriteWidth, spriteHeight));
		
		
		sprites.add(new Sprite(barracks_idle = new BufferedImage[2], "barracks_idle", 2 * width, (int) (1.5 * height)));
		sprites.add(new Sprite(barracks_dead = new BufferedImage[4], "barracks_dead", 2 * width, (int) (1.5 * height)));
		
		
		sprites.add(new Sprite(mageTower_idle = new BufferedImage[2], "mageTower_idle", 1 * width, 2 * height));
		sprites.add(new Sprite(mageTower_dead = new BufferedImage[2], "mageTower_dead", 1 * width, 2 * height));
		
		
		player_idle_up = sheet.crop(3 * width, 1 * height, width, height);
		player_idle_down = sheet.crop(4 * width, 1 * height, width, height);
		player_idle_right = sheet.crop(2 * width, 1 * height, width, height);
		player_idle_left = sheet.crop(1 * width, 1 * height, width, height);
		
		human_dead = sheet.crop(5 * width, 1 * height, width, height);
		human_splat1 = sheet.crop(6 * width, 1 * height, width, height);
		human_splat2 = sheet.crop(6 * width, 2 * height, width, height);
		
		player_up[0] = sheet.crop(3 * width, 2 * height, width, height);
		player_up[1] = sheet.crop(3 * width, 3 * height, width, height);
		player_down[0] = sheet.crop(4 * width, 2 * height, width, height);
		player_down[1] = sheet.crop(4 * width, 3 * height, width, height);
		player_right[0] = sheet.crop(2 * width, 2 * height, width, height);
		player_right[1] = sheet.crop(2 * width, 3 * height, width, height);
		player_left[0] = sheet.crop(1 * width, 2 * height, width, height);
		player_left[1] = sheet.crop(1 * width, 3 * height, width, height);
		
		builder_idle_up = sheet.crop(3 * width, 4 * height, width, height);
		builder_idle_down = sheet.crop(4 * width, 4 * height, width, height);
		builder_idle_right = sheet.crop(2 * width, 4 * height, width, height);
		builder_idle_left = sheet.crop(1 * width, 4 * height, width, height);
		
		builder_up[0] = sheet.crop(3 * width, 5 * height, width, height);
		builder_up[1] = sheet.crop(3 * width, 6 * height, width, height);
		builder_down[0] = sheet.crop(4 * width, 5 * height, width, height);
		builder_down[1] = sheet.crop(4 * width, 6 * height, width, height);
		builder_right[0] = sheet.crop(2 * width, 5 * height, width, height);
		builder_right[1] = sheet.crop(2 * width, 6 * height, width, height);
		builder_left[0] = sheet.crop(1 * width, 5 * height, width, height);
		builder_left[1] = sheet.crop(1 * width, 6 * height, width, height);
		
		sicky_idle_up = sheet.crop(3 * width, 7 * height, width, height);
		sicky_idle_down = sheet.crop(4 * width, 7 * height, width, height);
		sicky_idle_right = sheet.crop(2 * width, 7 * height, width, height);
		sicky_idle_left = sheet.crop(1 * width, 7 * height, width, height);
		
		sicky_up[0] = sheet.crop(3 * width, 8 * height, width, height);
		sicky_up[1] = sheet.crop(3 * width, 9 * height, width, height);
		sicky_down[0] = sheet.crop(4 * width, 8 * height, width, height);
		sicky_down[1] = sheet.crop(4 * width, 9 * height, width, height);
		sicky_right[0] = sheet.crop(2 * width, 8 * height, width, height);
		sicky_right[1] = sheet.crop(2 * width, 9 * height, width, height);
		sicky_left[0] = sheet.crop(1 * width, 8 * height, width, height);
		sicky_left[1] = sheet.crop(1 * width, 9 * height, width, height);
		
		
		wizard_idle[0] = sheet.crop(1 * width, 10 * height, 2 * width, 2 * height);
		wizard_idle[1] = sheet.crop(1 * width, 12 * height, 2 * width, 2 * height);
		wizard_idle[2] = sheet.crop(1 * width, 14 * height, 2 * width, 2 * height);
		wizard_idle[3] = sheet.crop(1 * width, 12 * height, 2 * width, 2 * height);
		
		wizard_down[0] = sheet.crop(3 * width, 10 * height, 2 * width, 2 * height);
		wizard_down[1] = sheet.crop(3 * width, 12 * height, 2 * width, 2 * height);
		wizard_down[2] = sheet.crop(3 * width, 10 * height, 2 * width, 2 * height);
		wizard_down[3] = sheet.crop(3 * width, 14 * height, 2 * width, 2 * height);
		
		wizard_left[0] = sheet.crop(5 * width, 10 * height, 2 * width, 2 * height);
		wizard_left[1] = sheet.crop(5 * width, 12 * height, 2 * width, 2 * height);
		
		wizard_right[0] = sheet.crop(7 * width, 10 * height, 2 * width, 2 * height);
		wizard_right[1] = sheet.crop(7 * width, 12 * height, 2 * width, 2 * height);
		
		wizard_up[0] = sheet.crop(9 * width, 10 * height, 2 * width, 2 * height);
		wizard_up[1] = sheet.crop(9 * width, 12 * height, 2 * width, 2 * height);
		wizard_up[2] = sheet.crop(9 * width, 10 * height, 2 * width, 2 * height);
		wizard_up[3] = sheet.crop(9 * width, 14 * height, 2 * width, 2 * height);
		
		mudcrab_down[0] = sheet.crop(1 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_down[1] = sheet.crop(1 * width, 18 * height, 2 * width, 2 * height);
		mudcrab_down[2] = sheet.crop(1 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_down[3] = sheet.crop(1 * width, 20 * height, 2 * width, 2 * height);
		
		mudcrab_left[0] = sheet.crop(5 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_left[1] = sheet.crop(5 * width, 18 * height, 2 * width, 2 * height);
		mudcrab_left[2] = sheet.crop(5 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_left[3] = sheet.crop(5 * width, 20 * height, 2 * width, 2 * height);
		
		mudcrab_right[0] = sheet.crop(7 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_right[1] = sheet.crop(7 * width, 18 * height, 2 * width, 2 * height);
		mudcrab_right[2] = sheet.crop(7 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_right[3] = sheet.crop(7 * width, 20 * height, 2 * width, 2 * height);
		
		mudcrab_up[0] = sheet.crop(9 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_up[1] = sheet.crop(9 * width, 18 * height, 2 * width, 2 * height);
		mudcrab_up[2] = sheet.crop(9 * width, 16 * height, 2 * width, 2 * height);
		mudcrab_up[3] = sheet.crop(9 * width, 20 * height, 2 * width, 2 * height);
		
		
		projectile_vertical[0] = sheet.crop(7 * width, 1 * height, width, height);
		projectile_vertical[1] = sheet.crop(7 * width, 2 * height, width, height);
		
		projectile_horizontal[0] = sheet.crop(7 * width, 3 * height, width, height);
		projectile_horizontal[1] = sheet.crop(7 * width, 4 * height, width, height);
		
		projectile_fireball_right[0] = sheet.crop(14 * width, 10 * height, 2 * width, 2 * height);
		projectile_fireball_right[1] = sheet.crop(16 * width, 10 * height, 2 * width, 2 * height);
		projectile_fireball_left[0] = sheet.crop(14 * width, 12 * height, 2 * width, 2 * height);
		projectile_fireball_left[1] = sheet.crop(16 * width, 12 * height, 2 * width, 2 * height);
		projectile_fireball_up[0] = sheet.crop(14 * width, 14 * height, 2 * width, 2 * height);
		projectile_fireball_up[1] = sheet.crop(16 * width, 14 * height, 2 * width, 2 * height);
		projectile_fireball_down[0] = sheet.crop(14 * width, 16 * height, 2 * width, 2 * height);
		projectile_fireball_down[1] = sheet.crop(16 * width, 16 * height, 2 * width, 2 * height);
		
		
		tower_idle[0] = sheet.crop(5 * width, 22 * height, width, height);
		tower_idle[1] = sheet.crop(6 * width, 22 * height, width, height);
		
		tower_dead[0] = sheet.crop(12 * width, 1 * height, width, height);
		tower_dead[1] = sheet.crop(11 * width, 1 * height, width, height);
		tower_dead[2] = sheet.crop(13 * width, 1 * height, width, height);
		tower_dead[3] = sheet.crop(11 * width, 1 * height, width, height);
		
		barracks_idle[0] = sheet.crop(1 * width, 22 * height, 2 * width, (int) (1.5 * height));
		barracks_idle[1] = sheet.crop(3 * width, 22 * height, 2 * width, (int) (1.5 * height));
		
		//barracks_dead[0]
		
		mageTower_idle[0] = sheet.crop(1 * width, 24 * height, 2 * width, 4 * height);
		mageTower_idle[1] = sheet.crop(3 * width, 24 * height, 2 * width, 4 * height);
		
		//mageTower_dead[0]
		
		
		wall1 = sheet.crop(16 * width, 1 * height, width, height);
		stone1 = sheet.crop(17 * width, 1 * height, width, height);
		grass1 = sheet.crop(18 * width, 1 * height, width, height);
		wall2 = sheet.crop(19 * width, 1 * height, width, height);
		
		tree1 = sheet.crop(14 * width, 1 * height, 2 * width, 4 * height);
		rock1 = sheet.crop(0 * width, 0 * height, width, height);
		
		
		heart = sheet.crop(25 * width, 2 * height, 1 * width, 1 * height);
		
		button_start[0] = sheet.crop(20 * width, 1 * height, 5 * width, 2 * height - 8);
		button_start[1] = sheet.crop(20 * width, 2 * height + 8, 5 * width, 2 * height - 8);
		
		button_menu[0] = sheet.crop(20 * width, 4 * height, 5 * width, 2 * height - 8);
		button_menu[1] = sheet.crop(20 * width, 5 * height + 8, 5 * width, 2 * height - 8);
		
		button_options[0] = sheet.crop(20 * width, 7 * height, 5 * width, 2 * height - 8);
		button_options[1] = sheet.crop(20 * width, 8 * height + 8, 5 * width, 2 * height - 8);
		
		button_spriteViewer[0] = sheet.crop(20 * width, 10 * height, 5 * width, 2 * height - 8);
		button_spriteViewer[1] = sheet.crop(20 * width, 11 * height + 8, 5 * width, 2 * height - 8);
		
		button_left[0] = button_start[0];//TODO
		button_left[1] = button_start[1];//TODO
		
		button_right[0] = button_start[0];//TODO
		button_right[1] = button_start[1];//TODO
		
		button_zoom[0] = button_start[0];//TODO
		button_zoom[1] = button_start[1];//TODO
		
		button_color[0] = button_start[0];//TODO
		button_color[1] = button_start[1];//TODO
		
		selection_cross_grey[0] = sheet.crop(26 * width, 1 * height, 1 * width, 1 * height);
		selection_cross_grey[1] = sheet.crop(25 * width, 1 * height, 1 * width, 1 * height);
		
		selection_cross_red[0] = sheet.crop(27 * width, 1 * height, 1 * width, 1 * height);
		selection_cross_red[1] = sheet.crop(25 * width, 1 * height, 1 * width, 1 * height);
		
		selection_cross_green[0] = sheet.crop(28 * width, 1 * height, 1 * width, 1 * height);
		selection_cross_green[1] = sheet.crop(25 * width, 1 * height, 1 * width, 1 * height);
		
		selection_cross_black[0] = sheet.crop(25 * width, 1 * height, 1 * width, 1 * height);
		selection_cross_black[1] = sheet.crop(25 * width, 1 * height, 1 * width, 1 * height);
	}
	
	public static ArrayList<Sprite> getSprites() {
		return sprites;
	}
}
