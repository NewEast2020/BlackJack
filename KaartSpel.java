import java.util.ArrayList;
import java.util.Random;

class KaartSpel {
    static final String TOTAALWAARDE = "==== totaalwaarde";
    static final String STREEPJE = " ====";
    private static int maxWaardeSpeler = 0;
    private static int maxWaardeBank = 0;
    private KaartSpel(){
    }
    public static int getMaxWaardeSpeler() {
        return maxWaardeSpeler;
    }

    public static int getMaxWaardeBank() {
        return maxWaardeBank;
    }

    public static void setMaxWaardeSpeler(int maxWaardeSpeler) {
    }
    public static void setMaxWaardeBank(int maxWaardeBank) {
    }

    public static int vraagEenKaart(ArrayList<Kaart> kaarten1, ArrayList<Kaart> spelerOfBank, String hlp, String spelerBank) {
        ArrayList<Integer> waarden = new ArrayList<>();
        spelerOfBank.add(kaarten1.get(kaarten1.size() - 1));
        kaarten1.remove(kaarten1.size() - 1);
        if (hlp.equals("noPrint")) return -1;
        else {
            int aantalAzen = KaartSpel.bepaalAantalAzen(spelerOfBank);
            KaartSpel.getWaarde(spelerOfBank, waarden, aantalAzen);
            return printKaarten(spelerOfBank, spelerBank, aantalAzen, waarden);
        }
    }

    public static ArrayList<Kaart> schudKaarten(ArrayList<Kaart> kaarten1) {
        ArrayList<Kaart> kaarten2 = new ArrayList<>();
        int shuffle;
        Random rand = new Random();
        for (int k = kaarten1.size() - 1; k >= 0; k--) {
            shuffle = rand.nextInt(k + 1);
            kaarten2.add(kaarten1.get(shuffle));
            kaarten1.remove(shuffle);
        }
        return kaarten2;
    }

    public static int bepaalAantalAzen(ArrayList<Kaart> spelerOfBank) {
        int aantalAzen = 0;
        for (int k = 0; k < spelerOfBank.size(); k++) {
            if (spelerOfBank.get(k).getIsAas()) aantalAzen++;
        }
        return aantalAzen;
    }

    public static void setMaxWaardeSpeler(ArrayList<Kaart> listSpeler, int aantalAzen, ArrayList waardenSpeler) {
        int waarde = 0;
        for (int k = 0; k < listSpeler.size(); k++) {
            waarde += listSpeler.get(k).getWaarde();
        }
        if (aantalAzen > 0) {
            KaartSpel.maxWaardeSpeler = (int) waardenSpeler.get(waardenSpeler.size() - 1);
        } else {
            KaartSpel.maxWaardeSpeler = waarde;
        }
    }

    public static void setMaxWaardeBank(ArrayList<Kaart> listBank, int aantalAzen, ArrayList waardenBank) {
        int waarde = 0;
        for (int k = 0, teller = 1; k < listBank.size(); k++, teller++) {
            waarde += listBank.get(k).getWaarde();
        }
        if (aantalAzen > 0) {
            KaartSpel.maxWaardeBank = (int) waardenBank.get(waardenBank.size() - 1);
        } else {
            KaartSpel.maxWaardeBank = waarde;
        }
    }

    public static int printKaarten(ArrayList<Kaart> spelerOfBank, String spelerBank, int aantalAzen, ArrayList waarden) {
        int waarde = 0;
        for (int k = 0, teller = 1; k < spelerOfBank.size(); k++, teller++) {
            System.out.print(teller + " ");
            spelerOfBank.get(k).printKaart();
            waarde += spelerOfBank.get(k).getWaarde();
        }
        if (aantalAzen > 0) {
            aantalAzenGroterDanNul(spelerBank, waarden);
        } else {
            aantalAzenKleinerGelijkNul(spelerBank, waarde);
        }
        return waarde;
    }

    private static void aantalAzenKleinerGelijkNul(String spelerBank, int waarde) {
        System.out.println(TOTAALWAARDE + spelerBank + ": " + waarde + STREEPJE);
        if (spelerBank.equals("Bank")) {
            KaartSpel.maxWaardeBank = waarde;
        } else {
            KaartSpel.maxWaardeSpeler = waarde;
        }
    }

