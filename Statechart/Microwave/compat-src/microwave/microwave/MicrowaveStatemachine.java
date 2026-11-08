package microwave.microwave;

import com.yakindu.core.ITimed;
import com.yakindu.core.ITimerService;
import microwave.ITimer;
import microwave.ITimerCallback;
import microwave.generated.MicrowaveBehavior;

/**
 * API compatibility between the supplied 2020 UI and CREATE 5.6.
 * All states, guards, actions and timing decisions are generated from microwave.sct.
 * The original generated implementation in src is retained but excluded locally.
 */
public class MicrowaveStatemachine implements IMicrowaveStatemachine, ITimerCallback {
    private MicrowaveBehavior behavior = new MicrowaveBehavior();
    private ITimer legacyTimer;
    private SCInterfaceOperationCallback callback;

    private final SCInterface api = new SCInterface() {
        public void raiseHigh() { behavior.raiseHigh(); }
        public void raiseLow() { behavior.raiseLow(); }
        public void raiseDigit(long value) { behavior.raiseDigit(value); }
        public void raiseTimer() { behavior.raiseTimer(); }
        public void raiseStart() { behavior.raiseStart(); }
        public void raiseStop() { behavior.raiseStop(); }
        public void raiseOpen() { behavior.raiseOpen(); }
        public void raiseClose() { behavior.raiseClose(); }
        public long getPower() { return behavior.getPower(); }
        public void setPower(long value) { behavior.setPower(value); }
        public void setSCInterfaceOperationCallback(SCInterfaceOperationCallback value) {
            callback = value;
            connectCallback();
        }
    };

    private void connectCallback() {
        if (callback == null) return;
        behavior.setOperationCallback(new MicrowaveBehavior.OperationCallback() {
            public void displayTime(long t) { callback.displayTime(t); }
            public void display(String msg) { callback.display(msg); }
            public void clearDisplay() { callback.clearDisplay(); }
            public void cook() { callback.cook(); }
            public void stopCook() { callback.stopCook(); }
            public void beepOn() { callback.beepOn(); }
            public void beepOff() { callback.beepOff(); }
            public void closeDoor() { callback.closeDoor(); }
            public void openDoor() { callback.openDoor(); }
        });
    }

    public void setTimer(ITimer value) {
        legacyTimer = value;
        behavior.setTimerService(new ITimerService() {
            public void setTimer(ITimed target, int eventID, long time, boolean periodic) {
                legacyTimer.setTimer(MicrowaveStatemachine.this, eventID, time, periodic);
            }
            public void unsetTimer(ITimed target, int eventID) {
                legacyTimer.unsetTimer(MicrowaveStatemachine.this, eventID);
            }
        });
    }

    public void init() {
        if (behavior.isActive()) behavior.exit();
        behavior = new MicrowaveBehavior();
        if (legacyTimer != null) setTimer(legacyTimer);
        connectCallback();
    }
    public SCInterface getSCInterface() { return api; }
    public void enter() { behavior.enter(); }
    public void exit() { behavior.exit(); }
    public boolean isActive() { return behavior.isActive(); }
    public boolean isFinal() { return behavior.isFinal(); }
    public void runCycle() { behavior.triggerWithoutEvent(); }
    public void timeElapsed(int eventID) { behavior.raiseTimeEvent(eventID); }
}
