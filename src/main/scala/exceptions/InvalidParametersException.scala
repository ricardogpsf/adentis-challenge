package exceptions

class InvalidParametersException(message: String) extends Exception("InvalidParameters - " + message)
