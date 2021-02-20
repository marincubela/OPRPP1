package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Sučelje koje definira način na koji treba implementirati naredbe.
 * 
 * @author Cubi
 *
 */
public interface ShellCommand {
	/**
	 * Metoda koja izvršava ovu naredbu.
	 * 
	 * @param env trenutno okruženje za koje treba izvesti ovu naredbu.
	 * @param arguments naredbeni argumenti koje je korisnik unio za ovu naredbu.
	 * @return status shell-a nakon naredbe.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Metoda koja vraća naziv ove naredbe.
	 * 
	 * @return ime ove naredbe.
	 */
	String getCommandName();

	/**
	 * Metoda koja vraća opis naredbe s instrukcijama za korištenje.
	 * 
	 * @return opis naredbe s instrukcijama za korištenje.
	 */
	List<String> getCommandDescription();

}
