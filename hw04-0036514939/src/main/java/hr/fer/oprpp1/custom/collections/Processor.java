package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje koje modelira objekt koji može obaviti neku operacjiu nad danim objektom
 * 
 * @author Cubi
 *
 */
public interface Processor<T> {
	void process(T value);
}