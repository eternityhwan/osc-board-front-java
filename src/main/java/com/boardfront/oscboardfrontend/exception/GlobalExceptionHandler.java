package com.boardfront.oscboardfrontend.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class) // 유효성 검사 실패 시 발생하는 예외를 처리
    @ResponseBody
    protected Object handleException(ConstraintViolationException exception) {

//        return Response
//                .builder()
//                .header(Header
//                        .builder()
//                        .isSuccessful(false)
//                        .resultCode(-400)
//                        .resultMessage(getResultMessage(exception.getConstraintViolations().iterator())) // 오류 응답을 생성
//                        .build())
//                .build();

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST).badRequest()
                .body(getResultMessage(exception.getConstraintViolations().iterator())); // 오류 응답을 생성
    }

    protected String getResultMessage(final Iterator<ConstraintViolation<?>> violationIterator) {
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (violationIterator.hasNext() == true) {
            final ConstraintViolation<?> constraintViolation = violationIterator.next();
            resultMessageBuilder
                    .append("['")
                    .append(getPopertyName(constraintViolation.getPropertyPath().toString())) // 유효성 검사가 실패한 속성
                    .append("' is '")
                    .append(constraintViolation.getInvalidValue()) // 유효하지 않은 값
                    .append("'. ")
                    .append(constraintViolation.getMessage()) // 유효성 검사 실패 시 메시지
                    .append("]");

            if (violationIterator.hasNext() == true) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }

    protected String getPopertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1); // 전체 속성 경로에서 속성 이름만 가져온다.
    }
}
