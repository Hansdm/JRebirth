/**
 * Copyright JRebirth.org © 2011-2012 
 * Contact : sebastien.bordes@jrebirth.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jrebirth.core.resource.color;

/**
 * The interface <strong>HSBColor</strong>.
 * 
 * @author Sébastien Bordes
 * 
 */
public class HSBColor extends AbstractBaseColor {

    /** The saturation value 0-255. */
    private final double hue;

    /** The saturation value 0-255. */
    private final double saturation;

    /** The brightness value 0-255. */
    private final double brightness;

    /**
     * Default Constructor.
     * 
     * @param hue
     * @param saturation
     * @param brightness
     */
    public HSBColor(final double hue, final double saturation, final double brightness) {
        super();
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    /**
     * Default Constructor.
     * 
     * @param hue
     * @param saturation
     * @param brightness
     * @param opacity
     */
    public HSBColor(final double hue, final double saturation, final double brightness, final double opacity) {
        super(opacity);
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    /**
     * Return the hue value 0.0-1.0.
     * 
     * @return Returns the hue.
     */
    public double hue() {
        return this.hue;
    }

    /**
     * Return the saturation value 0.0-1.0.
     * 
     * @return Returns the saturation.
     */
    public double saturation() {
        return this.saturation;
    }

    /**
     * Return the brightness value 0.0-1.0.
     * 
     * @return Returns the brightness.
     */
    public double brightness() {
        return this.brightness;
    }

}
