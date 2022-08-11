# Command-Line-Interpreter

#Team:

Hossam Mohamed Hamdy => hossameljendy@gmail.com  github => https://github.com/hossamelgendy1
Mazen Ahmed Mahmoud => mazenelsheriif@gmail.com  github => https://github.com/Mazenelshereef
Zeyad Ahmed Abdelhamid => azeyad682@gmail.com    github => https://github.com/Ze-Ro0o

#All Features:

CLI allow the user to enter the input through the keyboard. After the 
User writes the command and presses enter, the string is parsed, and the indicated command executed. 
The CLI will keep accepting different commands from the user until the user writes “exit”, then the CLI terminates. 


#The program list of commands are:


1- "echo": Takes 1 argument and prints it.

2- "pwd": Takes no arguments and prints the current path

3- "cd": there are three different versions which are:
       1. cd takes no arguments and changes the current path to the path of your home directory.
       2. cd takes 1 argument which is “..” (e.g. cd ..) and changes the current directory to the previous directory.
       3. cd takes 1 argument which is either the full path or the relative (short) path and changes the current path to that path.
4- "ls": Takes no arguments and lists the contents of the current directory sorted alphabetically.

5- "ls -r": Takes no arguments and lists the contents of the current directory in reverse order.

6- "mkdir": Takes 1 or more arguments and creates a directory for each argument. Each argument can be:
            • Directory name (in this case the new directory is created in  the current directory)
            • Path (full/short) that ends with a directory name (in this case the new directory is created in the given path)
            
7- "rmdir": there are two different versions which are:
        1. rmdir takes 1 argument which is “*” (e.g. rmdir *) and removes all the empty directories in the current directory.
        2. rmdir takes 1 argument which is either the full path or the relative (short) path and removes the given directory only if it is empty.
        
8- "touch": Takes 1 argument which is either the full path or the relative (short) path that ends with a file name and creates this file.

9- "cp": Takes 2 arguments, both are files and copies the first onto the second.

10- "cp -r": Takes 2 arguments, both are directories (empty or not) and copies the first directory (with all its content) into the second one.

11- "rm": Takes 1 argument which is a file name that exists in the current directory and removes this file.

12- "cat": Takes 1 argument and prints the file’s content or takes 2 arguments and concatenates the content of the 2 files and prints it.

13- ">": Format: command > FileNameRedirects the output of the first command to be written to a file. If the file doesn’t exist, it will be created.
If the file exists, its original content will be replaced.
Example:
    echo Hello World > myfile.txt
    ls > file




