package model;

public enum SHIP {
    BLUE("ships/playerShip3_blue.png"),
    GREEN("ships/playerShip3_green.png"),
    ORANGE("ships/playerShip3_orange.png"),
    RED("ships/playerShip3_red.png");

    private String urlShip;

    private SHIP(String urlShip) {
        this.urlShip = urlShip;
    }

    public String getUrl () {
        return this.urlShip;
    }

}
