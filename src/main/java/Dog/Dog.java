package Dog;

public class Dog {
    private String id;
    private String name;
    private String race;
    private int age;

    // Constructeur
    public Dog(final String id, final String name, final String race, final int age) {
        super();
        this.id = id;
        this.name = name;
        this.race = race;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}