package mx.com.qtx.servNegocio;

public class PersistenciaException extends Exception {

	public PersistenciaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenciaException(String message) {
		super(message);
	}

}
