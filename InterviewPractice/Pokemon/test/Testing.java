import org.junit.Test;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;


public class Testing {

    @Test
    public void basicTestsHashComp() {
        PriorityQueue<Pokemon> pq = new PriorityQueue<>();
        ArrayList<Pokemon> al = new ArrayList<>();
        HashMap<Pokemon, ArrayList<String>> hm = new HashMap<>();

        try {
            Pokemon pikachu = new Pokemon("Pikachu", 25, "Electric");
            Pokemon torchic = new Pokemon("Torchic", 255, "Fire");
            Pokemon celebi = new Pokemon("Celebi", 251, "Psychic", "Grass");
            al.add(torchic);
            hm.put(torchic, torchic.getTypes());
            al.add(pikachu);
            hm.put(pikachu, pikachu.getTypes());
            al.add(celebi);
            hm.put(celebi, celebi.getTypes());

            for (Pokemon p : al) {
                pq.add(p);
            }

            Collections.sort(al);
            assert al.get(0).equals(pikachu);
            assert pq.remove().equals(pikachu);
            assert al.get(1).equals(celebi);
            assert pq.remove().equals(celebi);
            assert al.get(2).equals(torchic);
            assert pq.remove().equals(torchic);

            assert hm.get(torchic).equals(torchic.getTypes());
            assert hm.get(pikachu).equals(pikachu.getTypes());
            assert hm.get(celebi).equals(celebi.getTypes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Pokemon.NotAPokemonTypeException.class)
    public void notAPokemonExceptionTest() throws Pokemon.NotAPokemonTypeException {
        Pokemon p = new Pokemon("NotAPokymon", -1, "Dark", "NotAPokymonType");
    }
}
