package com.hackplan.androidarcmenu;

import android.graphics.PointF;

class PointUtils {
    /**
     * Polar coordinates to Rectangular coordinates
     *
     * @param origin   of the coordinate system
     * @param distance this really means "radius"
     * @param angle    from the x-axis in radians; positive increases in the counterclockwise direction
     */
    static PointF getPoint(PointF origin, float distance, double angle) {
        PointF newPoint = new PointF();
        newPoint.x = origin.x + (float) (distance * Math.cos(angle));
        newPoint.y = origin.y + (float) (distance * Math.sin(angle));
        return newPoint;
    }
}
