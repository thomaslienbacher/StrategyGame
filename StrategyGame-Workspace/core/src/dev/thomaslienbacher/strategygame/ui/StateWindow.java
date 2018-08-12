package dev.thomaslienbacher.strategygame.ui;

import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

public class StateWindow extends VisWindow {

    public StateWindow(String title) {
        super(title);

        init();
    }

    private void init() {
        VisLabel label = new VisLabel("Hello");
        add(label);

        setWidth(300);
    }
}
