package szamlak.adatok;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Oraallas {
    int ev;
    int honap;
    int elozoGazOraallas;
    int aktualisGazOraallas;
    double egysegarGaz;
    int elozoVillanyOraallas;
    int aktualisVillanyOraallas;
    double egysegarVillany;
    int kozosKoltseg;
    int lakber;

    public Oraallas(int ev, int honap, int elozoGazOraallas, int aktualisGazOraallas, double egysegarGaz,
                    int elozoVillanyOraallas, int aktualisVillanyOraallas, double getEgysegarVillany, int kozosKoltseg,
                    int lakber) {
        this.ev = ev;
        this.honap = honap;
        this.elozoGazOraallas = elozoGazOraallas;
        this.aktualisGazOraallas = aktualisGazOraallas;
        this.egysegarGaz = egysegarGaz;
        this.elozoVillanyOraallas = elozoVillanyOraallas;
        this.aktualisVillanyOraallas = aktualisVillanyOraallas;
        this.egysegarVillany = getEgysegarVillany;
        this.kozosKoltseg = kozosKoltseg;
        this.lakber = lakber;
    }

    public static String reszletesSzoveg(Oraallas elem) {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        formatSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("###,###.##", formatSymbols);
        int gazFogyasztas = elem.gazFogyasztasSzamolo();
        int villanyFogyasztas = elem.villanyFogyasztasSzamolo();
        int gazHavi = ((int) Math.round(gazFogyasztas * elem.getEgysegarGaz()));
        int villanyHavi = (int) Math.round(villanyFogyasztas * elem.getEgysegarVillany());
        StringBuilder sb = new StringBuilder();
        sb.append("Gáz: \nFogyasztás a megelőző hónap óta: " + gazFogyasztas + " köbméter");
        sb.append("\nA gázszámla ebben az időszakban: " + df.format(gazHavi) + " Ft");
        sb.append("\nA gázóvillanyraállás: " + df.format(elem.getAktualisGazOraallas()) );
        sb.append("\n\nVillany: \nFogyasztás a megelőző hónap óta: " + villanyFogyasztas + " kW");
        sb.append("\nA villanyszámla ebben az időszakban: " + df.format(villanyHavi) + " Ft");
        sb.append("\nA villanyóraállás: " + df.format(elem.aktualisVillanyOraallas) );
        sb.append("\n\nKözös költség: " + df.format(elem.getKozosKoltseg()) + " Ft");
        sb.append("\n\nAlbérleti díj: " + df.format(elem.getLakber()) + " Ft");
        sb.append("\n\nÖsszes fizetendő: " + df.format(gazHavi + villanyHavi + elem.getKozosKoltseg() + elem.getLakber()) + " Ft");
        return sb.toString();
    }

    public int gazFogyasztasSzamolo() {

        return this.getAktualisGazOraallas() - this.getElozoGazOraallas();
    }

    public int villanyFogyasztasSzamolo() {
        return this.getAktualisVillanyOraallas() - this.getElozoVillanyOraallas();
    }

    public void hozzaadOraallas(Oraallas ujOraallas) {
        AdatKezelo.getInstance().adatHozzaad(ujOraallas);
    }

    public void elemTorles(Oraallas torlendo) {
        AdatKezelo.getInstance().adatTorles(torlendo);
    }


    @Override
    public String toString() {
        return this.getEv() + ". " + this.getHonap() + ". hónap";
    }

    public int getEv() {
        return ev;
    }

    public int getHonap() {
        return honap;
    }

    public int getElozoGazOraallas() {
        return elozoGazOraallas;
    }

    public int getAktualisGazOraallas() {
        return aktualisGazOraallas;
    }

    public double getEgysegarGaz() {
        return egysegarGaz;
    }

    public int getElozoVillanyOraallas() {
        return elozoVillanyOraallas;
    }

    public int getAktualisVillanyOraallas() {
        return aktualisVillanyOraallas;
    }

    public double getEgysegarVillany() {
        return egysegarVillany;
    }

    public int getKozosKoltseg() {
        return kozosKoltseg;
    }

    public int getLakber() {
        return lakber;
    }


}
