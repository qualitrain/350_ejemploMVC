package mx.com.qtx.web;

public class NegocioException extends RuntimeException {

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(String message) {
		super(message);
	}

}
