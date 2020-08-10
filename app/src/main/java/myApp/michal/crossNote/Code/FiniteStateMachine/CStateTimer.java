package myApp.michal.crossNote.Code.FiniteStateMachine;

public enum CStateTimer implements ICState {

    STATE_TEST_IDLE {
        @Override
        public Enum<CStateTimer> handleState() {
            return STATE_TEST_CDT;
        }
    },

    STATE_TEST_PLAY {
        @Override
        public Enum<CStateTimer> handleState() {
            m_handler.onPlayHandler();
            return STATE_TEST_PAUSE;
        }
    },

    STATE_TEST_PAUSE {
        @Override
        public Enum<CStateTimer> handleState() {
            m_handler.onPauseHandler();
            return STATE_TEST_PLAY;
        }
    },

    STATE_TEST_RESET {
        @Override
        public Enum<CStateTimer> handleState() {
            m_handler.onResetHandler();
            return STATE_TEST_PLAY;
        }
    },

    STATE_TEST_CDT {
        @Override
        public Enum<CStateTimer> handleState() {
            m_handler.onCountDownHandler();
            return STATE_TEST_PAUSE;
        }
    };
    IHandlerEvent m_handler = new TimerHandlerEvent();
}
