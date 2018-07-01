# My Journal App
<br/><br/>
ALCWithGoogle3-slack 
Profile name: <b>BigPictureDisciple<b/>
 <br/><br/>
This is my attempt at the 7 Days of code challenge - ALCWithGoogle 3.0<br/>

As per project instructions :-<br/> 
<br/>
At minimum users should be able to:<br/>
 1.Register and Login using google authentication.<br/>
 2.View all entries to their diary.<br/>
 3.View the contents of a diary entry.<br/>
 4.Add and modify an entry.<br/><br/>
I affirm that feature 1 is <b>INCOMPLETE</b>.<br/>
<br/>
<br/>
Minimal implementation:<br/>
User is requested to sign-in via the SIGN-IN button, using<br/>
a Google account.If none exists,user may tap on CREATE ACCOUNT button, and is redirected to Google's 
account creation page.<br/>
![alt text](journalapp.png "Screenshot of landing screen")<br/><br/>

![alt text](google-account-screen.png "Screenshot of google screen")<br/>
After creating a Google account, user may return
to app and sign-in via the Google sign-in button
<br/>
![alt text](sign-button.png "Screenshot of sign-in")<br/>
<br/><br/>
After successful authentication, user is brought to the "Home screen".<br/>
This will be empty, for new users(or if no entries have been saved).<br/>
<br/>
![alt text](home-screen.png "Screenshot of home screen")<br/>
User is prompted to add entries on empty screen
<br/><br/>

<br/>
NOTE: This feature is incomplete, as there is NO AUTHENTICATION feature
set.
<br/>
<br/><br/>
Dialog box gives user means of adding journal entry and saving it<br/>
![alt text](create-entry-screen.png "Screenshot of new entry screen")
<br/>
Entries are modifiable(Edit/Delete)<br/>
<b>LONG PRESS<b/> on the entry to call up "Select Action" menu<br/>
 
![alt text](delete-screen.png "delete entry screen")<br/>
 
![alt text](edit-entry.png "Screenshot of screen")<br/>

Entries are displayed<br/>
![alt text](added-entry.png "Screenshot of entry screen")<br/>

There's a settings option intended<br/>
![alt text](settings-menu.png "launch settings screen")<br/>

User may customize app appearance(un-implemented)<br/>
![alt text](settings-screen.png "Settings screen")<br/>

Over all, I concede that I was unable to successfully meet all the minimum requirements within the afforded time.
<br/>
I utilize SQLite3 for the app, however I must state that I'd looked into using Firebase. 
