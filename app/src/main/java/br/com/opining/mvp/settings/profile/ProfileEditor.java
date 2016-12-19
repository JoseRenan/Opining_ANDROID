package br.com.opining.mvp.settings.profile;

public interface ProfileEditor {
    interface ProfileEditorListener {
        void onUpdadeSuccessful();
        void onUpdateError(Exception e);
    }
    void doUpdate(String newEmail, String newName);
}
