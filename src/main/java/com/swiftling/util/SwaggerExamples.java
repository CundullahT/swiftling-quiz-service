package com.swiftling.util;

public class SwaggerExamples {

    private SwaggerExamples() {}

    public static final String QUIZ_RESULT_SAVE_REQUEST_EXAMPLE = "{\n" +
            "  \"quizType\": \"In Progress Only\",\n" +
            "  \"language\": \"en\",\n" +
            "  \"correctAnswerAmount\": 15,\n" +
            "  \"wrongAnswerAmount\": 3,\n" +
            "  \"timedOutAnswerAmount\": 2,\n" +
            "  \"bestTimeInSeconds\": 4,\n" +
            "  \"worstTimeInSeconds\": 12,\n" +
            "  \"resultForEachPhrase\": {\n" +
            "    \"f47ac10b-58cc-4372-a567-0e02b2c3d479\": {\n" +
            "      \"consecutiveCorrectAmount\": 5,\n" +
            "      \"answeredWrongOrTimedOutAtLeastOnce\": false\n" +
            "    },\n" +
            "    \"e0eec5e4-11a3-4d0e-988e-d8fc46c4d9b9\": {\n" +
            "      \"consecutiveCorrectAmount\": 2,\n" +
            "      \"answeredWrongOrTimedOutAtLeastOnce\": true\n" +
            "    },\n" +
            "    \"9c6f4d1e-3df0-4b0a-96f7-1a60e9876de3\": {\n" +
            "      \"consecutiveCorrectAmount\": 10,\n" +
            "      \"answeredWrongOrTimedOutAtLeastOnce\": false\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String QUIZ_RESULT_SAVE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"CREATED\",\n" +
            "  \"message\": \"The quiz result has been saved successfully.\",\n" +
            "  \"data\": {\n" +
            "    \"externalQuizResultId\": \"550e8400-e29b-41d4-a716-446655440099\",\n" +
            "    \"date\": \"2025-06-05\",\n" +
            "    \"quizType\": \"In Progress Only\",\n" +
            "    \"language\": \"English\",\n" +
            "    \"correctAnswerAmount\": 15,\n" +
            "    \"wrongAnswerAmount\": 3,\n" +
            "    \"timedOutAnswerAmount\": 2,\n" +
            "    \"bestTimeInSeconds\": 4,\n" +
            "    \"worstTimeInSeconds\": 12\n" +
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

    public static final String DAILY_STREAK_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The daily streak has been retrieved successfully.\",\n" +
            "  \"data\": {\n" +
            "    \"dailyStreak\": 14,\n" +
            "    \"updatedToday\": true\n" +
            "  }\n" +
            "}";

    public static final String USER_NOT_FOUND_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The user does not exist: 550e8400-e29b-41d4-a716-446655440000\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String QUIZZES_NOT_DELETED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The quizzes can not be deleted.\",\n" +
            "  \"httpStatus\": \"CONFLICT\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
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
