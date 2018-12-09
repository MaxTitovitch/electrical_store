package dialog_construction;

import dialogs.CDialog;

public class DirectorByCreate {
    private CDialog authorizationDialog;
    private AbstractFrameDialogCreator dialogCreator;

    public DirectorByCreate(CDialog authorizationjDialog){
        this.authorizationDialog = authorizationjDialog;
    }

    public void setDialogCreator(AbstractFrameDialogCreator dialogCreator) {
        this.dialogCreator = dialogCreator;
    }

    public CDialog getDialog() {
        return dialogCreator.getFrameDialog();
    }

    public void createDialog() {
        dialogCreator.createDialog(authorizationDialog);
    }
}
