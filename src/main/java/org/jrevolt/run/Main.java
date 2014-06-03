package org.jrevolt.run;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class Main {

	static public void main(String[] args) {
		// this should never be called, actually. MvnLauncher actually delegates main() call to the main class defined
		// in Maven artifact referenced in first argument. See premain()
		throw new AssertionError("Build/Assembly error? This should never be called...");
	}

	// SpringBoot MvnLauncher callback
	static public String[] premain(String[] args) {
		String artifact = (args.length > 0) ? args[0] : null;
		if (artifact == null || !artifact.matches("[^:]+:[^:]+:[^:]+")) {
			throw new RuntimeException("Undefined or invalid main artifact. First parameter must specify Maven artifact URI ({groupId}:{artifactId}:{version})");
		}

		System.setProperty("MvnLauncher.artifact", artifact);
		String[] newargs = new String[args.length - 1];
		System.arraycopy(args, 1, newargs, 0, newargs.length);
		return newargs;
	}
}
