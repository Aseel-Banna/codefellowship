# CodeFellowship Application
The application uses localhost:8282.
In the main page, user can sign up by clicking on the link that will preview a sign up form for the user. Other routes available:
1. /signup - page for user sign up.
2. /users/{id} - page displayed after signing up. Displays basic info.
3. /login - page that allows user to log-in to the site.
4. /myprofile - page that displays the user info.
5. /posts/add - page that allows user to add post.
6. /allUsers - page that holds all the users in database
7. /follow/{id} - this route appears when user follow another user.
8. /feed - page that views all of the posts from the users that they follow.

## User Story
* As a user, you have to use ***/signup*** to create a profile or if you have created one, use ***/login*** to log in to your profile. 
* Use ***/users/{id}*** to get your information.
* Use ***/posts/add*** route to add a post, then use ***/myprofile*** to access your information and your posts
* Use ***/allUsers*** route so, you can follow other users.
* Use ***/feed*** route to see all posts from users that you are following.

## To run the application
1. Clone the repo and go to the directory. On the terminal, run `./gradlew bootRun`
2. Open your web browser and go to localhost:8282