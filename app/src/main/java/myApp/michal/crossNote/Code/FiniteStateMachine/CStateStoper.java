package myApp.michal.crossNote.Code.FiniteStateMachine;

public enum CStateStoper implements ICState {
    STATE_IDLE {
        @Override
        public Enum<CStateStoper> handleState() {
            return STATE_COUNT_DOWN;
        }
    },
    STATE_PLAY {
        @Override
        public Enum<CStateStoper> handleState() {
            m_handler.onPlayHandler();
            return STATE_PAUSE;
        }
    },
    STATE_PAUSE {
        @Override
        public Enum<CStateStoper> handleState() {
            m_handler.onPauseHandler();
            return STATE_PLAY;
        }
    },
    STATE_RESET {
        @Override
        public Enum<CStateStoper> handleState() {
            m_handler.onResetHandler();
            return STATE_COUNT_DOWN;
        }
    },
    STATE_COUNT_DOWN {
        @Override
        public Enum<CStateStoper> handleState() {
            m_handler.onCountDownHandler();
            return STATE_PAUSE;
        }
    },
    STATE_RESET_ALL {
        @Override
        public Enum<CStateStoper> handleState() {
            m_handler.onResetAll();
            return STATE_IDLE;
        }
    };
    private static IHandlerEvent m_handler = new StoperHandlerEvent();
}
