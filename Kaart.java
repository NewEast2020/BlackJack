class Kaart {
    private String soort;
    private String nummerFig;
    private String kleur;
    private int waarde;
    private boolean isAas;

    public String getSoort() {
        return soort;
    }
    public String getNummerFig() {
        return nummerFig;
    }
    public String getkleur() {
        return kleur;
    }

    int getWaarde() {
        return waarde;
    }

    boolean getIsAas() {
        return isAas;
    }

    Kaart(String soort, String nummerFig, String kleur) {
        this.soort = soort;
        this.nummerFig = nummerFig;
        this.kleur = kleur;
    }

    Kaart(String soort, String nummerFig, String kleur, int waarde) {
        this(soort, nummerFig, kleur);
        this.waarde = waarde;
    }

    Kaart(String soort, String nummerFig, String kleur, int waarde, boolean isAas) {
        this(soort, nummerFig, kleur, waarde);
        this.isAas = isAas;
    }

    void printKaart() {
        if (isAas) System.out.println(soort + " " + nummerFig + " " + kleur + " 1 of 11 ");
        else System.out.println(soort + " " + nummerFig + " " + kleur + " " + waarde);
    }
}
