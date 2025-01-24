Social Fitness App
==================

Description
-----------

Social Fitness is an Android app designed to help users stay motivated and engaged in the gym. It generates personalized workouts based on the user's available time and targeted muscle groups. The app also incorporates a social aspect, allowing users to add friends, share their workouts with photos, and view and interact with their friends' posts. Users can track each other's stats and engage in friendly competition to boost their fitness journey.

* * * * *

Current Features
----------------

### Screens and Functionalities

#### 1\. Sign Up Screen

-   Allows users to input the following information:

-   Name

-   Last Name

-   Gender

-   Birthdate

-   Username

-   Password (hashed using SHA-256 for security).

#### 2\. Login Screen

-   Prompts users to enter their username and password.

-   Validates credentials against the database.

-   Denies access if credentials do not match or are not found.

#### 3\. Welcome Screen

-   Greets the user by name with a random greeting message.

-   Displays the app logo.

-   Includes a "Back" button to return to the login page and a "Get Started" button to navigate to the home page.

#### 4\. Home Screen

-   Top Left: Placeholder for the user's profile picture (currently shows the app logo).

-   Top Right: Settings icon with a logout button.

-   Center Widgets:

-   Weather Widget: Displays the current weather, low/high temperature, feels like temperature, humidity percentage, UV index, and visibility (data fetched using a weather API).

-   Music Widget: Fixed album image with back, pause/play, and next buttons (Spotify API integration planned).

-   User Stats Widget: Placeholder to display user stats (under development).

-   Workout Widgets:

-   Current workout in progress.

-   Previous workout details.

-   Bottom Navigation Buttons:

-   Friends Icon: Navigates to the Friends screen.

-   Home Icon: Returns to the Home screen.

-   Workout Icon: Navigates to the Workout screen.

#### 5\. Workout Screen

-   Displays current workout information (if active).

-   "Generate" button for creating a new workout:

-   Prompts the user to select 1 to 3 muscle groups from the following:

-   Chest, Legs, Cardio, Back, Arms, Abs, Biceps, Triceps, Shoulders.

-   Prompts the user to choose a workout duration:

-   Short, Medium, or Long.

-   Generates a workout and allows the user to re-generate if not satisfied before starting.

-   "Start" button: Placeholder with a message saying "UI not ready."

-   Plans for future implementation:

-   Display generated workout details (sets, reps, duration).

-   Timer to track workout progress.

-   Option to save the workout and post it with a photo, caption, and current song.

#### 6\. Friends Screen

-   Displays friends' posts in an Instagram-style feed, including:

-   Friend's name (clickable to view their stats).

-   Workout details (type, duration, etc.).

-   Daily song.

-   Date and caption.

-   Like button to interact with posts.

-   Top Right Corner: Options to:

-   Add/Search/Remove friends.

-   View friend requests.

* * * * *

Future Features
---------------

### Home Page Enhancements

-   Spotify API integration for real-time music widget functionality.

-   Expanded settings options.

-   Additional widgets:

-   User stats.

-   Previous workouts.

-   Current workout progress.

-   Features to:

-   Edit user stats.

-   Track streaks.

-   Earn achievements (e.g., streaks, max bench press, max squat).

### Workout Screen Enhancements

-   Refine workout generation algorithm.

-   Display full workout details:

-   Sets and reps for each exercise.

-   Real-time workout timer.

-   Add functionality for users to:

-   Upload a profile image.

-   Take and post workout photos.

### Competitive Features

-   Streak counts.

-   Achievements to encourage engagement.

### Additional Features

-   Continue brainstorming and adding features during development.

* * * * *

Technologies Used
-----------------

-   Programming Language: Kotlin, XML

-   Security: SHA-256 hashing for password encryption.

-   APIs:

-   Weather API for real-time weather data.

-   Spotify API (planned).

-   Database: Firebase Real-time Database to store user information, workouts, and social interactions.

* * * * *

Current Status
--------------

The app is under active development with several core features already implemented. The focus is now on enhancing existing screens, adding planned features, and improving the user experience.
