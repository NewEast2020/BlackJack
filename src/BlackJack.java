import java.util.ArrayList;
import java.util.Scanner;
public class BlackJack {
    static final String ACCOUNT = "  Account: ";
    static final String NOPRINT = "noPrint";
    static final String SPELER = "Speler";
    static final String ONJUISTE_INVOER = "Onjuiste invoer - ";
    static final String BANK = "Bank";

    public static void main(String[] args) {
        System.out.println("NimWing Yuan rev 047! gelijk ");
        ArrayList<Kaart> kaarten2 = new ArrayList<>();
        ArrayList<Kaart> Speler = new ArrayList<>();
        ArrayList<Kaart> Bank = new ArrayList<>();
        ArrayList<Integer> waardenBank = new ArrayList<>(); // mogelijke waarden indien er een Aas is
        Scanner reader = new Scanner(System.in);

        boolean isSpelen = true;       // spel wordt gespeeld ?
        boolean isPassen = false;      // speler past ?
        boolean isNogEenKeer = true;   // nog een keer spelen
        boolean isVergelijken = false; // speler en bank niet over de 21, vergelijken waarden nodig
        boolean eersteBeurt = true;    // indien 1e beurt 2 kaarten uitdelen
        prt("Wilt u spelen ? [j/n]. Standaard inleg: " + SpelerA.getInlegA() + " Euro. Account: " + SpelerA.getAccountA() + " Euro");

        char input = 'x';
        boolean isInputOK = false;
        input = getInput(reader, input, isInputOK);
        isSpelen = (input == 'j');
        kaarten2 = isKaartenMakenSchudden(kaarten2, isSpelen);
        while (isNogEenKeer) {
            while (isSpelen) {
                eersteBeurt = isEersteBeurt(kaarten2, Speler, Bank, eersteBeurt);
                input = 'x';
                isInputOK = false;
                input = getInput2(reader, input, isInputOK);

                switch (input) {
                    case 'q': isSpelen = false; break; // quit
                    case 'k': isSpelen = isSpelen(kaarten2, Speler, isSpelen);  break;  // kaart vragen
                    case 'p': isPassen = isPassen(Bank, waardenBank);
                        if (isSpelerKleinerGelijkBank(KaartSpel.getMaxWaardeSpeler(), KaartSpel.getMaxWaardeBank())) {
                            isVergelijken = true; break;
                        }
                        while (isPassen) {
                            if (isBankGroterGelijkSpeler(Bank)) {
                                isVergelijken = true; break;
                            } else if (isBankOntploft(KaartSpel.getWaarde(Bank), 21)) { // check this out
                                isSpelen = isSpelen(); break;
                            } else {
                                VraagEenKaart(kaarten2, Bank, waardenBank);
                            }
                        }
                        break; // passen
                    default: break;
                }
                isSpelen = isSpelen(isSpelen, isVergelijken);
            } // isSpelen
            input = getInputNogEenKeer(reader);
            isNogEenKeer = (input == 'j');
            isSpelen = isNogEenKeer;
            if (isSpelen) {
                isVergelijken = false;
                kaarten2 = getKaarten(Speler, Bank);
                eersteBeurt = true;
            }
        }
        prt("Tot ziens");
        prt("BlackJacken");
    }

    private static boolean isSpelerKleinerGelijkBank(int maxWaardeSpeler, int maxWaardeBank) {
        return maxWaardeSpeler <= maxWaardeBank;
    }

    private static boolean isBankOntploft(int waarde, int i) {
        return waarde > i;
    }

    private static boolean isBankGroterGelijkSpeler(ArrayList<Kaart> bank) {
        return KaartSpel.getMaxWaardeBank() >= KaartSpel.getMaxWaardeSpeler() &&
                isSpelerKleinerGelijkBank(KaartSpel.getWaarde(bank), 21);
    }

    private static ArrayList<Kaart> isKaartenMakenSchudden(ArrayList<Kaart> kaarten2, boolean isSpelen) {
        ArrayList<Kaart> kaarten1;
        if (isSpelen) {
            kaarten1 = KaartSpel.maakKaarten();
            kaarten2 = KaartSpel.schudKaarten(kaarten1);
        }
        return kaarten2;
    }

    private static char getInputNogEenKeer(Scanner reader) {
        char input;
        boolean isInputOK;
        prt("Wilt U nog een keer spelen ? [j/n]");
        input = 'x';
        isInputOK = false;
        input = getInput3(reader, input, isInputOK, "  j(a) n(ee)");
        return input;
    }

    private static boolean isPassen(ArrayList<Kaart> bank, ArrayList<Integer> waardenBank) {
        boolean isPassen;
        isPassen = true;
        int aantalAzen = KaartSpel.bepaalAantalAzen(bank);
        KaartSpel.getWaarde(bank, waardenBank, aantalAzen);
        KaartSpel.printKaarten(bank, BANK, aantalAzen, waardenBank);
        return isPassen;
    }

    private static boolean isSpelen(boolean isSpelen, boolean isVergelijken) {
        if (isVergelijken) {
            isSpelen = isSpelen(isSpelen);
        } else {
            KaartSpel.printMenu();
        }
        return isSpelen;
    }

    private static char getInput(Scanner reader, char input, boolean isInputOK) {
        input = getInput3(reader, input, isInputOK, " j(a) / n(ee)");
        return input;
    }

