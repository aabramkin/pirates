package nl.abramkin.pirates;

public class Purchase {
    private String sourceName;
    private Integer numberOfGallons;
    private Double priceOfGallon;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getNumberOfGallons() {
        return numberOfGallons;
    }

    public void setNumberOfGallons(Integer numberOfGallons) {
        this.numberOfGallons = numberOfGallons;
    }

    public Double getPriceOfGallon() {
        return priceOfGallon;
    }

    public void setPriceOfGallon(Double priceOfGallon) {
        this.priceOfGallon = priceOfGallon;
    }

    public Double getTotalPrice() {
        return getPriceOfGallon() * getNumberOfGallons();
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "sourceName='" + sourceName + '\'' +
                ", numberOfGallons=" + numberOfGallons +
                ", priceOfGallon=" + priceOfGallon +
                '}';
    }
}
