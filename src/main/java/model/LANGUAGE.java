package model;

public enum LANGUAGE {
    PL("language/poland.png"),
    ENG("language/uk.png");

    private String urlLanguage;

  private LANGUAGE(String urlLanguage) {
        this.urlLanguage = urlLanguage;
    }

    public String getUrl() {
        return urlLanguage;
    }

    public void setUrl(String urlLanguage) {
        this.urlLanguage = urlLanguage;
    }
}
