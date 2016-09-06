package dev.flash.tilegame.ui.objects;

public interface ClickListener {

	public void onClick(int button);

	public void pressed(int button);
	//May be better to have a PressedListener Interface that is only imported by the mouse objects
}
