package br.com.opining.view.adapters;

/**
 * Created by José Renan on 18/09/2016.
 */
public class ListItem {

    // Resource id
    private int icon;
    private int title;

    public ListItem (int icon, int title) {

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
