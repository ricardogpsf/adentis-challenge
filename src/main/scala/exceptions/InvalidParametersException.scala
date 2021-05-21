package exceptions

class InvalidParametersException(message: String) extends Exception("Invalid parameters - " + message)
