package pl.androidland.responses;


public interface Response<T> {
    ResponseType getType();
    String getMessage();
    T getContent();
}
