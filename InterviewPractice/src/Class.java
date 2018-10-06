import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Class {

    public static void main(String[] args) {
        basicTestsHashComp();
    }

    public static class Pokemon implements Comparable<Pokemon> {
        private String name;
        private int pokedex_number;
        private HashSet<String> all_types = new HashSet<>();
        private ArrayList<String> types;

        private void InstantiateAllTypes() {
            String[] all_types = {"Psychic", "Dark", "Fighting", "Electric", "Water", "Bug", "Ice", "Fire", "Ground", "Rock", "Fairy", "Poison", "Normal", "Ghost", "Dragon", "Grass", "Flying", "Steel"};

            for (String type : all_types)  {
                this.all_types.add(type);
            }
        }

        public Pokemon(String name, int pokedex_number, String ...types) throws NotAPokemonTypeException{
            InstantiateAllTypes();
            this.name = name;
            this.pokedex_number = pokedex_number;
            this.types = new ArrayList<>();

            for (String type : types) {
                if (!all_types.contains(type)) {
                    throw new NotAPokemonTypeException();
                }
                else {
                    this.types.add(type);
                }
            }
        }

        @Override
        public int hashCode() {
            String name = this.name.toUpperCase();
            int res = 0;
            for (int i = 0; i < name.length(); i++) {
                res += (int)(name.charAt(i)) * i;
            }

            return res;
        }

        @Override
        public String toString() {
            return "{" + this.name + ", " + this.pokedex_number + "}";
        }

        public Boolean equals(Pokemon other) {
            return this.compareTo(other) == 0;
        }

        @Override
        public int compareTo(Pokemon other) {
            if (this.pokedex_number < other.pokedex_number) {
                return -1;
            }
            else if (this.pokedex_number > other.pokedex_number) {
                return 1;
            }
            return 0;
        }

        class NotAPokemonTypeException extends Exception {
        }

    }

    public static void basicTestsHashComp() {
        PriorityQueue<Pokemon> pq = new PriorityQueue<>();
        ArrayList<Pokemon> al = new ArrayList<>();
        HashMap<Pokemon, ArrayList<String>> hm = new HashMap<>();

        try {
            Pokemon pikachu = new Pokemon("Pikachu", 25, "Electric");
            Pokemon torchic = new Pokemon("Torchic", 255, "Fire");
            Pokemon celebi = new Pokemon("Celebi", 251, "Psychic", "Grass");
            al.add(torchic);
            hm.put(torchic, torchic.types);
            al.add(pikachu);
            hm.put(pikachu, pikachu.types);
            al.add(celebi);
            hm.put(celebi, celebi.types);

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

            assert hm.get(torchic).equals(torchic.types);
            assert hm.get(pikachu).equals(pikachu.types);
            assert hm.get(celebi).equals(celebi.types);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}