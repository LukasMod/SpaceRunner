package model;

public enum SHIP {
    BLUE("ships/playerShip3_blue.png", "hud/playerLife3_blue.png"),
    GREEN("ships/playerShip3_green.png", "hud/playerLife3_green.png"),
    ORANGE("ships/playerShip3_orange.png", "hud/playerLife3_orange.png"),
    RED("ships/playerShip3_red.png", "hud/playerLife3_red.png");

    private String urlShip;
    private String urlLife;

    private SHIP(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public String getUrlLife() {
        return urlLife;
    }
}
