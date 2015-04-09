package com.jameslennon.spacejump.grid;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jameslennon.spacejump.util.Globals;

public abstract class GridItem {
    protected Body body;
    protected Image img;
    private int health = 1;
    private boolean removed, visible;
    private Stage stage;

    public Body getBody() {
        return body;
    }

    public void show(Group g, Stage s) {
        stage = s;
//
		if (img != null)
			g.addActor(img);
        // img.setZIndex(105);
    }

    public void update() {
        if (body==null)return;
        if (health <= 0) {
            die();
        }
    }

    public void setHealth(int amt) {
        health = amt;
    }

    public void changeHealth(int amt, byte id) {
        health += amt;
    }

    public int getHealth() {
        return health;
    }

    public void die() {
        if (!removed) {
            img.setVisible(false);
            img.remove();
            Globals.gridMap.removeItem(this);
            removed = true;
        }
    }

    public void collide(GridItem other) {
    }

    public void endCollide(GridItem other){
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean rem){
        removed = rem;
    }

    public Image getImage() {
        return img;
    }

    public void setVisible(boolean b){
        if(b!=visible){
            visible = b;
            if(b){
                img.setVisible(true);
//                if (img != null)
//                    stage.addActor(img);
            }else{
                if(img!=null){
                    img.setVisible(false);
//                    img.remove();
                }
            }
        }
    }
}