    private static char getInput2(Scanner reader, char input, boolean isInputOK) {
        while (!isInputOK) {
            input = reader.nextLine().charAt(0);
            if (input == 'k' || input == 'p' || input == 'q') isInputOK = true;
            if (!isInputOK) {
                prt( ONJUISTE_INVOER + input + "  k(aart)  p(as)  q(uit) )");
            }
        }
        return input;
    }

    private static char getInput3(Scanner reader, char input, boolean isInputOK, String s) {
        while (!isInputOK) {
            input = reader.nextLine().charAt(0);
            if (input == 'j' || input == 'n') isInputOK = true;
            if (!isInputOK) {
                prt(ONJUISTE_INVOER + input + s);
            }
        }
        return input;
    }

    private static boolean isSpelen() {
        boolean isSpelen;
        isSpelen = false;
        prt( BANK + " verliest !!");
        prtWinst();
        return isSpelen;
    }

    private static void VraagEenKaart(ArrayList<Kaart> kaarten2, ArrayList<Kaart> bank, ArrayList<Integer> waardenBank) {
        int aantalAzen;
        KaartSpel.vraagEenKaart(kaarten2, bank, NOPRINT, BANK);
        aantalAzen = KaartSpel.bepaalAantalAzen(bank);
        KaartSpel.getWaarde(bank, waardenBank, aantalAzen); // 036
        KaartSpel.printKaarten(bank, BANK, aantalAzen, waardenBank);
        KaartSpel.setMaxWaardeBank(bank, aantalAzen, waardenBank);
    }

    private static boolean isSpelen(ArrayList<Kaart> kaarten2, ArrayList<Kaart> speler, boolean isSpelen) {
        if (isBankOntploft(KaartSpel.vraagEenKaart(kaarten2, speler, "[ case k ]", SPELER), 21)) {
            isSpelen = false;
            prtVerlies();
            prt( SPELER + " verliest !!");
            return isSpelen;
        }
        return isSpelen;
    }

    private static boolean isEersteBeurt(ArrayList<Kaart> kaarten2, ArrayList<Kaart> speler, ArrayList<Kaart> bank, boolean eersteBeurt) {
        if (eersteBeurt) {
            KaartSpel.setMaxWaardeBank(0);
            KaartSpel.setMaxWaardeSpeler(0);
            KaartSpel.vraagEenKaart(kaarten2, speler, NOPRINT, SPELER);
            KaartSpel.vraagEenKaart(kaarten2, speler, "eersteBeurt", SPELER);
            KaartSpel.vraagEenKaart(kaarten2, bank, NOPRINT, BANK);
            KaartSpel.vraagEenKaart(kaarten2, bank, NOPRINT, BANK);
            KaartSpel.printMenu();
            eersteBeurt = false;
        }
        return eersteBeurt;
    }

    private static ArrayList<Kaart> getKaarten(ArrayList<Kaart> speler, ArrayList<Kaart> bank) {
        ArrayList<Kaart> kaarten1;
        ArrayList<Kaart> kaarten2;
        speler.clear();
        bank.clear();
        kaarten1 = KaartSpel.maakKaarten();
        kaarten2 = KaartSpel.schudKaarten(kaarten1);
        KaartSpel.setMaxWaardeSpeler(0);
        KaartSpel.setMaxWaardeBank(0);
        KaartSpel.printMenu();
        return kaarten2;
    }

    private static boolean isSpelen(boolean isSpelen) {
        if (KaartSpel.getMaxWaardeSpeler() == KaartSpel.getMaxWaardeBank()) {
            prt("Gelijk " + KaartSpel.getMaxWaardeSpeler() + " , Bank wint");
            prtGelijk();
            isSpelen = false;
        } else if (KaartSpel.getMaxWaardeSpeler() < KaartSpel.getMaxWaardeBank()) {
            prt( BANK + ":" + KaartSpel.getMaxWaardeBank() + " - Speler:" + KaartSpel.getMaxWaardeSpeler() + " " + BANK + " wint");
            prtVerlies();
            isSpelen = false;
        } else if (isBankOntploft(KaartSpel.getMaxWaardeSpeler(), KaartSpel.getMaxWaardeBank())) {
            prt( BANK + ":" + KaartSpel.getMaxWaardeBank() + " - Speler:" + KaartSpel.getMaxWaardeSpeler() + " " + SPELER + " wint");
            prtWinst();
            isSpelen = false;
        }
        return isSpelen;
    }

    static void prt(String prt) {
        System.out.print("                                           ");
        System.out.println(prt);
    }

    static void prtVerlies() {
        SpelerA.setAccountA(-SpelerA.getInlegA());
        prt("Verlies: -" + SpelerA.getInlegA() + ACCOUNT + (SpelerA.getAccountA()));
    }

    static void prtGelijk() {
        SpelerA.setAccountA(-SpelerA.getInlegA());
        prt("Gelijk: -" + SpelerA.getInlegA() + ACCOUNT + (SpelerA.getAccountA()));
    }

    static void prtWinst() {
        SpelerA.setAccountA(SpelerA.getInlegA());
        prt("Winst: +" + SpelerA.getInlegA() + ACCOUNT + (SpelerA.getAccountA()));
    }

    static void prtWinstBlackJack() {
        SpelerA.setAccountA(SpelerA.getInlegA());
        prt("BlackJack: +" + (SpelerA.getInlegA()) + ACCOUNT + (SpelerA.getAccountA()));
    }
}