    private static void aantalAzenGroterDanNul(String spelerBank, ArrayList waarden) {
        System.out.print(TOTAALWAARDE + spelerBank + ": ");
        for (int k = 0; k < waarden.size(); k++) {
            if (k == waarden.size() - 1) {
                if (spelerBank.equals("Bank")) {
                    KaartSpel.maxWaardeBank = (int) waarden.get(k);
                } else {
                    KaartSpel.maxWaardeSpeler = (int) waarden.get(k);
                }
                System.out.print(waarden.get(k));
            } else {
                System.out.print(waarden.get(k) + ", ");
            }
        }
        System.out.println(STREEPJE);
    }

    public static int printKaarten(ArrayList<Kaart> spelerOfBank, String spelerBank) {
        int waarde = 0;
        for (int k = 0, teller = 1; k < spelerOfBank.size(); k++, teller++) {
            System.out.print(teller + " ");
            spelerOfBank.get(k).printKaart();
            waarde += spelerOfBank.get(k).getWaarde();
        }
        System.out.println(TOTAALWAARDE + spelerBank + ": " + waarde + STREEPJE);
        return waarde;
    }

    public static ArrayList<Integer> getWaarde(ArrayList<Kaart> spelerOfBank, ArrayList<Integer> waardenSpelerBank, int aantalAzen) {
        int waarde = 0;
        waardenSpelerBank.clear();
        for (int k = 0; k < spelerOfBank.size(); k++) {
            waarde += spelerOfBank.get(k).getWaarde();
        }
        waardenSpelerBank.add(Integer.valueOf(waarde));
        for (int k = 1; k <= aantalAzen; k++) {
            waarde += 10;
            if (waarde <= 21) {
                waardenSpelerBank.add(waarde);
            }
        }
        return waardenSpelerBank;
    }

    public static int getWaarde(ArrayList<Kaart> spelerOfBank) {
        int waarde = 0;
        for (int k = 0; k < spelerOfBank.size(); k++) {
            waarde += spelerOfBank.get(k).getWaarde();
        }
        return waarde;
    }

    public static void printMenu() {
        System.out.println("                                           Menu:  k = kaart vragen,  p = passen,  q = spel stoppen");
    }

    public static ArrayList<Kaart> maakKaarten(ArrayList<Kaart> kaarten1, String soort, String nummerFig, String kleur, int waarde, boolean isAas) {
        kaarten1.add(new Kaart(soort, nummerFig, kleur, waarde, isAas));
        return kaarten1;
    }

    public static ArrayList<Kaart> maakKaarten() {
        ArrayList<Kaart> kaarten1 = new ArrayList<>();
        String soort = "";
        String kleur = "";
        String nummerFig ;
        int waarde = 0;
        boolean isAas = false;

        for (int j = 0; j < 4; j++) {
            switch (j) {
                case 0:
                    soort = "Schoppen";
                    kleur = "Zwart";
                    break;
                case 1:
                    soort = "Ruiten";
                    kleur = "Rood";
                    break;
                case 2:
                    soort = "Harten";
                    kleur = "Rood";
                    break;
                default:
                    soort = "Klaveren";
                    kleur = "Zwart";
                    break;
            }

            for (int i = 1; i <= 13; i++) {
                switch (i) {
                    case 1:
                        nummerFig = "Aas";
                        waarde = 1; // 11
                        isAas = true;
                        break;
                    case 11:
                        nummerFig = "Boer";
                        waarde = 10;
                        isAas = false;
                        break;
                    case 12:
                        nummerFig = "Vrouw";
                        waarde = 10;
                        isAas = false;
                        break;
                    case 13:
                        nummerFig = "Heer";
                        waarde = 10;
                        isAas = false;
                        break;
                    default:
                        nummerFig = Integer.toString(i);
                        waarde = i;
                        isAas = false;
                }
                kaarten1.add(new Kaart(soort, nummerFig, kleur, waarde, isAas));
            }
        }
        return kaarten1;
    }
}