/**
 * Copyright 2014 Stephen Gibson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jameslennon.spacejump.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * An extension of the {@link OrthographicCamera}
 * <p/>
 * With several helper methods implemented which allows you to have fixed/moving
 * cameras, as well as following a given position.<br>
 * A current core feature is the ability to rumble the camera
 *
 * @author Stephen Gibson
 */
public class ActionCam extends OrthographicCamera {

    /**
     * If camera shake is enabled, has no effect if the camera is set to follow
     */
    public boolean shakeEnabled;

    /****************************************************/
    /***************** Camera shake *********************/
    /****************************************************/
    /**
     * If the camera is set to a fixed position, this will prevent any camera
     * movements. Useful for when a camera should snap to something and follow
     * or remain static in certain areas of a game world
     */
    public boolean isFixed;
    /**
     * Time since last frame
     */
    protected float delta;
    /**
     * If the camera is shaking
     */
    protected boolean shaking;
    /**
     * The total time the camera will shake for
     */
    protected float shakeTime;
    /**
     * Current amount of time the shake has been active
     */
    protected float currentShakeTime;
    /**
     * The power of the camera shake
     */
    protected float shakePower;
    /**
     * The current power of the camera shake
     */
    protected float currentShakePower;
    /**
     * How much the camera can move on the X axis
     */
    protected float shakeX;
    /**
     * How much the camera will move on the Y axis
     */
    protected float shakeY;
    /**
     * Camera position prior to shake
     */
    protected float x1, y1, z1;

    /****************************************************/
    /*********** Camera position and settings ***********/
    /****************************************************/
    /**
     * Camera position after shake
     */
    protected float x2, y2, z2;
    /**
     * A temporary vector used to hold the x and y positions of a
     * {@link Vector2} object for interpolation with a {@link Vector3} object
     */
    protected Vector3 lerp = new Vector3();
    /**
     * The smoothing of the interpolation between the camera vector and a given
     * vector
     */
    protected float smoothing;

    /**
     * Construct a new ExtendedOrthographicCamera with default values, ensure
     * you set if the camera is fixed ({@link #isFixed}), the width/height and
     * position before calling any updates or methods or you will throw errors
     */
    public ActionCam() {
        super();
        isFixed = false;

    }

    /**
     * Construct a new ExtendedOrthographic Camera with the given viewport width
     * and height, this constructor assumes you are setting the position of the
     * camera after creation
     *
     * @param viewportWidth  the width of the viewport to set
     * @param viewportHeight the height of the viewport to set
     */
    public ActionCam(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
        isFixed = false;
    }

    /**
     * Construct a new ExtendedOrthographic Camera except with the given width,
     * height and position to ensure the camera is placed correctly within the
     * first frame. If you do not set the postion, ensure you set the camera
     * position before any updates or you will get null pointers
     * <p/>
     * <b> WARNING </b> If isFixed is set to true, this constructor forces a
     * fixed camera
     *
     * @param viewportWidth  the width to set
     * @param viewportHeight the height to set
     * @param position       the position of the camera on creation, if not set camera will
     *                       be created at 0, 0
     * @param isFixed        wither or not the camera will be fixed. If you require a
     *                       camera that moves set to true
     */
    public ActionCam(float viewportWidth, float viewportHeight,
                     Vector2 position, boolean isFixed) {
        super(viewportWidth, viewportHeight);
        super.position.set(position, 0);
        this.isFixed = isFixed ? true : false;

    }

    public void follow(Body b) {
        Vector3 pos = new Vector3(b.getPosition(), 0);
        Vector2 vel = b.getLinearVelocity();
        Globals.stage.getCamera().unproject(pos);
        position.x = pos.x * Globals.PIXELS_PER_METER - vel.x;// *50;
        position.y = pos.y * Globals.PIXELS_PER_METER - vel.y;
        if (shaking)
            processShake(delta);
        // lerp.x = position.x + offsetX;
        // lerp.y = position.y + offsetY;
        //
        // if (isFixed)
        // throw new IllegalStateException("A fixed camera can not move");
        //
        // if (shakeEnabled) {
        // if (shaking) {
        // processShake(delta);
        // } else {
        // super.position.lerp(lerp, smoothing);
        //
        // }
        // }

    }

    public void center(Vector2 position, float offsetX, float offsetY) {
        lerp.set(position, 0);

        if (!isFixed)
            throw new IllegalStateException("Camera must be fixed");

        if (shakeEnabled) {
            if (shaking) {
                processShake(delta);
            } else {
                super.position.lerp(lerp, smoothing);

            }
        } else {
            super.position.set(position.x, position.y, z1);
        }
    }

    public void center() {
        lerp.set(viewportWidth / 2, viewportHeight / 2, 0);

        if (!isFixed)
            throw new IllegalStateException("Camera must be fixed");

        if (shakeEnabled) {
            if (shaking) {
                processShake(delta);
            } else {
                super.position.lerp(lerp, smoothing);
            }

        } else {
            super.position.lerp(lerp, smoothing);
        }
    }

    /**
     * Update the camera, this will call on its super method in {@link Camera}
     */
    @Override
    public void update() {
        super.update();
        delta = Gdx.graphics.getDeltaTime();
    }

    private final void processShake(float delta) {
        if (currentShakeTime <= shakeTime) {
            currentShakePower = shakePower
                    * ((shakeTime - currentShakeTime / 1.15f) / shakeTime);

            shakeX = (MathUtils.random(-0.5f, 0.5f) * MathUtils.random(1.25f,
                    1.5f)) * currentShakePower;
            shakeY = (MathUtils.random(-0.5f, 0.5f) * MathUtils.random(1.25f,
                    1.5f)) * currentShakePower;

            translate(-shakeX, -shakeY);
            currentShakeTime += delta;
        } else {
            shaking = false;
        }

    }

    /**
     * Start a camera shake, if not using a coordinate system and using pixel
     * coordinate world, a high power will have to be used for an effective
     * shake. Can use used to cause rumbled effects from explosions in game or
     * something similar
     *
     * @param power     The power of the shake, this is multiplied by 0.5f
     * @param time      The amount of time in seconds the shake should last for
     * @param smoothing how smooth the camera will snap back into place after the
     *                  camera shake, clamped 0-1
     */
    public final void startShake(float power, float time, float smoothing) {
        if (!shakeEnabled)
            throw new IllegalStateException("Camera shake is not enabled");
        shakePower = power;
        shakeTime = time;
        this.smoothing = smoothing;
        currentShakeTime = 0;
        shaking = true;
    }

    public void stopShake() {
        shaking = false;
    }

}
