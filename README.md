# My Personal Project

## Functionalities
This application will keep track of the results of
the Chess games that the user plays and will allow
the user to gain insight into their performance by
calculating and organizing the data of the games
the user inserts.

The following points detail some of the statistics
that the application will display:
- **Win/draw/loss** rate across specific timeframes
- **Win/draw/loss** rate depending on the colour of 
the pieces the user used each game
- **Win/draw/loss** rate depending on the type of 
Chess opening the user played

## Intended Demographic
This application is intended to be used by both
over-the-board and online Chess players who want
to have easy access to some of their Chess
statistics.

## Reason for Interest
I am only an above average Chess player, but I have
a fascination for statistics, especially those
that involve my personal interests. I enjoy drawing 
inferences from data, so I believe that creating this
application will be an enjoyable process with an
*intrinsic* reward.

## User Stories
- As a user, I want to be able to add a Chess match
to my list of Chess matches.
- As a user, I want to be able to see how many
matches I have played in a given timeframe.
- As a user, I want to be able to see my
win/draw/loss rate over all of my matches.
- As a user, I want to be able to see my 
win/draw/loss rate depending on whether I played
as white or black that game.
- As a user, I want to be able to see the number of
matches I have played in a certain time of day.
- As a user, I want to be able to save my chess match log
to file (if I so choose).
- As a user, I want to be able to load my chess match log
from file (if I so choose).

## Instructions for Grader
- You can generate the first required action related to
adding Xs to a Y by pressing the "Add Chess Match" 
button from the menu, and then pressing one of the
win/draw/loss buttons, either black or white, and then
filling in the date in MM/DD/YYYY format and filling
in the time in XX:XX 24 hour time format, and then 
press the "Confirm" button.
- You can generate the second required action related to
  adding Xs to a Y by pressing the "View Statistics"
  button from the menu, and then pressing the
  "View Overall Win Rate" button.
- You can locate my visual component by looking at
the background of any of the frames.
- You can save the state of my application by pressing
the "Save Chess Log" button from the menu.
- You can reload the state of my application by
pressing "Load Chess Log" button from the menu.

## Phase 4: Task 2
- chess match added to match log
- all matches displayed
- total number of matches displayed
- overall win rate displayed
- chess match added to match log
- total number of matches displayed
- overall win rate displayed
- all matches displayed

Note: Loading the saved match log will be logged with 
"chess match added to match log" for every match that
is loaded in.

## Phase 4: Task 3
Looking at my UML diagram, I noticed that the classes
ViewStatisticsFrame, ChessLogApp, and AddMatchFrame all
have a single field of MatchLog, which means that I could
have likely created a parent class which included that
field, and made the three classes all extend off of that
new parent class.

Additionally, I realized that all three of those classes
mentioned above have their own JFrame, so perhaps I could
make the parent class abstract and include abstract methods
to initialize a JFrame and the necessary components involved.
By refactoring the project in that way, I would make the 
relationship between those classes more clear and a
new UML diagram would reflect that relationship.