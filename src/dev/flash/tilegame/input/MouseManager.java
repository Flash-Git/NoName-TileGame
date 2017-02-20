package dev.flash.tilegame.input;

import dev.flash.tilegame.ui.objects.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	
	private UIManager uiManager;
	
	public MouseManager() {
	}
	
	// Implemented methods
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseMoved(e);
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
			
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = true;
			
		}
		if (uiManager != null && !uiManager.getObjects().isEmpty()) {
			uiManager.onMousePressed(e, e.getButton());
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseMoved(e);
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = false;
		}
		if (uiManager != null && !uiManager.getObjects().isEmpty()) {
			uiManager.onMouseRelease(e, e.getButton());
		}
		mouseMoved(e);//refresh mouse location on uiManager switch
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (uiManager != null && !uiManager.getObjects().isEmpty()) {
			uiManager.onMouseMove(e);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	// Getters
	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	
	public UIManager getUIManager() {
		return uiManager;
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
}
