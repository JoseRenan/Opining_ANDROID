package br.com.opining.mvp.settings.profile;

import br.com.opining.R;
import br.com.opining.helpers.ValidatorHelper;

public class EditProfilePresenterImpl implements EditProfilePresenter, ProfileEditor.ProfileEditorListener {

    private EditProfileView mView;
    private ProfileEditor mModel;

    public EditProfilePresenterImpl(EditProfileView mView) {
        this.mModel = new EditProfileModel(this);
        this.mView = mView;
        mModel.loadActualNameAndEmail();
    }

    @Override
    public void doUpdate(String newEmail, String newName) {
        if (validateInformations(newEmail, newName)) {
            mModel.doUpdate(newEmail, newName);
        }
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }

    private boolean validateInformations(String email, String name) {

        boolean isValid = true;

        if (email.isEmpty()) {
            isValid = false;
            mView.showInvalidEmailError(mView.get().getString(R.string.error_email_empty));
        } else if (!ValidatorHelper.validateEmail(email)) {
            isValid = false;
            mView.showInvalidEmailError(mView.get().getString(R.string.error_email));
        }

        if (!ValidatorHelper.validateName(name)) {
            isValid = false;
            mView.showInvalidNameError(mView.get().getString(R.string.error_name));
        }

        return isValid;
    }

    @Override
    public void onUpdadeSuccessful() {
        mView.notifySuccessfulEdit();
        mModel.loadActualNameAndEmail();
    }

    @Override
    public void onUpdateError(Exception e) {
        //TODO catch exceptions received
        mView.notifyErrorEdit(mView.get().getString(R.string.error));
    }

    @Override
    public void onLoadActualNameAndEmail(String name, String email) {
        mView.setActualNameAndEmail(name, email);
    }
}
