class InvalidTaskNumberException extends RuntimeException {
    public InvalidTaskNumberException() {}
    public InvalidTaskNumberException(String message) {
        super(message);
    }
}