package com.swiftling.controller;

import com.swiftling.dto.DailyStreakDTO;
import com.swiftling.dto.QuizHistoryDTO;
import com.swiftling.dto.QuizResultDTO;
import com.swiftling.dto.QuizStatsDTO;
import com.swiftling.dto.wrapper.ExceptionWrapper;
import com.swiftling.dto.wrapper.ResponseWrapper;
import com.swiftling.service.QuizService;
import com.swiftling.util.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
//@CrossOrigin({"http://localhost:8762", "http://cundi.onthewifi.com:8762", "http://localhost:5000", "http://cundi.onthewifi.com:5000", "https://swiftlingapp.com", "https://www.swiftlingapp.com"})
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/result")
    @Operation(summary = "Save the result of a(n) completed/ended quiz.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResultDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.QUIZ_RESULT_SAVE_REQUEST_EXAMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The quiz result has been saved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.QUIZ_RESULT_SAVE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The statuses of the phrases in the quiz result could not be updated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_STATUSES_NOT_UPDATED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> save(@Valid @RequestBody QuizResultDTO quizResultDTO) {

        QuizResultDTO savedQuizResult = quizService.save(quizResultDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.CREATED)
                .success(true)
                .message("The quiz result has been saved successfully.")
                .data(savedQuizResult)
                .build());

    }

    @GetMapping("/history")
    @Operation(summary = "Get the quiz history for the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The quiz history has been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.QUIZ_HISTORY_LIST_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getQuizHistory() {

        List<QuizHistoryDTO> quizHistory = quizService.getQuizHistory();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The quiz history has been retrieved successfully.")
                .data(quizHistory)
                .build());

    }

    @GetMapping("/stats")
    @Operation(summary = "Get the stats of the quizzes solved by the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The stats have been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.QUIZ_STATS_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getQuizStats() {

        QuizStatsDTO quizStats = quizService.getQuizStats();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The stats have been retrieved successfully.")
                .data(quizStats)
                .build());

    }

    @GetMapping("/daily-streak")
    @Operation(summary = "Get the daily streak of the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The daily streak has been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.DAILY_STREAK_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getDailyStreak() {

        DailyStreakDTO dailyStreakDTO = quizService.getDailyStreak();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The daily streak has been retrieved successfully.")
                .data(dailyStreakDTO)
                .build());

    }

    @DeleteMapping("/delete-all-user-quizzes")
    @Operation(summary = "Delete all existing quizzes belongs to an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The quizzes have been deleted successfully."),
            @ApiResponse(responseCode = "404", description = "The user does not exist: 550e8400-e29b-41d4-a716-446655440000",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "The quizzes can not be deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.QUIZZES_NOT_DELETED_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<Void> deleteAllByUser(@RequestParam(value = "external-user-id", required = true) UUID externalOwnerUserAccountId) {

        quizService.deleteAllByUser(externalOwnerUserAccountId);

        return ResponseEntity.noContent().build();

    }

}
