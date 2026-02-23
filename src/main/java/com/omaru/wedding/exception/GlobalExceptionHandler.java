package com.omaru.wedding.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
//404/400/500 が全部 ProblemDetail になる。
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InviteNotFoundException.class)
    public ProblemDetail handleInviteNotFound(InviteNotFoundException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setType(URI.create("https://example.com/problems/invite-not-found"));
        pd.setTitle("Invite not found");
        pd.setDetail("招待状が見つかりません。URLをご確認ください。");
        pd.setInstance(URI.create(req.getRequestURI()));

        pd.setProperty("errorCode", "INVITE_NOT_FOUND");
        pd.setProperty("traceId", traceIdOrNull());

        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("https://example.com/problems/validation-error"));
        pd.setTitle("Validation error");
        pd.setDetail("入力内容に誤りがあります。");
        pd.setInstance(URI.create(req.getRequestURI()));

        pd.setProperty("errorCode", "VALIDATION_ERROR");
        pd.setProperty("traceId", traceIdOrNull());

        List<ValidationErrorDto> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(GlobalExceptionHandler::toValidationError)
                .toList();

        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleBodyInvalid(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("https://example.com/problems/request-body-invalid"));
        pd.setTitle("Request body invalid");
        pd.setDetail("リクエストボディの形式が正しくありません。");
        pd.setInstance(URI.create(req.getRequestURI()));

        pd.setProperty("errorCode", "REQUEST_BODY_INVALID");
        pd.setProperty("traceId", traceIdOrNull());

        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex, HttpServletRequest req) {
        // 本番前提：詳細はレスポンスに出さず、ログで追う
        log.error("Unhandled exception: method={}, uri={}", req.getMethod(), req.getRequestURI(), ex);

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setType(URI.create("https://example.com/problems/internal-error"));
        pd.setTitle("Internal server error");
        pd.setDetail("サーバ内部でエラーが発生しました。時間をおいて再度お試しください。");
        pd.setInstance(URI.create(req.getRequestURI()));

        pd.setProperty("errorCode", "INTERNAL_ERROR");
        pd.setProperty("traceId", traceIdOrNull());

        return pd;
    }

    private static ValidationErrorDto toValidationError(FieldError fe) {
        return new ValidationErrorDto(fe.getField(), fe.getDefaultMessage());
    }

    private static String traceIdOrNull() {
        return org.slf4j.MDC.get("traceId");
    }

    public record ValidationErrorDto(String field, String message) {}
}