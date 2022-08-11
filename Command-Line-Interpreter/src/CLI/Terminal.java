package CLI;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Terminal {

	/*
	 * global variables
	 */
	private static String pwd = "C:\\"; // default
	private static File currentFile = null;
	static Parser parser;
	/*
	 * end of global variables
	 */

	/*
	 * helper functions
	 */
	public static void reverseArray(String[] arr) {
		// Converting Array to List
		List<String> list = Arrays.asList(arr);
		// Reversing the list using Collections.reverse() method
		Collections.reverse(list);
	}

	public static File transformToAbsolute(String pathOfFile) {
		File file = new File(pathOfFile);
		if (!file.isAbsolute()) {
			file = new File(currentFile.getAbsolutePath(), pathOfFile);
		}
		return file.getAbsoluteFile();
	}

	/*
	 * end of helper functions
	 */

	/*
	 *  commands implementations
	 */
	public static String echo(String arg) {
		return arg;
	}

	public static String pwd() {
		return pwd;
	}

	public static void cd() {
		pwd = "C:\\";
	}

	public static void cd(String path) throws IOException {
		if (path.equals("..")) {
			for (int i = pwd.length() - 2; i >= 0; i--) {
				if (pwd.charAt(i) == '\\') {
					pwd = pwd.substring(0, i + 1);
					currentFile = new File(pwd);
					break;
				}

			}
		} else {
			currentFile = new File(path);

			// Checking if path is relative or absolute
			if (!(currentFile.isAbsolute()))
				currentFile = new File(pwd + "\\" + path);

			// Checking if it exists
			if (currentFile.exists()) {

				// Checking if it's a directory and not a file
				if (currentFile.isDirectory()) {

					// canonical resolves .. in paths by default
					pwd = currentFile.getCanonicalPath();

				} else {
					System.out.println("ERROR: only directories can be accessed!");
				}

			} else {
				System.out.println("ERROR: no such file or directory!");
			}
		}
	}

	// print dir content "Alphabitcally"
	public static String ls() {
		currentFile = new File(pwd);
		String[] childs = currentFile.list();
		String output = "";
		for (String child : childs) {
			output = output.concat(child + '\n');
		}
		return output;
	}

	// print dir content "reverse Alphabitcally"
	public static String lsR() {
		currentFile = new File(pwd);
		String[] childs = currentFile.list();
		reverseArray(childs);
		String output = "";
		for (String child : childs) {
			output = output.concat(child + '\n');
		}
		return output;
	}

	// copy file to another
	public static void cp(String a, String b) throws Exception {
		File filein = transformToAbsolute(a);
		File fileout = transformToAbsolute(b);
		FileInputStream in = new FileInputStream(filein);
		FileOutputStream out = new FileOutputStream(fileout);

		try {
			int n;

			// read() function to read the
			// byte of data
			while ((n = in.read()) != -1) {
				// write() function to write
				// the byte of data
				out.write(n);
			}
		} finally {
			if (in != null) {

				// close() function to close the
				// stream
				in.close();
			}
			// close() function to close
			// the stream
			if (out != null) {
				out.close();
			}
		}
		System.out.println("File Copied");
	}

	// Redirects the output of the first command to be written to file(replace the
	// file content with it )
	public static void redirect(String fileName, String output) throws IOException {
		File myObj;
		try {	
			myObj = transformToAbsolute(fileName);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
			FileWriter writer = new FileWriter(myObj.getAbsolutePath());
			writer.write(output);
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// Redirects the output of the first command to be written to file(Append text
	// to file).
	public static void redirectAppend(String fileName, String str) {
		File myObj;
		// Try block to check for exceptions
		try {
			myObj = transformToAbsolute(fileName);
			// Open given file in append mode by creating an
			// object of BufferedWriter class
			BufferedWriter out = new BufferedWriter(new FileWriter(myObj.getAbsolutePath(), true));

			// Writing on output stream
			out.write(str);
			// Closing the connection
			out.close();
		}

		// Catch block to handle the exceptions
		catch (IOException e) {

			// Display message when exception occurs
			System.out.println("exception occoured" + e);
		}
	}

	public static void rm(String fileName) {
		File file = transformToAbsolute(fileName);
		if (!file.exists())
			System.out.println("This File is not found!");
		else if (file.isDirectory())
			System.out.println(" You Can't delete a directory using this command!");
		else if (file.delete())
			System.out.println("The File is deleted!");
		else
			System.out.println("This File can not be deleted!");

	}

	public static String cat(String pathOfFile) throws FileNotFoundException {
		String contentInFile = "";
		File file = transformToAbsolute(pathOfFile);
		if (!file.exists())
			System.out.println("This File is not found!");
		if (file.isFile()) {
			Scanner fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				contentInFile = contentInFile.concat(fileScanner.nextLine() + '\n');
			}
		}
		return contentInFile;
	}

	public static String cat(String pathOfFile1, String pathOfFile2) throws IOException {
		String fileContent1 = "";
		String fileContent2 = "";

		File file = transformToAbsolute(pathOfFile1);
		File file2 = transformToAbsolute(pathOfFile2);
		if (!file.exists())
			System.out.println("ERROR: file '" + file.getCanonicalPath() + "' was not found!");
		if (!file2.exists())
			System.out.println("ERROR: file '" + file2.getCanonicalPath() + "' was not found!");
		if (file.isFile() && file2.isFile()) {
			Scanner fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				fileContent1 = fileContent1.concat(fileScanner.nextLine() + '\n');
			}
			fileScanner = new Scanner(file2);
			while (fileScanner.hasNextLine()) {
				fileContent2 = fileContent2.concat(fileScanner.nextLine() + '\n');
			}
		}
		return fileContent1.concat(fileContent2);
	}
	
	public static void mkdir(String [] paths)throws IOException {
        File[] files = new File[paths.length] ;
        for (int i =0; i< paths.length; i++){
        	files[i] = transformToAbsolute(paths[i]);
            if(files[i].exists())
                System.out.println("The Directory: "+ files[i] +" already exists.");
            boolean isCreated =  files[i].mkdirs();
            if(!isCreated)
                System.out.println("Directory can't be created!");
            else
                System.out.println("Directory is Created Successfully!");
        }

    }
	
	public static void rmdir(String fileName) {
		currentFile = new File(pwd);
        if (fileName.equals("*")) {
            File[] existedFiles = currentFile.listFiles();

            for (File _file : existedFiles) {

                if (_file.isDirectory() && _file.delete()) {
                    System.out.println("The Directory "+ _file +" has been deleted Successfully");
                }
            }
        } else {
            File file = transformToAbsolute(fileName);
            if (!file.exists())
                System.out.println("This directory is not found!");
            else if (file.isFile())
            	 System.out.println("Sorry this command does not delete files!");
            else if (file.delete())
                System.out.println("Directory \"" +fileName+ "\" has been Deleted Successfully!");
            else
            	 System.out.println("Can't delete a non-empty directory!");
        }

    }
	/*
	 * end of commands implementations
	 */

	// This method will choose the suitable command method to be called
	public static void chooseCommandAction() throws Exception {
		switch (parser.getCommandName()) {
		case "echo":
			if (parser.redirectCmdExists()) {
				if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">"))
					redirect(parser.getArgs()[2], echo(parser.getArgs()[0]));
				else if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">>"))
					redirectAppend(parser.getArgs()[2], echo(parser.getArgs()[0]));
			} else {
				System.out.println(echo(parser.getArgs()[0]));
			}
			break;
		case "pwd":
			if (parser.redirectCmdExists()) {
				if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">"))
					redirect(parser.getArgs()[1], pwd());
				else if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">>"))
					redirectAppend(parser.getArgs()[1], pwd());
			} else {
				System.out.println(pwd());
			}
			break;
		case "cd":
			if (parser.getArgs().length == 0)
				cd();
			else if (parser.getArgs().length == 1)
				cd(parser.getArgs()[0]);
			break;
		case "ls":
			if (parser.redirectCmdExists() && parser.getArgs().length == 2) {
				if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">"))
					redirect(parser.getArgs()[1], ls());
				else if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">>"))
					redirectAppend(parser.getArgs()[1], ls());
			} else if (parser.redirectCmdExists() && parser.getArgs().length == 3) {
				if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">"))
					redirect(parser.getArgs()[2], lsR());
				else if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">>"))
					redirectAppend(parser.getArgs()[2], lsR());
			}
			else {
				if (parser.getArgs().length == 0)
					System.out.println(ls());
				else if (parser.getArgs().length == 1)
					System.out.println(lsR());
			}
			break;
		case "mkdir":
			mkdir(parser.getArgs());
			break;
		case "rmdir":
			rmdir(parser.getArgs()[0]);
			break;
		case "cp":
			cp(parser.getArgs()[0], parser.getArgs()[1]);
			break;
		case "rm":
			rm(parser.getArgs()[0]);
			break;
		case "cat":
			if (parser.redirectCmdExists() && parser.getArgs().length == 3) {
				if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">"))
					redirect(parser.getArgs()[2], cat(parser.getArgs()[0]));
				else if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">>"))
					redirectAppend(parser.getArgs()[2], cat(parser.getArgs()[0]));
			} else if (parser.redirectCmdExists() && parser.getArgs().length == 4) {
				if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">"))
					redirect(parser.getArgs()[3], cat(parser.getArgs()[0], parser.getArgs()[1]));
				else if (parser.getArgs()[parser.getRedirectCmdPosition()].equals(">>"))
					redirectAppend(parser.getArgs()[3], cat(parser.getArgs()[0], parser.getArgs()[1]));
			}
			else {
				if (parser.getArgs().length == 1)
					System.out.println(cat(parser.getArgs()[0]));
				else if (parser.getArgs().length == 2)
					System.out.println(cat(parser.getArgs()[0], parser.getArgs()[1]));
			}
			break;
		case "exit":
			System.exit(0);
		default:
			break;
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		parser = new Parser();
		while (true) {
			try {
				System.out.print(pwd + '>');
				if (parser.parse(input.nextLine()))
					chooseCommandAction();
			} catch (Exception e) {
				System.out.println("unexpected error occured!");
			}
			
		}
	}

}
