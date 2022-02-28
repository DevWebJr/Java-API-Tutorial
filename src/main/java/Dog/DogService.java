package Dog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DogService {
    private final Map<String, Dog> dogs = new HashMap<String, Dog>();

    // Constructeur
    public DogService() {
        super();
        initDogs();
    }

    // Initialiser les objets
    private void initDogs() {
        final Dog lassie = new Dog("12345fh", "Lassie", "colley", 12);
        final Dog milou = new Dog("e7654", "Milou", "fox-terrier", 11);
        final Dog scoobydoo = new Dog("s93d78", "Scooby-Doo", "Danois", 7);
        final Dog idefix = new Dog("6222mk9p", "Idefix", "Bichon", 17);
        // Ajouts dans la map
        dogs.put(lassie.getId(), lassie);
        dogs.put(milou.getId(), milou);
        dogs.put(scoobydoo.getId(), scoobydoo);
        dogs.put(idefix.getId(), idefix);
    }

    // Récupérer l'ensemble des objets
    public List<Dog> findAll() {
        return dogs.values().stream()
                .collect(Collectors.toList());
        // return new ArrayList<Dog>(dogs.values());
    }
    // Récupérer un objet selon l'id
    public Dog findById(final String id) {
        return dogs.get(id);
    }
    // Mettre à jour un objet
    public Dog update(final Dog dog) {
        dogs.put(dog.getId(), dog);
        return dog;
    }
    // Ajouter un objet
    public Dog add(final Dog dog) {
        final String id = "fr" + System.currentTimeMillis() + "d";
        final Dog newdog = new Dog(id,
                dog.getName(),
                dog.getRace(),
                dog.getAge());
        dogs.put(id, newdog);
        return newdog;
    }
    // Effacer un objet
    public void remove(final String id) {
            dogs.remove(id);
    }
}
