package cn.xfakir.saber.core.config;

public class Saber {
    private String name;
    private String weapon;

    public Saber(String name, String weapon) {
        this.name = name;
        this.weapon = weapon;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
