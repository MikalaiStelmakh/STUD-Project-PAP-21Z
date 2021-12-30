package z11.mqttSub.model;

public class Author {
    private int id;
    private String firstName;
    private String lastName;
    private int birthYear;
    private int deathYear;

    public Author(int id, String firstName, String lastName, int birthYear){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public Author(int id, String firstName, String lastName, int birthYear, int deathYear){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public int getBirthYear(){
        return this.birthYear;
    }

    public void setBirthYear(int birthYear){
        this.birthYear = birthYear;
    }

    public int getDeathYear(){
        return this.deathYear;
    }

    public void setDeathYear(int deathYear){
        this.deathYear = deathYear;
    }

    public String getName(){
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString(){
        return getName();
    }

}
