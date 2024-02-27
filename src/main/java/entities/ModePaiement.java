package entities;

public enum ModePaiement {
    ESPECES("Espèces"),
    CHEQUE("Chèque"),
    CARTE_BANCAIRE("Carte Bancaire"),
    VIREMENT("Virement");

    private final String mode;

    ModePaiement(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return mode;
    }
}
