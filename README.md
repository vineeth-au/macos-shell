## Overview
A POSIX compliant shell (in the near future) capable of interpreting shell commands, running 
external programs and builtin commands like cd, pwd, echo and more.

## Java Version
This version has been only tested on Java 25.

## Current Features
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
1. `cd` builtin is used to change the current working directory - Implementation in Progress

## Getting Started
Run `./your_program.sh`
