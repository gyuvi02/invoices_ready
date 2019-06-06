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
import java.util.ListIterator;

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

    public int alapdijSzamito(Oraallas elem) {
        ListIterator<Oraallas> listIterator = rezsiAdatok.listIterator();
        if (listIterator.hasPrevious()) {
            int kulonbseg = elem.getHonap() - listIterator.previous().getHonap();
            return kulonbseg >= 0 ? kulonbseg * elem.gazAlapdij : (kulonbseg + 12) * elem.gazAlapdij;
//            if (kulonbseg < 0) {
//                return (kulonbseg + 12) * elem.gazAlapdij;
//            }
//            return kulonbseg * elem.gazAlapdij;
        }else return elem.gazAlapdij;
    }

}
