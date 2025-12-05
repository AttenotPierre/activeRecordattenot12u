package activeRecord;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPersonne {

    @BeforeEach
    public void setup() throws SQLException {
        DBConnection.setNomDB("testpersonne");
        Personne.createTable();
        new Personne("Spielberg", "Steven").save();
        new Personne("Scott", "Ridley").save();
        new Personne("Kubrick", "Stanley").save();
        new Personne("Fincher", "David").save();
    }

    @AfterEach
    public void clean() throws SQLException {
        Personne.deleteTable();
    }

    // 7.1 : test de findAll
    @Test
    public void testFindAll() throws SQLException, ClassNotFoundException {
        ArrayList<Personne> personnes = Personne.findAll();

        assertTrue(personnes.size() >= 4,
                "Il doit y avoir au moins 4 personnes dans la base");

        boolean foundSpielberg = personnes.stream()
                .anyMatch(p -> p.getNom().equals("Spielberg")
                        && p.getPrenom().equals("Steven"));

        assertTrue(foundSpielberg,
                "Spielberg Steven doit etre present dans la base");
    }

    // 7.2 : test de findById
    @Test
    public void testFindById() throws SQLException {
        Personne p1 = Personne.findById(1);
        assertNotNull(p1, "findById(1) ne doit pas renvoyer null");
        assertEquals(1, p1.getId());
        assertEquals("Spielberg", p1.getNom());
        assertEquals("Steven", p1.getPrenom());

        Personne pInconnu = Personne.findById(9999);
        assertNull(pInconnu, "findById(9999) doit renvoyer null");
    }

    // 7.3 : test de findByName
    @Test
    public void testFindByName() throws SQLException {
        List<Personne> scotts = Personne.findByName("Scott");

        assertFalse(scotts.isEmpty(),
                "findByName('Scott') ne doit pas renvoyer une liste vide");

        for (Personne p : scotts) {
            assertEquals("Scott", p.getNom());
        }

        List<Personne> inconnus = Personne.findByName("NomQuiNexistePas");
        assertTrue(inconnus.isEmpty(),
                "findByName sur un nom inexistant doit renvoyer une liste vide");
    }

    // 8.4 : test de delete
    @Test
    public void testDelete() throws SQLException {
        Personne p = Personne.findById(1);
        assertNotNull(p);
        p.delete();
        assertEquals(-1, p.getId());
        Personne p2 = Personne.findById(1);
        assertNull(p2);
    }

    // 8.5 : test de save (nouvelle personne)
    @Test
    public void testSaveNew() throws SQLException {
        Personne p = new Personne("Nolan", "Christopher");
        assertEquals(-1, p.getId());
        p.save();
        assertNotEquals(-1, p.getId());
    }

    // 8.5 : test de save (update)
    @Test
    public void testUpdate() throws SQLException {
        Personne p = Personne.findById(1);
        p.save();
        Personne p2 = Personne.findById(1);
        assertEquals("Spielberg", p2.getNom());
    }
}