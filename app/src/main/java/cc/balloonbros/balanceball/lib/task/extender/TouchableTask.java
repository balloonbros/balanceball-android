package cc.balloonbros.balanceball.lib.task.extender;

import android.view.MotionEvent;
import android.view.View;

import cc.balloonbros.balanceball.lib.task.PluggableTask;

public class TouchableTask extends PluggableTask implements View.OnTouchListener {
    @Override
    protected void onRegister() {
        getTask().getGame().getView().setOnTouchListener(this);
    }

    @Override
    protected void onKilled() {
        getTask().getGame().getView().setOnTouchListener(null);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return ((Touchable) getTask()).onTouch(motionEvent);
    }
}
