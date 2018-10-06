import java.util.ArrayList;
import java.util.HashSet;

public class Pokemon implements Comparable<Pokemon> {

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

    public String getName() {
        return this.name;
    }

    public int getPokedex_number() {
        return this.pokedex_number;
    }

    public ArrayList<String> getTypes() {
        return this.types;
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