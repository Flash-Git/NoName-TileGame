package dev.flash.tilegame.utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
	public static String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static boolean rectCollides(Rectangle rect1, Rectangle rect2) {//Not trusting rect.intersects(Rect rect);
		int r1x = rect1.x;
		int r1y = rect1.y;
		int r2x = rect1.x + rect1.width;
		int r2y = rect1.y + rect1.height;
		int r3x = rect2.x;
		int r3y = rect2.y;
		int r4x = rect2.x + rect2.width;
		int r4y = rect2.y + rect2.height;
		
		return !(r2y < r3y || r1y > r4y || r2x < r3x || r1x > r4x);
	}
}
