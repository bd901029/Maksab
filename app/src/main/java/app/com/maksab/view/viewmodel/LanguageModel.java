package app.com.maksab.view.viewmodel;

public class LanguageModel {
    public String language = "";

    public LanguageModel(String language) {
        this.language = language;
    }

    public LanguageModel() {}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
