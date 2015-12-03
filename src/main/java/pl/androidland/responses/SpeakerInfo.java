package pl.androidland.responses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SpeakerInfo {

    private final String name;
    private final Collection<String> voiceFilesPaths;
    private final Date date;

    private SpeakerInfo(Builder builder) {
        this.name = builder.name;
        this.voiceFilesPaths = builder.voiceFilesPaths;
        this.date = builder.date;
    }

    public String getName() {
        return name;
    }

    public Collection<String> getVoiceFilesPaths() {
        return voiceFilesPaths;
    }

    public Date getDate() {
        return date;
    }

    public static class Builder {
        private String name;
        private List<String> voiceFilesPaths;
        private Date date;

        public static Builder newInfo() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withVoiceFilesPaths(List<String> voiceFilesPaths) {
            this.voiceFilesPaths = new ArrayList<>(voiceFilesPaths);
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public SpeakerInfo build() {
            return new SpeakerInfo(this);
        }
    }

}
