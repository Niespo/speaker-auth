package pl.androidland.responses;

public class RecognitionInfo {

    private final String name;
    private final int likehoodRatio;
    private final double distance;

    private RecognitionInfo(Builder builder) {
        this.name = builder.name;
        this.likehoodRatio = builder.likehoodRatio;
        this.distance = builder.distance;
    }


    public double getDistance() {
        return distance;
    }

    public int getLikehoodRatio() {
        return likehoodRatio;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;
        private int likehoodRatio;
        private double distance;

        public static Builder newInfo() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLikehoodRatio(int likehoodRatio) {
            this.likehoodRatio = likehoodRatio;
            return this;
        }

        public Builder withDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public RecognitionInfo build() {
            return new RecognitionInfo(this);
        }
    }

}
