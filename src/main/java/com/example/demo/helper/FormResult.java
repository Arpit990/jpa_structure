package com.example.demo.helper;

import com.example.demo.data.Enums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class FormResult {
    private ResponseJSON responseJSON;

    public static FormResult success(boolean isSuccess, String message, Object responseData) {
        FormResult formResult = new FormResult();
        ResponseJSON responseJSON = new ResponseJSON();
        responseJSON.setSuccess(isSuccess);
        responseJSON.setMessage(message);
        responseJSON.setResponseCode(Enums.AjaxResponse.SUCCESS.toString());
        responseJSON.setResponseData(responseData);
        formResult.setResponseJSON(responseJSON);
        return formResult;
    }

    public static FormResult info() {
        FormResult formResult = new FormResult();
        ResponseJSON responseJSON = new ResponseJSON();
        responseJSON.setMessage("Info");
        responseJSON.setResponseCode(Enums.AjaxResponse.INFO.toString());
        formResult.setResponseJSON(responseJSON);
        return formResult;
    }

    public static FormResult warning() {
        FormResult formResult = new FormResult();
        ResponseJSON responseJSON = new ResponseJSON();
        responseJSON.setMessage("Warning");
        responseJSON.setResponseCode(Enums.AjaxResponse.WARNING.toString());
        formResult.setResponseJSON(responseJSON);
        return formResult;
    }

    public static FormResult processing() {
        FormResult formResult = new FormResult();
        ResponseJSON responseJSON = new ResponseJSON();
        responseJSON.setMessage("Processing");
        responseJSON.setResponseCode(Enums.AjaxResponse.PROCESSING.toString());
        formResult.setResponseJSON(responseJSON);
        return formResult;
    }

    public static FormResult error(Object modelState) {
        List<Error> errorList = getErrorsListFromModelState(modelState);
        ResponseJSON responseJSON = new ResponseJSON();

        if (errorList.size() > 1) {
            responseJSON.setMessage("A few fields are missing or invalid.");
        } else {
            responseJSON.setMessage(!errorList.isEmpty() ? (errorList.get(0).getMessage() != null ? errorList.get(0).getMessage() : "") : "");
        }

        responseJSON.setResponseCode(Enums.AjaxResponse.ERROR.toString());
        responseJSON.setErrors(errorList);
        FormResult formResult = new FormResult();
        formResult.setResponseJSON(responseJSON);
        return formResult;
    }

    public static FormResult errorWithCustomMessage(Object modelState, String customMessage) {
        List<Error> errorList = getErrorsListFromModelState(modelState);
        ResponseJSON responseJSON = new ResponseJSON();

        if (customMessage == null || customMessage.trim().isEmpty()) {
            if (errorList.size() > 1) {
                responseJSON.setMessage("A few fields are missing or invalid.");
            } else {
                responseJSON.setMessage(!errorList.isEmpty() ? (errorList.get(0).getMessage() != null ? errorList.get(0).getMessage() : "") : "");
            }
        } else {
            responseJSON.setMessage(customMessage);
        }

        responseJSON.setResponseCode(Enums.AjaxResponse.ERROR.toString());
        responseJSON.setErrors(errorList);
        FormResult formResult = new FormResult();
        formResult.setResponseJSON(responseJSON);
        return formResult;
    }

    private static List<Error> getErrorsListFromModelState(Object modelState) {
        List<Error> errorList = new ArrayList<>();
        // Assuming modelState is a Map<String, Object> where each value has a property 'Errors'
        // This part needs to be implemented based on the actual structure of modelState
        // Example:
        // for (Map.Entry<String, Object> entry : modelState.entrySet()) {
        //     for (Error error : entry.getValue().getErrors()) {
        //         errorList.add(new Error(entry.getKey(), error.getErrorMessage()));
        //     }
        // }
        return errorList;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Error {
    private String field;
    private String message;
}

@Data
class ResponseJSON {
    private boolean isSuccess;
    private String message;
    private String responseCode;
    private Object responseData;
    private List<Error> errors;
}
