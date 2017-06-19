package br.com.banana.exception;

/**
 * Exceção para reservas já existentes
 * 
 * @author vagner
 *
 */
public class ReservaJaExisteException extends RuntimeException {

	private static final long serialVersionUID = 1097579080784954670L;

	public ReservaJaExisteException() {
	}

	public ReservaJaExisteException(String message) {
		super(message);
	}

	public ReservaJaExisteException(Throwable cause) {
		super(cause);
	}

	public ReservaJaExisteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReservaJaExisteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
