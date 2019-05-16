package szamlak.adatok;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OraallasTest {
    Oraallas tesztOraallas = new Oraallas(2018, 12, 99, 199,
            100.0, 200, 299, 200.0,
            10000, 20000);


    @Test
    public void gazFogyasztasSzamolo() {
        assertEquals(100,
                tesztOraallas.getAktualisGazOraallas() - tesztOraallas.getElozoGazOraallas());

    }

    @Test
    public void villanyFogyasztasSzamolo() {
        assertEquals(99,
                tesztOraallas.getAktualisVillanyOraallas() - tesztOraallas.getElozoVillanyOraallas());
    }
}