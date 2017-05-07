package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.Handler;

public class UIManager {
	
	private Handler handler;
	private ArrayList<UIObject> objects;
	
	public UIManager(Handler handler) {
	    this.handler = handler;
	    objects = new ArrayList<UIObject>();
	}
	
	public void update(){
		for(UIObject obj : objects)
			obj.update();
		
	}
	
	public void render(Graphics g){
		for(UIObject obj : objects)
			obj.render(g);;
		
	}
	
	public void onMouseRelease(MouseEvent e){
		for(UIObject obj : objects)
			obj.onMouseRelease(e);
		
	}
	
	public void onMouseMove(MouseEvent e){
		for(UIObject obj : objects)
			obj.onMouseMove(e);
		
	}
	
	public void addObject(UIObject e){
		objects.add(e);
	}
	
    public void removeObject(UIObject obj){
		objects.remove(obj);
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

}
