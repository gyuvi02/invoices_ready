package szamlak.adatok;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdatKezelo {
    private static AdatKezelo peldany = new AdatKezelo();
    private static String filenev = "D:\\/rezsi.json";
    private List<Oraallas> rezsiAdatok = new ArrayList<>();

    public static AdatKezelo getInstance() {
        return peldany;
    }

    public List<Oraallas> getRezsiAdatok() {
        return rezsiAdatok;
    }

    public void betoltSzamlak() {
        Gson gson = new GsonBuilder().create();
        Type rezsiType = new TypeToken<ArrayList<Oraallas>>(){}.getType();
        try (JsonReader reader = new JsonReader(new FileReader(filenev))) {
            rezsiAdatok = gson.fromJson(reader, rezsiType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void kiirSzamlak() {
        Gson gson = new GsonBuilder().create();
        try (FileWriter writer = new FileWriter(filenev)) {
            writer.write(gson.toJson(rezsiAdatok));
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    public void adatHozzaad(Oraallas ujOraallas) {
        rezsiAdatok.add(ujOraallas);
    }

    public void adatTorles(Oraallas torlendo) {
        rezsiAdatok.remove(torlendo);
    }

    public double alapdijSzamito(Oraallas elem) {
            int kulonbseg = kulonbsegSzamito(elem);
            return kulonbseg >= 0 ? kulonbseg * elem.gazAlapdij : (kulonbseg + 12) * elem.gazAlapdij;
    }

    public int kozosKoltsegSzamito(Oraallas elem) {
        int kulonbseg = kulonbsegSzamito(elem);
            return kulonbseg >= 0 ? kulonbseg * elem.getKozosKoltseg(): (kulonbseg + 12) * elem.getKozosKoltseg();
    }

    public int berletSzamito(Oraallas elem) {
        int alberlet = 0;
        int kulon = 0;
        int kulonbseg = kulonbsegSzamito(elem);
        if (kulonbseg >= 0) {
            kulon = kulonbseg;}
        else kulon = kulonbseg + 12;
        for (int i = kulon-1; i >= 0; i--) {
            int aktualisHo = elem.getHonap()-i;
                if ( aktualisHo == 7 || aktualisHo == 8) {
                    alberlet += 0;
                } else alberlet = alberlet + elem.getLakber();
            }
        return alberlet;
    }

    public int kulonbsegSzamito(Oraallas elem) {
        if (elemsorszam(elem) == 0) {
            return 2; //1 lenne, de az elso szamla pont 2 honapos (januar es februar egyutt)
        }
        int kulonbseg = helyesKulonbseg(elem);
        return kulonbseg;
    }

    public int alberletKulonbsegSzamito(Oraallas elem) {
        int honapSzam = 0;
        int masEvKiegeszito = 0;
        int kulonbseg = kulonbsegSzamito(elem);
        if (elem.getHonap() - rezsiAdatok.get(elemsorszam(elem) - 1).getHonap() < 0) {
            masEvKiegeszito = 12;
        }
        for (int i = kulonbseg-1; i >= 0; i--) {
            int aktualisHo = elem.getHonap()-i + masEvKiegeszito;
            if ( aktualisHo == 7 || aktualisHo == 8) {
                honapSzam += 0;
            } else honapSzam++;
        }
        return honapSzam;

    }

    private int elemsorszam(Oraallas elem) {
        return rezsiAdatok.indexOf(elem);
    }

    private int helyesKulonbseg(Oraallas elem) {
        int kul = elem.getHonap() - rezsiAdatok.get(elemsorszam(elem) - 1).getHonap();
        if (kul >= 0) {
            return kul;
        } else return kul + 12;
    }
}
