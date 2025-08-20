class InvalidCommandException extends RuntimeException {
    public InvalidCommandException() {}
    public InvalidCommandException(String message) {
        super(message);
    }
}