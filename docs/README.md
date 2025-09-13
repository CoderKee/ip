# Kee User Guide 😎

Kee is an ultra-friendly graphical interface user(GUI) app that 
helps you manage your tasks with ease.

To start using Kee, follow these steps:
1. Go to the [release page](https://github.com/CoderKee/ip/releases/tag/A-Jar).
2. Copy the `jar` file into an empty folder.
3. Open a command window in that folder.
4. Run the command:
```sh
   java -jar "{filename}.jar"
```

## Features 🛠️

Kee is equipped with multiple features that make task management simple and intuitive:
- [x] Create new tasks
- [x] List tasks
- [x] Mark and unmark tasks
- [x] Delete tasks
- [x] Find tasks
- [x] Remind upcoming deadlines
- [x] Store and loading tasks

## Adding new tasks

Kee separates tasks into 3 categories

### Todo tasks

Todo task refers to task that do not have deadlines/start time/end time

Command: ``todo <task>``

Example:
```
> todo code
Okay I've added:
[T][] code
Now you've got 1 task(s)
```

### Deadline tasks

Deadline task refers to task that has a deadline

Command: ``deadline <task> /by <deadline>``

Note that <deadline> must be in the format ``d/M/yyyy HH:mm``

Example:
```
> deadline homework /by 8/9/2025 18:00
Okay I've added:
[D][] homework (by: 8 September 2025 6:00pm)
Now you've got 2 task(s)
```

### Event tasks

Event task refers to task that has a start time and end time

Command: ``event <task> /from <start> /to <end>``

Note that <start> and <end> must be in the format ``d/M/yyyy HH:mm``

Example:
```
> event lecture /from 9/9/2025 12:00 /to 9/9/2025 14:00
Okay I've added:
[E][] lecture (by: 9 September 2025 
12:00pm to: 9 September 2025 2:00 pm)
Now you've got 3 task(s)
```

## Listing tasks

Listing tasks returns a numbered list of all tasks

Command: ``list``

Example:
```
> list
Here are your tasks:
1. [T][] code
2. [D][] homework (by: 8 September 2025 6:00pm)
3. [E][] lecture (by: 9 September 2025 
12:00pm to: 9 September 2025 2:00 pm)
```

## Marking tasks

Marking a task indicates that the task is completed

Command: ``mark <task number/ task name>``

Example:
```
> mark 1
Congratulations on completing:
[T][X] code
```

## Unmarking tasks

Unmarking a task indicates that the task is not completed

Command: ``unmark <task number/ task name>``

Example:
```
> unmark 1
Ok, I've unmarked:
[T][] code
```

## Deleting tasks

Deleting a task removes it from the list

Command: ``delete <task number/ task name>``

Example:
```
> delete 1
Okay, I've removed:
[T][] code
Now you've got 2 task(s)
```

## Finding tasks

Finding a task returns tasks that contain the keyword specified

Command: ``find <keyword>``

Example:
```
> find home
Here are the matching tasks I found:
1. [D][] homework (by: 8 September 2025 6:00pm)
```

## Reminding

Reminding returns a list of tasks with deadlines ending today

Command: ``remind``

Example (Assuming today is 8/9/2025):
```
> remind
Here are your deadlines for today:
1. [D][] homework (by: 8 September 2025 6:00pm)
```

## Storing and loading tasks

Tasks are automatically stored in a .txt folder when user
quits the app

To quit the app:

Command: ``bye``

Tasks are automatically loaded from the .txt file upon starting the app

```
> bye
Have a good day! ^.^
(app closes)
```