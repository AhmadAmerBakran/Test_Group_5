package easv_MTunes.gui.Controller;

import easv_MTunes.gui.Model.MTModel;

public abstract class ControllerManager {
    private MTModel model;

    public MTModel getModel() {
        return model;
    }

    public void setModel(MTModel model) {
        this.model = model;
    }

    public abstract void setup() throws Exception;
}
