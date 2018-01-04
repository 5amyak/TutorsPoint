# Tutor’s Point

An online learning platform for the video tutorials on various courses provided by different tutors around the globe. 
Where tutors would create courses and upload videos and students would be able to watch them and learn.

## Getting Started

Check the UML Class diagram present inside _UML Diagrams_ folder to better understand the project flow.

Before trying to run the project create a database for the project by importing __TutorsPoint.sql__ file.

To run the project simply run the __TutorsPoint.jar__ file.
Run the server code present in _src_ folder before uploading any videos.

I used XAMPP server for development purpose to store videos.

## Dependencies

1. External JAR's
    * vlcj-3.10.1
    * mysql-connector
2. Install VLC as vlcj uses its System libraries.
3. Add __slf4j-nop-_version_.jar__ to avoid _Failed to load class org.slf4j.impl.StaticLoggerBinder_ error.

## Main Features

1. Create or manage a Student/Teacher account
2. Teacher Account:
    * Create course and divide course into subtopics
    * Upload videos and add tags into subtopics
    * Manage content
    * track stats of content
3. Student Account:
    * watch/like/comment videos
    * search based on tags/author/subject
    * rate courses and maintain a watch later list

## Built With

* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - The Java IDE for Professional Developers by JetBrains
* [phpMyAdmin](https://www.phpmyadmin.net/) - GUI for MySql

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
