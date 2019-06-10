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
    private static String filenev = "src/main/resources/rezsi.json";
    private List<Oraallas> rezsiAdatok = new ArrayList<>();

    public static AdatKezelo getInstance() {
        return peldany;
    }

    public List<Oraallas> getRezsiAdatok() {
        return rezsiAdatok;
    }

    public void betoltSzamlak() {
        Gson gson = new GsonBuilder().create();
        Type carListType = new TypeToken<ArrayList<Oraallas>>(){}.getType();
        try (JsonReader reader = new JsonReader(new FileReader(filenev))) {
            rezsiAdatok = gson.fromJson(reader, carListType);
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
        int kulonbseg = kulonbsegSzamito(elem);
        if (kulonbseg >= 0) {
            for (int i = 0; i < kulonbseg; i++) {
                if (elem.getHonap() == 7 || elem.getHonap() == 8) {
                    alberlet = alberlet + elem.getLakber() / 2;
                } else alberlet = alberlet + elem.getLakber();
            }
        }else alberlet = (kulonbseg + 12) * elem.getLakber();
        return alberlet;
    }

    public int kulonbsegSzamito(Oraallas elem) {
        if (elemsorszam(elem) == 0) {
            return 2; //1 lenne, de az elso szamla pont 2 honapos (januar es februar egyutt)
        }
        int kulonbseg = elem.getHonap() - rezsiAdatok.get(elemsorszam(elem) - 1).getHonap();
        return kulonbseg;
    }

    private int elemsorszam(Oraallas elem) {
        return rezsiAdatok.indexOf(elem);
    }
}
