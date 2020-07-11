class SpelerA {
    private SpelerA(){
    }
    private static int inlegA = 10;
    private static double accountA = 150;

    public static void setAccountA(int inlegA) {
        accountA += inlegA;
    }

    public static double getAccountA() {
        return accountA;
    }

    public static int getInlegA() {
        return inlegA;
    }
}
