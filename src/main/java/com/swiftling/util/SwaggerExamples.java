package com.swiftling.util;

public class SwaggerExamples {

    private SwaggerExamples() {}

    public static final String QUIZ_RESULT_SAVE_REQUEST_EXAMPLE = "{\n" +
            "  \"quizType\": \"LEARNED_ONLY\",\n" +
            "  \"language\": \"CZECH\",\n" +
            "  \"correctAnswerAmount\": 12,\n" +
            "  \"wrongAnswerAmount\": 3,\n" +
            "  \"timedOutAnswerAmount\": 1,\n" +
            "  \"bestTimeInSeconds\": 4,\n" +
            "  \"worstTimeInSeconds\": 15,\n" +
            "  \"learnedPhrases\": {\n" +
            "    \"550e8400-e29b-41d4-a716-446655440000\": 10,\n" +
            "    \"550e8400-e29b-41d4-a716-446655440001\": 11\n" +
            "  },\n" +
            "  \"inProgressPhrases\": {\n" +
            "    \"550e8400-e29b-41d4-a716-446655440002\": 5,\n" +
            "    \"550e8400-e29b-41d4-a716-446655440003\": 6\n" +
            "  }\n" +
            "}";

    public static final String QUIZ_RESULT_SAVE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"CREATED\",\n" +
            "  \"message\": \"The quiz result has been saved successfully.\",\n" +
            "  \"data\": {\n" +
            "    \"externalQuizResultId\": \"550e8400-e29b-41d4-a716-446655440099\",\n" +
            "    \"date\": \"2025-06-05\",\n" +
            "    \"quizType\": \"LEARNED_ONLY\",\n" +
            "    \"language\": \"FRENCH\",\n" +
            "    \"correctAnswerAmount\": 12,\n" +
            "    \"wrongAnswerAmount\": 3,\n" +
            "    \"timedOutAnswerAmount\": 1,\n" +
            "    \"bestTimeInSeconds\": 4,\n" +
            "    \"worstTimeInSeconds\": 15\n" +
            "  }\n" +
            "}";

    public static final String QUIZ_HISTORY_LIST_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The quiz history has been retrieved successfully.\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"externalQuizId\": \"550e8400-e29b-41d4-a716-446655440000\",\n" +
            "      \"date\": \"2025-06-05\",\n" +
            "      \"quizType\": \"LEARNED_ONLY\",\n" +
            "      \"language\": \"FRENCH\",\n" +
            "      \"correctAnswerAmount\": 12,\n" +
            "      \"wrongAnswerAmount\": 3,\n" +
            "      \"timedOutAnswerAmount\": 1,\n" +
            "      \"bestTimeInSeconds\": 4,\n" +
            "      \"worstTimeInSeconds\": 12\n" +
            "    },\n" +
            "    {\n" +
            "      \"externalQuizId\": \"550e8400-e29b-41d4-a716-446655440001\",\n" +
            "      \"date\": \"2025-06-01\",\n" +
            "      \"quizType\": \"ALL_MIXED\",\n" +
            "      \"language\": \"SPANISH\",\n" +
            "      \"correctAnswerAmount\": 15,\n" +
            "      \"wrongAnswerAmount\": 2,\n" +
            "      \"timedOutAnswerAmount\": 0,\n" +
            "      \"bestTimeInSeconds\": 3,\n" +
            "      \"worstTimeInSeconds\": 10\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String QUIZ_STATS_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The stats have been retrieved successfully.\",\n" +
            "  \"data\": {\n" +
            "    \"overallBestTimeInSeconds\": 3,\n" +
            "    \"latestBestTimeInSeconds\": 4,\n" +
            "    \"latestQuizResults\": {\n" +
            "      \"correctAnswerAmount\": 12,\n" +
            "      \"wrongAnswerAmount\": 3,\n" +
            "      \"timedOutAnswerAmount\": 1\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Access is denied\",\n" +
            "  \"httpStatus\": \"FORBIDDEN\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The external ID of the user account could not be retrieved.\",\n" +
            "  \"httpStatus\": \"SERVICE_UNAVAILABLE\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String PHRASE_STATUSES_NOT_UPDATED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The statuses of the phrases in the quiz result could not be updated.\",\n" +
            "  \"httpStatus\": \"SERVICE_UNAVAILABLE\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String VALIDATION_EXCEPTION_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Invalid Input(s)\",\n" +
            "  \"httpStatus\": \"BAD_REQUEST\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\",\n" +
            "  \"errorCount\": 1,\n" +
            "  \"validationExceptions\": [\n" +
            "    {\n" +
            "      \"errorField\": \"email\",\n" +
            "      \"rejectedValue\": \"john.doe.123456\",\n" +
            "      \"reason\": \"The email address must be in a valid email format.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
