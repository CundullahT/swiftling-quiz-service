package com.swiftling.controller;

import com.swiftling.dto.QuizResultDTO;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:8762")
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
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> save(@Valid @RequestBody QuizResultDTO quizResultDTO) {

        QuizResultDTO savedQuizResult = quizService.save(quizResultDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.CREATED)
                .success(true)
                .message("The quiz result has been saved successfully.")
                .data(savedQuizResult)
                .build());

    }

//    @GetMapping("/phrases")
//    @Operation(summary = "Get all the phrases created by the currently logged in user, with/without status and language filters.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The phrases have been retrieved successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_GET_ALL_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getPhrases(@RequestParam(value = "status", required = false) String status,
//                                                      @RequestParam(value = "lang", required = false) String language) {
//
//        List<PhraseDTO> phrases = phraseService.getPhrases(status, language);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The phrases have been retrieved successfully.")
//                .data(phrases)
//                .build());
//
//    }
//
//    @GetMapping("/last-ten-phrases")
//    @Operation(summary = "Get the last 10 phrases created by the currently logged in user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The last 10 phrases have been retrieved successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_GET_LAST_TEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getLastTenPhrases() {
//
//        List<PhraseDTO> phrases = phraseService.getLastTenPhrases();
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The last 10 phrases have been retrieved successfully.")
//                .data(phrases)
//                .build());
//
//    }
//
//    @GetMapping("/phrase-details")
//    @Operation(summary = "Get the details of a phrase by using the External Phrase ID.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The phrase has been retrieved successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_GET_SINGLE_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "404", description = "The user account does not exist: + sample@email.com",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getPhrases(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) {
//
//        PhraseDTO phrase = phraseService.getPhraseDetails(externalPhraseId);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The phrase has been retrieved successfully.")
//                .data(phrase)
//                .build());
//
//    }
//
//    @GetMapping("/languages")
//    @Operation(summary = "Get all the language options.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The languages have been retrieved successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.LANGUAGE_GET_ALL_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getLanguages() {
//
//        List<String> languages = phraseService.getLanguages();
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The languages have been retrieved successfully.")
//                .data(languages)
//                .build());
//
//    }
//
//    @GetMapping("/quiz-languages")
//    @Operation(summary = "Get all the quiz language options for the logged in user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The quiz languages have been retrieved successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.QUIZ_LANGUAGE_GET_ALL_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getQuizLanguages() {
//
//        List<String> languages = phraseService.getQuizLanguages();
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The quiz languages have been retrieved successfully.")
//                .data(languages)
//                .build());
//
//    }
//
//    @GetMapping("/tags")
//    @Operation(summary = "Get all the tag options for the logged in user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The tags have been retrieved successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.TAG_GET_ALL_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getTags() {
//
//        List<String> tags = phraseService.getTags();
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The tags have been retrieved successfully.")
//                .data(tags)
//                .build());
//
//    }
//
//    @PutMapping("/update-phrase")
//    @Operation(summary = "Update an existing phrase created by the logged in user.",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhraseDTO.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_UPDATE_REQUEST_EXAMPLE))))
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The phrase has been updated successfully.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_UPDATE_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "409", description = "The given phrase already exists: demo phrase",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> update(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId, @Valid @RequestBody PhraseDTO phraseDTO) {
//
//        PhraseDTO updatedPhrase = phraseService.update(externalPhraseId, phraseDTO);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .message("The phrase has been updated successfully.")
//                .data(updatedPhrase)
//                .build());
//
//    }
//
//    @DeleteMapping("/delete-phrase")
//    @Operation(summary = "Delete an existing phrase belongs to the logged in user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "The phrase has been deleted successfully."),
//            @ApiResponse(responseCode = "404", description = "The phrase does not exist: 550e8400-e29b-41d4-a716-446655440000",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "409", description = "The phrase can not be deleted: + 550e8400-e29b-41d4-a716-446655440000",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_DELETED_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> delete(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) {
//
//        phraseService.delete(externalPhraseId);
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseWrapper.builder()
//                .statusCode(HttpStatus.NO_CONTENT)
//                .build());
//
//    }
//
//    @PostMapping("pronunciation/original")
//    @Operation(summary = "Get a pronunciation of the original phrase created by the logged in user.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "The pronunciation of the original phrase has been retrieved successfully."),
//            @ApiResponse(responseCode = "404", description = "The phrase does not exist: 550e8400-e29b-41d4-a716-446655440000",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "404", description = "/some/file/location.txt (No such file or directory)",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.FILE_NOT_FOUND_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "409", description = "/some/file/location.txt (No such file or directory)",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.IOEXCEPTION_RESPONSE_EXAMPLE))),
//            @ApiResponse(responseCode = "403", description = "Access is denied",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
//                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
//    public ResponseEntity<ResponseWrapper> getOriginalPronunciation(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) throws Exception {
//
//        String audioFileName = "pronunciation.mp3";
//
//        phraseService.originalPhraseSynthesizeSpeech(externalPhraseId, audioFileName);
//
//        byte[] audioBytes = Files.readAllBytes(Paths.get(audioFileName));
//
//        return ResponseEntity.ok(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .data(audioBytes)
//                .build());
//
//    }
//
//    @PostMapping("pronunciation/meaning")
//    public ResponseEntity<ResponseWrapper> getMeaningPronunciation(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) throws Exception {
//
//        String audioFileName = "pronunciation.mp3";
//
//        phraseService.meaningPhraseSynthesizeSpeech(externalPhraseId, audioFileName);
//
//        byte[] audioBytes = Files.readAllBytes(Paths.get(audioFileName));
//
//        return ResponseEntity.ok(ResponseWrapper.builder()
//                .statusCode(HttpStatus.OK)
//                .success(true)
//                .data(audioBytes)
//                .build());
//
//    }

}
