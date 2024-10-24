package org.example.entity;

public enum TrainingType {
    STRENGTH_TRAINING {
        @Override
        public Long getId() {
            return 1L;
        }
    },
    AEROBIC {
        @Override
        public Long getId() {
            return 2L;
        }
    },
    BODYBUILDING {
        @Override
        public Long getId() {
            return 3L;
        }
    },
    FLEXIBILITY_TRAINING {
        @Override
        public Long getId() {
            return 4L;
        }
    },
    WEIGHTLIFTING {
        @Override
        public Long getId() {
            return 5L;
        }
    };

    public abstract Long getId();
}
