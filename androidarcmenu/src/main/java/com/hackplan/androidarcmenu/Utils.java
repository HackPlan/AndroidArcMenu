
/**
 * Copyright (C) 2015 ogaclejapan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackplan.androidarcmenu;

/**
 * Modified by Dacer.
 * from https://github.com/ogaclejapan/ArcLayout
 */
public class Utils {


    public static int x(int radius, float degrees) {
        return Math.round(Utils.computeCircleX(radius, degrees));
    }

    public static int y(int radius, float degrees) {
        return Math.round(computeCircleY(radius, degrees));
    }

    private static float computeCircleX(float r, float degrees) {
        return (float) (r * Math.cos(Math.toRadians(degrees)));
    }

    private static float computeCircleY(float r, float degrees) {
        return (float) (r * Math.sin(Math.toRadians(degrees)));
    }

}
