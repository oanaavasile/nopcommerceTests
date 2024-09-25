package model;

import java.util.Objects;

public class ApparelProduct extends Product{
    private String size;
    private String color;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o) || getClass() != o.getClass()) return false;
        ApparelProduct that = (ApparelProduct) o;
        return Objects.equals(size, that.size) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size, color);
    }

    @Override
    public String toString() {
        return "ApparelProduct{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", totalPrice=" + getTotalPrice() +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

}
