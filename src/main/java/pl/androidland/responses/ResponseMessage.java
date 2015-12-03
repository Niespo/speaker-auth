package pl.androidland.responses;


public class ResponseMessage<T> implements Response {

    private final ResponseType type;
    private final String message;
    private final T content;

    private ResponseMessage(Builder<T> builder) {
        this.type = builder.type;
        this.message = builder.message;
        this.content = builder.content;
    }

    @Override
    public ResponseType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getContent() {
        return content;
    }

    public static class Builder<S> {
        private final ResponseType type;
        private String message;
        private S content;

        public static <K> Builder<K> newResponse(ResponseType type) {
            return new Builder<>(type);
        }

        private Builder(ResponseType type) {
            this.type = type;
        }

        public Builder<S> withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder<S> withContent(S content) {
            this.content = content;
            return this;
        }

        public ResponseMessage<S> build() {
            return new ResponseMessage<>(this);
        }
    }

}
