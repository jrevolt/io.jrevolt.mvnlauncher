package io.jrevolt.mvnlauncher;

import org.springframework.boot.loader.MvnLauncher;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class Main {

	static public void main(String[] args) {
		String artifact = (args.length > 0) ? args[0] : null;
		if (artifact == null || !artifact.matches("[^:]+(:[^:]+){2,5}")) {
			throw new RuntimeException("Undefined or invalid main artifact. First parameter must specify Maven artifact URI ({groupId}:{artifactId}:{version})");
		}
		System.setProperty("MvnLauncher.artifact", artifact);
		String[] newargs = new String[args.length - 1];
		System.arraycopy(args, 1, newargs, 0, newargs.length);
		MvnLauncher.main(newargs);
	}

}
