import java.util.List;

public class Monster {
    private String name;
    private List<String> spells;
    private  String url;

    public Monster(String name, List<String> spells) {
        this.name = name;
        this.spells = spells;
    }

    public Monster(String name, List<String> spells, String url) {
        this.name = name;
        this.spells = spells;
        this.url = url;
    }

    public Monster(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", spells=" + spells +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Monster() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSpells() {
        return spells;
    }

    public void setSpells(List<String> spells) {
        this.spells = spells;
    }

}
