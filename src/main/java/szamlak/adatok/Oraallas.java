package szamlak.adatok;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Oraallas {
    protected int ev;
    protected int honap;
    protected double elozoGazOraallas;
    protected double aktualisGazOraallas;
    protected double egysegarGaz;
    protected double gazAlapdij;
    protected double elozoVillanyOraallas;
    protected double aktualisVillanyOraallas;
    protected double egysegarVillany;
    protected int kozosKoltseg;
    protected int lakber;
    protected double csereGaz;
    protected double csereVillany;

    public Oraallas(int ev, int honap, double elozoGazOraallas, double aktualisGazOraallas, double egysegarGaz, double gazAlapdij,
                    double elozoVillanyOraallas, double aktualisVillanyOraallas, double getEgysegarVillany, int kozosKoltseg,
                    int lakber, double csereGaz, double csereVillany) {
        this.ev = ev;
        this.honap = honap;
        this.elozoGazOraallas = elozoGazOraallas;
        this.aktualisGazOraallas = aktualisGazOraallas;
        this.egysegarGaz = egysegarGaz;
        this.gazAlapdij = gazAlapdij;
        this.elozoVillanyOraallas = elozoVillanyOraallas;
        this.aktualisVillanyOraallas = aktualisVillanyOraallas;
        this.egysegarVillany = getEgysegarVillany;
        this.kozosKoltseg = kozosKoltseg;
        this.lakber = lakber;
        this.csereGaz = csereGaz;
        this.csereVillany = csereVillany;
    }

    public static String reszletesSzoveg(Oraallas elem) {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        formatSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("###,###.##", formatSymbols);
        double gazFogyasztas = elem.gazFogyasztasSzamolo();
        double villanyFogyasztas = elem.villanyFogyasztasSzamolo();
        double gazHavi = gazFogyasztas * elem.getEgysegarGaz();
        double villanyHavi = villanyFogyasztas * elem.getEgysegarVillany();
        double alapdij = AdatKezelo.getInstance().alapdijSzamito(elem);
        int kozosK = AdatKezelo.getInstance().kozosKoltsegSzamito(elem);
        int alberlet = AdatKezelo.getInstance().berletSzamito(elem);
        int kulonbseg = AdatKezelo.getInstance().kulonbsegSzamito(elem);
        StringBuilder sb = new StringBuilder();
        sb.append("Gáz: \nFogyasztás a megelőző hónap óta: " + df.format(gazFogyasztas) + " köbméter");
        sb.append("\nA gázszámla ebben az időszakban: " + df.format(Math.round(gazHavi)) + " Ft");
        sb.append("\nA gázóraállás az időszak végén: " + df.format(elem.getAktualisGazOraallas()) );
        sb.append("\nGáz alapdíj (" + kulonbseg + " hónap): " + df.format(alapdij) + " Ft");
        sb.append("\n\nVillany: \nFogyasztás a megelőző hónap óta: " + df.format(villanyFogyasztas)+ " kW");
        sb.append("\nA villanyszámla ebben az időszakban: " + df.format(Math.round(villanyHavi)) + " Ft");
        sb.append("\nA villanyóraállás az időszak végén: " + df.format(elem.aktualisVillanyOraallas) );
        sb.append("\n\nKözös költség (" + kulonbseg + " hónap): " + df.format(kozosK) + " Ft");
        sb.append("\n\nAlbérleti díj (" + kulonbseg + " hónap): " + df.format(alberlet) + " Ft");
        sb.append("\n\nÖsszes fizetendő: " + df.format (Math.round(gazHavi + villanyHavi + alapdij +
                kozosK + alberlet)) + " Ft");
        return sb.toString();
    }

    public double gazFogyasztasSzamolo() {
        if (this.csereGaz != 0.0) {
            return this.getAktualisGazOraallas() + this.csereGaz;
        } else {
            return this.getAktualisGazOraallas() - this.getElozoGazOraallas();
        }
    }

    public double villanyFogyasztasSzamolo() {
        if (this.csereVillany != 0.0) {
            return this.getAktualisVillanyOraallas() + this.csereVillany;
        } else {
            return this.getAktualisVillanyOraallas() - this.getElozoVillanyOraallas();
        }
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

    public double getElozoGazOraallas() {
        return elozoGazOraallas;
    }

    public double getAktualisGazOraallas() {
        return aktualisGazOraallas;
    }

    public double getEgysegarGaz() {
        return egysegarGaz;
    }

    public double getGazAlapdij(){return gazAlapdij; }

    public double getElozoVillanyOraallas() {
        return elozoVillanyOraallas;
    }

    public double getAktualisVillanyOraallas() {
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
