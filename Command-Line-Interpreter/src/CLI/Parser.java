package CLI;

public class Parser {
	private String commandName;
	private String[] args;

	private boolean inQuotes, redirectCmdExists;
	private int redirectCmdPosition;
	private String[] allCmds = { "pwd", "echo", "cd", "ls", "mkdir", "rmdir", "cp", "rm", "cat", "exit" };

	// This method will divide the input into commandName and args
	// where "input" is the string command entered by the user
	public boolean parse(String input) {
		inQuotes = false;
		redirectCmdExists = false;

		// count the number of arguments
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '"' && inQuotes)
				inQuotes = false;
			else if (input.charAt(i) == '"' && !inQuotes)
				inQuotes = true;
			else if (input.charAt(i) == ' ' && !inQuotes)
				count++;

		}
		// create args array with the size of count
		args = new String[count];

		int startIndex = 0; // used for substring() method.
		boolean isFirstWord = true;
		count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '"' && inQuotes)
				inQuotes = false;
			else if (input.charAt(i) == '"' && !inQuotes)
				inQuotes = true;
			else if (input.charAt(i) == '>') {
				redirectCmdExists = true;
				redirectCmdPosition = count;
			} 
			if ((input.charAt(i) == ' ' && !inQuotes) || i == input.length() - 1) {
				if (isFirstWord) {
					if (i == input.length() - 1)
						commandName = input.substring(startIndex, i + 1);
					else
						commandName = input.substring(startIndex, i);
					startIndex = i + 1;
					isFirstWord = false;
				} else {
					if (i == input.length() - 1 && input.charAt(i) == '"')
						args[count++] = input.substring(startIndex + 1, i);
				    else if (i == input.length() - 1) // case of last argument
						args[count++] = input.substring(startIndex, i + 1);
					else if (input.charAt(i - 1) == '"') // case of argument in double quotes
						args[count++] = input.substring(startIndex + 1, i - 1);
					else // case of normal argument without quotes
						args[count++] = input.substring(startIndex, i);
					startIndex = i + 1; // the start index for the next argument is after the current space
				}
			}
		}

		// Check if command is valid or not
		for (String cmd : allCmds) {
			if (cmd.equals(commandName)) {
				switch (cmd) {
				case "exit":
					if (args.length == 0)
						return true;
					else {
						System.out.println("ERROR: " + cmd + " command requires no arguments!");
						return false;
					}
				case "pwd":
					if (args.length == 0)
						return true;
					else if (redirectCmdExists) {
						if (args.length == 2 && redirectCmdPosition == 0)
							return true;
						else {
							System.out.println("ERROR: " + cmd + " and redirect commands require exactly 0,1 argument respectively!");
							return false;
						}
					} else {
						System.out.println("ERROR: " + cmd + " command requires no arguments!");
						return false;
					}
				case "cd":
					if (args.length == 0 || args.length == 1)
						return true;
					else {
						System.out.println("ERROR: " + cmd + " command requires either 0 or 1 argument!");
						return false;
					}
				case "ls":
					if (args.length == 0 || (args.length == 1 && args[0].equals("-r")))
						return true;
					else if (redirectCmdExists) {
						if ((args.length == 2 && redirectCmdPosition == 0)
								|| (args.length == 3 && redirectCmdPosition == 1 && args[0].equals("-r")))
							return true;
						else {
							System.out.println("ERROR: " + cmd + " and redirect commands require exactly 0,1 argument respectively!");
							return false;
						}
					} else {
						System.out.println("ERROR: " + cmd + " command requires no arguments!");
						return false;
					}
				case "rm":
				case "rmdir":
					if (args.length == 1)
						return true;
					else {
						System.out.println("ERROR: " + cmd + " command requires exactly 1 argument!");
						return false;
					}
				case "cat":
					if (args.length == 1 || args.length == 2)
						return true;
					else if (redirectCmdExists) {
						if ((args.length == 3 && redirectCmdPosition == 1) || (args.length == 4 && redirectCmdPosition == 2 ))
							return true;
						else {
							System.out.println("ERROR: " + cmd + " and redirect commands require 1 or 2,1 argument respectively!");
							return false;
						}
					} else {
						System.out.println("ERROR: " + cmd + " command requires 1 or 2 arguments!");
						return false;
					}
				case "echo":
					if (args.length == 1)
						return true;
					else if (redirectCmdExists) {
						if (args.length == 3 && redirectCmdPosition == 1)
							return true;
						else {
							System.out
									.println("ERROR: " + cmd + " and redirect commands require exactly one argument!");
							return false;
						}
					} else {
						System.out.println("ERROR: " + cmd + " command requires exactly one argument!");
						return false;
					}
				case "cp":
					if (args.length == 2)
						return true;
					else {
						System.out.println("ERROR: " + cmd + " command requires exactly 2 arguments!");
						return false;
					}
				case "mkdir":
					if (args.length >= 1)
						return true;
					else {
						System.out.println("ERROR: " + cmd + " command requires 1 or more arguments!");
						return false;
					}
				}
			}
		}
		System.out.println("ERROR: UNKNOWN COMMAND!");
		return false;
	}

	public String getCommandName() {
		return commandName;
	}

	public String[] getArgs() {
		return args;
	}

	public int getRedirectCmdPosition() {
		if (redirectCmdExists)
			return redirectCmdPosition;
		return -1;
	}

	public boolean redirectCmdExists() {
		return redirectCmdExists;
	}
}
