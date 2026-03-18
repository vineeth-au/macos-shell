## Overview
A POSIX compliant shell (in the near future) capable of interpreting shell commands, running 
external programs and builtin commands like cd, pwd, echo and more.

## Java Version
This version has been only tested on Java 25.

## Getting Started
Run `./your_program.sh`

## Current Features ##

1. Prompt: The shell starts with a prompt ($) that signals it’s ready for your command. As of now, only a few commands are supported.
1. The `exit` command terminates the shell.
    1. `Control + C` terminates the shell.
1. The `echo` builtin prints its arguments to `stdout`, with spaces between them, and a newline at the end.
    1.

    ```shell
     $ echo hello world
     hello world
     $ echo one two three
     one two three
    ```
1. The `type` builtin is used to determine how a command would be interpreted if it were used. It checks whether a command is a builtin, an executable file, or unrecognized.
    1. The `type` builtin can search for executable files using `PATH`. For example, if the `PATH` is set to `/dir1:/dir2:/dir3`, the shell would search for executables in `/dir1`, then `/dir2`, and finally `/dir3`, in that order.

        ```shell
         $ type echo
         echo is a shell builtin
         $ type exit
         exit is a shell builtin
         $ type invalid_command
         invalid_command: not found
         $ type grep
         grep is /usr/bin/grep
         $ type invalid_command
         invalid_command: not found
         $ type echo
         echo is a shell builtin
        ```
1. Support for running external programs with arguments
1. When a command isn't a builtin, the shell would:
   1. Search for an executable with the given name in the directories listed in PATH (just like type does)

    ```shell
     $ type echo
     echo is a shell builtin
     $ type ls
     ls is /bin/ls
     $ type grep
     grep is /usr/bin/grep
    ```
1. `pwd` (print working directory) builtin prints the full, absolute path of the current working directory to stdout.
1. `cd` builtin is used to change the current working directory.

## Quotes ##

### Single Quotes ### 
Single quotes (') disable all special meaning for characters enclosed within them. Every character between single quotes is treated literally.
1. Characters inside single quotes (including escape characters and potential special characters like $, *, or ~) lose their special meaning and are treated as normal characters.
1. Consecutive whitespace characters (spaces, tabs) inside single quotes are preserved and are not collapsed or used as delimiters.
1. Quoted strings placed next to each other are concatenated to form a single argument.

E.G.

| Command              | Expected output | Explanation                                                  |
| --- | --- |--------------------------------------------------------------|
| echo 'hello    world'| hello    world  | Spaces are preserved within quotes                           |
| echo hello    world	| hello world  | Consecutive spaces are collapsed unless quoted               |
| echo 'hello''world'| helloworld | Adjacent quoted strings 'hello' and 'world' are concatenated |
| echo hello''world| helloworld  | Empty quotes '' are ignored                                  |

The `cat` command, with the file name parameter enclosed in single quotes:
```
$ cat '/tmp/file name' '/tmp/file name with spaces'
content1 content2
```
where `/tmp/file name` equals content1 and `/tmp/file name with spaces` equals content2 

### Double Quotes ###

In shell syntax, most characters within double quotes (") are treated literally. This implementation covers
1. Consecutive whitespaces (spaces, tabs) must be preserved. 
1. Characters that normally act as delimiters or special characters lose their special meaning inside double quotes and are treated literally. 
1. Double-quoted strings placed next to each other are concatenated to form a single argument.

E.G.

```
$ echo "hello    world"
hello    world         # Multiple spaces preserved

$ echo "hello""world"
helloworld             # Quoted strings next to each other are concatenated.

$ echo "hello" "world"
hello world            # Separate arguments

$ echo "shell's test"
shell's test           # Single quotes inside are literal
```

The `cat` command, with the file name parameter enclosed in single quotes:
```
$ cat "/tmp/file name" "/tmp/file 'name with 'spaces"
content1 content2
```
where `/tmp/file name` equals content1 and `/tmp/file 'name with 'spaces` equals content2
