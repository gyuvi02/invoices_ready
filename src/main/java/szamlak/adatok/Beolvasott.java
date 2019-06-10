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

    private Beolvasott() {}

    public static Beolvasott getInstance() {
        if (egyPeldany == null) {
            egyPeldany = new Beolvasott();
        }
        return egyPeldany;
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
