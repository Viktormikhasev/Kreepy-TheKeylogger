# Kreepy-TheKeylogger

This software system will be a Key Logging cum “User Activity Control” software. This system is designed to track the activities which a particular user performs on his/her computer. These be recorded and saved for later to be monitored and inspected by the ‘super user’ who has installed the software on the user’s system. 
 It provides tools to capture key stokes, mouse movements, mouse clicks, location of the user, screenshots of screen (in special cases: such as on opening new applications). By keeping the track of the activities performed by the user, the ‘super user’ could have control over the actions performed by the user and will enable a surveillance to ensure that only work/actions specified by the ‘super user’ are performed on the system. 
More specifically, this system is designed to allow the ‘super user’ to track and restrict the activities performed by a particular user on a system. 


## Process Flow in Project(Main Parts in project and their working)

The process flow starts from the Main Program.

An object of class security secure is created in Main.java which checks whether a password exists or not at the very start of the program.

If there is no file named password.txt in project directory then it will prompt user for a password .
Password Security

The user is allowed to only enter a password in certain manner(i.e. password must be minimum 8 characters in length, must have a special character, must have a number, must have  a lower case and upper case alphabet).
	This security of password is formulated using ‘regex’ in java.

Now the password entered by user will be entertained if it will be a good password and then a hash of password will be calculated using the strongest and modern cryptographic function ‘SHA-256’.

The ‘SHA-256’ is a one way hash function meaning we cannot get the password from the hash again.

Therefore it can only be broken by Brute force.

And with such a combination of password characters as ours this will need billions of attempts and the twist is that we are also saving a log for any unsuccessful attempt with a photo of the intruder and that too is sent to the registered email after 1 minute of unsuccessful login.

Therefore it become impossible to breach the security without being noticed.

### Unsuccessful Login Email Sending

The unsuccessful login attempt is send via email to the desired recipient with security being prime concern.

The event is run using a timer class which schedule the email sending.

Multithreading is used in order so that the main process and email process do not interfere in each other working and full utilization of system resources are done.

The exception in email sending are also hold properly:-
1.	By scheduling a resend of email after 1 minute in case internet is working.
2.	By scheduling to send the email after 1 hour if internet is not working.
3.	Three attempts are done to properly send the email

The email sending part is controlled by(emailHelper.java and mailSender.java).

The mail is sent using SMTP(the standard protocol for sending emails) vide TLS(Transport Layer Security) which provides proper encryption to the message being sent.

Since SMTP uses Symmetric Cryptography(i.e. the key for encrypting and decrypting the message is same),the message is sent more privately and securely as well as with full Integrity.

The email of unsuccessful login contains two things:-
1.	The date and time of the login
2.	The photo of the intruder.

The message to be sent via email is designed using MIME MESSAGE which is the standard of automated email sending.

A multipart is made to which message body parts(one containing information and other containing file to be attached ) are added.

A proper subject and recipient is assigned. Then the multipart is added to the message and the message is sent.


Same thing is carried out for sending Key Strokes report generated which is send to user at desired intervals as determined by the user.


### The scheduleTimer.java class

The class is responsible for sending emails at scheduled times and also for running the process of email in a separate thread so that the keylogger is always active.

### The KeyLogging part

For keylogging at the very beginning in Main.java the Java Native Hook library hook is registered.
JNativeHook is a library to provide global keyboard and mouse listeners for Java. This will allow you to listen for global shortcuts or mouse motion that would otherwise be impossible using pure Java. To accomplish this task, JNativeHook leverages platform-dependent native code through Java's native interface to create low-level system-wide hooks and deliver those events to the application.

The logging is done using the proceed_with_logging function in keylogger_main.java.  

	The listeners are used wisely and every  possible care is taken to include all the letters in the keyboard and to add them in the same sequence in which they appear.

Care is taken when user happens to do functions like ctrl-s for save to be properly aligned with the keystroke recording.


### The Active Application Changed and Java Native access

The active application at any time is determined using the JNA library which uses windows handlers in order to record the current active window.

The keystrokes are written in file in such a way so that they can be categorized to the application in which they were typed.

### Screenshot recording and Webcam Capture

The screenshots are captured using the ROBOT class in java. This class is generally used in the Testing phase of SDLC in order to do automated testing via test cases.

	We have leveraged the power of this class in our project to capture screenshots as and when the active application changes.


The webcam capture is achieved using the library Webcam_capture provided by Sarxos.

It will take a photo as and when some unauthorized person will try to enter into the keylogger software.


### The JTabbed Pane GUI (MainDialog.java)

The MainDialog.java is the class which is used for showing the keystrokes, invalid logins, screenshots and other important functionalities such as changing email, enabling/disabling screenshot service etc.

The GUI of the tabbed Panes(that are showing screenshots,logs,etc)
are shown with the help of JList.

A list of buttons are created having clear and concise information of the content they store and whenever any attempt is done to open the button a JFrame will open showing the info regarding that button event.

Thus the interface is designed to ensure the best user experience.

### File Permissions via FilePermissions.java

This class was used to increase the security of the data that is being stored.

The class contains method to Grant File Permissions:-

1.Make file Unhidden
2.Make it readable
3.Make it writable

Method:Revoke File Permissions:-

1.Make file hidden
2.Make file non-readable
3.Make file non-writable

The idea of file permissions is to hide the file in such a way so that even when the option of hidden files and folders is turned on we are not able to see the file.

The inspiration of this idea came from the virus (Shortcut maker generally found in Public Computers like computers in lab) which changes the attributes of file.

So taking the same line we have added 3 attributes +h(for hidden), +r(non-readable), +s for making the file a text file.

Due to addition of these attributes the files cannot be seen even when the show hidden items is turned on.

Thus, this leads to an extra layer of security to our data which is our prime concern.
