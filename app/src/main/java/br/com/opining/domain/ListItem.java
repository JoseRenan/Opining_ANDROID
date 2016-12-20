package br.com.opining.domain;

public class ListItem {

    // Resource id
    private int icon;
    private int title;

    public ListItem(int icon, int title) {

        super();

        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
