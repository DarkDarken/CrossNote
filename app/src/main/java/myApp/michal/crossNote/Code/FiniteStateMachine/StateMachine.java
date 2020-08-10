package myApp.michal.crossNote.Code.FiniteStateMachine;

public class StateMachine {
    private ICState m_state;

    public StateMachine(ICState p_idleState) {
        setState(p_idleState);
        performEvent();
    }
    public void performEvent() {
        m_state = (ICState) m_state.handleState();
    }
    public ICState getState(){
        return m_state;
    }
    public void setState(ICState p_state){
        m_state = p_state;
    }

    @Override
    public String toString() {
        return "StateMachine{" +
                "Active state = " + m_state +
                '}';
    }
}
