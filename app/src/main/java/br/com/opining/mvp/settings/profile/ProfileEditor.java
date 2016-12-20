package br.com.opining.mvp.settings.profile;

public interface ProfileEditor {
    interface ProfileEditorListener {
        void onUpdadeSuccessful();
        void onUpdateError(Exception e);
        void onLoadActualNameAndEmail(String name, String email);
    }
    void doUpdate(String newEmail, String newName);
    void loadActualNameAndEmail();
}
