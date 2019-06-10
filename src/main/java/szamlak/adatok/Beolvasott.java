package szamlak.adatok;

public class Beolvasott {

    private static Beolvasott egyPeldany = null;

    private String aktualisGaz;
    private String gazEgysegar;
    private String gazAlapDij;
    private String aktualisVillany;
    private String villanyEgysegar;
    private String kozosKoltseg;
    private String lakber;
    private double csereGaz;
    private double csereVillany;
    private double csereGazLezaro;
    private double csereVillanyLezaro;


//    public Beolvasott(String aktualisGaz, String gazEgysegar, String gazAlapDij, String aktualisVillany, String villanyEgysegar, String kozosKoltseg, String lakber, double csereGaz, double csereVillany) {
//        this.aktualisGaz = aktualisGaz;
//        this.gazEgysegar = gazEgysegar;
//        this.gazAlapDij = gazAlapDij;
//        this.aktualisVillany = aktualisVillany;
//        this.villanyEgysegar = villanyEgysegar;
//        this.kozosKoltseg = kozosKoltseg;
//        this.lakber = lakber;
//        this.csereGaz = csereGaz;
//        this.csereVillany = csereVillany;
//    }

//    public Beolvasott() {
//    }

    private Beolvasott() {}

    public static Beolvasott getInstance() {
        if (egyPeldany == null) {
            egyPeldany = new Beolvasott();
        }
        return egyPeldany;
    }

    public void kiir() {
        System.out.println("Aktualis gaz: " + this.getAktualisGaz());
        System.out.println("Gaz egysegar: " + this.getGazEgysegar());
        System.out.println("Gaz alapdij: " + this.getGazAlapDij());
        System.out.println("Aktualis villany: " + this.getAktualisVillany());
        System.out.println("Villany egysegar: " + this.getVillanyEgysegar());
        System.out.println("Kozos koltseg: " + this.getKozosKoltseg());
        System.out.println("Lakber: " + this.getLakber());
        System.out.println("CsereGaz: " + this.getCsereGaz());
        System.out.println("CsereVillany: " + this.getCsereVillany());
        System.out.println("CsereGazLezaro: " + this.getCsereGazLezaro());
        System.out.println("CsereVillanyLezaro: " + this.getCsereVillanyLezaro());
        System.out.println("\n***************************************\n");
    }

    public double getCsereGaz() {
        return csereGaz;
    }

    public void setCsereGaz(double csereGaz) {
        this.csereGaz = csereGaz;
    }

    public double getCsereVillany() {
        return csereVillany;
    }

    public void setCsereVillany(double csereVillany) {
        this.csereVillany = csereVillany;
    }

    public String getAktualisGaz() {
        return aktualisGaz;
    }

    public void setAktualisGaz(String aktualisGaz) {
        this.aktualisGaz = aktualisGaz;
    }

    public String getGazEgysegar() {
        return gazEgysegar;
    }

    public void setGazEgysegar(String gazEgysegar) {
        this.gazEgysegar = gazEgysegar;
    }

    public String getGazAlapDij() {
        return gazAlapDij;
    }

    public void setGazAlapDij(String gazAlapDij) {
        this.gazAlapDij = gazAlapDij;
    }

    public String getAktualisVillany() {
        return aktualisVillany;
    }

    public void setAktualisVillany(String aktualisVillany) {
        this.aktualisVillany = aktualisVillany;
    }

    public String getVillanyEgysegar() {
        return villanyEgysegar;
    }

    public void setVillanyEgysegar(String villanyEgysegar) {
        this.villanyEgysegar = villanyEgysegar;
    }

    public String getKozosKoltseg() {
        return kozosKoltseg;
    }

    public void setKozosKoltseg(String kozosKoltseg) {
        this.kozosKoltseg = kozosKoltseg;
    }

    public String getLakber() {
        return lakber;
    }

    public void setLakber(String lakber) {
        this.lakber = lakber;
    }

    public double getCsereGazLezaro() {
        return csereGazLezaro;
    }

    public void setCsereGazLezaro(double csereGazLezaro) {
        this.csereGazLezaro = csereGazLezaro;
    }

    public double getCsereVillanyLezaro() {
        return csereVillanyLezaro;
    }

    public void setCsereVillanyLezaro(double csereVillanyLezaro) {
        this.csereVillanyLezaro = csereVillanyLezaro;
    }
}
