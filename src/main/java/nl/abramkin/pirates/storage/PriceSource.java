package nl.abramkin.pirates.storage;

import java.util.Objects;

/**
 * Created by aabramkin on 05/03/16.
 */
public class PriceSource {
    private final String name;
    private final int size;
    private final double avgPrice;
    private final int minSize;
    private final int stepSize;

    private PriceSource(PriceSourceBuilder builder) {
        this.name = builder.name;
        this.size = builder.size;
        this.avgPrice = builder.avgPrice;
        this.minSize = builder.minSize;
        this.stepSize = builder.stepSize;
    }

    public int getMaxQuantityForAsk(int askQuantity) {
        if (minSize > askQuantity || stepSize > askQuantity)
            return 0;
        if (size < askQuantity) {
            return size;
        }
        int askIncrement = (int) (Math.floor((double) (askQuantity - minSize) / stepSize) * stepSize);
        return minSize + askIncrement;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getStepSize() {
        return stepSize;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceSource that = (PriceSource) o;
        return Objects.equals(size, that.size) &&
                Objects.equals(avgPrice, that.avgPrice) &&
                Objects.equals(minSize, that.minSize) &&
                Objects.equals(stepSize, that.stepSize) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, avgPrice, minSize, stepSize);
    }

    @Override
    public String toString() {
        return "PriceSource{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", avgPrice=" + avgPrice +
                ", minSize=" + minSize +
                ", stepSize=" + stepSize +
                '}';
    }

    public static class PriceSourceBuilder {
        private String name;
        private int size;
        private double avgPrice;
        private int minSize;
        private int stepSize;

        public PriceSourceBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PriceSourceBuilder setSize(int size) {
            this.size = size;
            return this;
        }

        public PriceSourceBuilder setAvgPrice(double avgPrice) {
            this.avgPrice = avgPrice;
            return this;
        }

        public PriceSourceBuilder setMinSize(int minSize) {
            this.minSize = minSize;
            return this;
        }

        public PriceSourceBuilder setStepSize(int stepSize) {
            this.stepSize = stepSize;
            return this;
        }

        public PriceSource build() {
            return new PriceSource(this);
        }
    }
}
