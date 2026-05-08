package com.example.demo.model;

public enum Service {
    CORTE("Corte", 35.00),
    SOBRANCELHA("Sobrancelha", 15.00),
    BARBA("Barba", 25.00),
    CORTE_INFANTIL("Corte Infantil", 30.00),
    CORTE_INFANTIL_SOBRANCELHA("Corte Infantil + Sobrancelha", 40.00),
    CORTE_BARBA("Corte + Barba", 55.00),
    CORTE_SOBRANCELHA("Corte + Sobrancelha", 45.00);

    private final String displayName;
    private final Double price;

    Service(String displayName, Double price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Double getPrice() {
        return price;
    }
}