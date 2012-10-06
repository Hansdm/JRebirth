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
package org.jrebirth.core.wave;

/**
 * 
 * The class <strong>WaveData</strong>.
 * 
 * Used to contain a value and its unique name.
 * 
 * @author Sébastien Bordes
 * 
 */
public final class WaveData<T> implements Comparable<WaveData<?>> {

    /**
     * The key property, must be set with an enumeration that implements WaveItem.
     */
    private WaveItem waveItem;

    /** The class type of the contained object. */
    private Class<T> valueClass;

    /** The value data. */
    private T value;

    /** The field used for sorting. */
    private int order;

    /**
     * Default Constructor.
     * 
     * @param waveItem the enumeration used as key property
     * @param value the data
     */
    public WaveData(final WaveItem waveItem, final T value) {
        setKey(waveItem);
        setValue(value);
    }

    /**
     * @return Returns the waveItem.
     */
    public WaveItem getKey() {
        return this.waveItem;
    }

    /**
     * @param waveItem the waveItem to set
     */
    public void setKey(final WaveItem waveItem) {
        this.waveItem = waveItem;
    }

    /**
     * @return Returns the valueClass.
     */
    protected Class<T> getValueClass() {
        return this.valueClass;
    }

    /**
     * @param valueClass The valueClass to set.
     */
    protected void setValueClass(final Class<T> valueClass) {
        this.valueClass = valueClass;
    }

    /**
     * @return Returns the value.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final T value) {
        this.value = value;
    }

    /**
     * @return Returns the order.
     */
    public int getOrder() {
        return this.order;
    }

    /**
     * @param order The order to set.
     */
    public void setOrder(final int order) {
        this.order = order;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return getOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        // A wave data is unique into a wave, no equals needed
        return obj instanceof WaveData && getOrder() == ((WaveData<T>) obj).getOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final WaveData<?> waveData) {
        return getOrder() - waveData.getOrder();
    }
}
