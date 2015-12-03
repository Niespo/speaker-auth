package pl.androidland.responses;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMessageFactory {

    public static ResponseEntity<Response> getFailUploadResponse() {
        return getResponse(ResponseType.FAIL, "File has been not upload");
    }

    public static ResponseEntity<Response> getFailRecognitionResponse() {
        return getResponse(ResponseType.FAIL, "There is no chance to recognize this voice");
    }

    public static <T> ResponseEntity<Response> getSpeakerInfoResponse(T speakerInfo) {
        return getResponse(ResponseType.SUCCESSFUL, "Results of voice recognition have been found", speakerInfo);
    }

    public static ResponseEntity<Response> getRegisterResponse(String name) {
        return getResponse(ResponseType.SUCCESSFUL, String.format("Speaker with name %s has already created", name), name);
    }

    public static ResponseEntity<Response> getNotRegisterResponse(String name) {
        return getResponse(ResponseType.FAIL, String.format("Could not register speaker. Speaker with name %s has already existed", name), name);
    }

    public static ResponseEntity<Response> getNotSpeakerFoundResponse(String name) {
        return getResponse(ResponseType.FAIL, String.format("No found speaker with name %s ", name));
    }

    public static ResponseEntity<Response> getIncorrectPasswordResponse(String name) {
        return getResponse(ResponseType.FAIL, String.format("Incorrect password for speaker with name %s ", name));
    }

    public static ResponseEntity<Response> getVoiceAddedResponse(SpeakerInfo speakerInfo) {
        return getResponse(ResponseType.SUCCESSFUL, "Voice has been added.", speakerInfo);
    }

    private static <T> ResponseEntity<Response> getResponse(ResponseType responseType, String message, T content) {
        return new ResponseEntity<>(ResponseMessage.Builder.
                newResponse(responseType)
                .withMessage(message)
                .withContent(content)
                .build(), HttpStatus.OK);
    }

    private static ResponseEntity<Response> getResponse(ResponseType responseType, String message) {
        return new ResponseEntity<>(ResponseMessage.Builder.
                newResponse(responseType)
                .withMessage(message)
                .build(), HttpStatus.OK);
    }

}
