<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone4</td></tr>
<tr><td> <em>Student: </em> Farmouth Dessin (fd238)</td></tr>
<tr><td> <em>Generated: </em> 8/9/2023 12:31:10 AM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-450-M23/it114-chatroom-milestone4/grade/fd238" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone3 from the proposal document:&nbsp;&nbsp;<a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Client can export chat history of their current session (client-side) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot of related UI</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.36.00Screen%20Shot%202023-08-08%20at%2011.35.01%20PM.png.webp?alt=media&token=b513697c-3a16-4104-b304-cced0e1a28ab"/></td></tr>
<tr><td> <em>Caption:</em> <p>Menu -history button<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot of exported data</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.37.15Chat-History%20Norm.png.webp?alt=media&token=fbec8864-7bd7-4d0c-84fb-0ce0bf75d838"/></td></tr>
<tr><td> <em>Caption:</em> <p>Chat history export<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.38.25Styling%3Aformatting.png.webp?alt=media&token=b6e4e71b-6c9d-473c-8b34-21858dede59a"/></td></tr>
<tr><td> <em>Caption:</em> <p>Styling formatting <br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">The method goToChatHistory() &nbsp;return the<br>entire the chat history of string that is stored into the sameChatHistory list.<o:p></o:p></p><p<br>class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;"><o:p>&nbsp;</o:p></p><p class="MsoNormal" style="margin: 0in; font-size: 12pt;<br>font-family: Calibri, sans-serif;">This method addMessageToHistory takes to parameter, String message and String clientName<br>that add new message to the chathistory &nbsp;</p><p class="MsoNormal" style="margin: 0in; font-size: 12pt;<br>font-family: Calibri, sans-serif;">The getFormattedChatHistory() method than return the chat history in a form<br>of string<o:p></o:p></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Client's mute list will persist across sessions (server-side) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a screenshot of how the mute list is stored</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.45.49Screen%20Shot%202023-08-08%20at%2011.45.33%20PM.png.webp?alt=media&token=70d257e0-427a-4958-a240-29c00c968dfc"/></td></tr>
<tr><td> <em>Caption:</em> <p>file<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add a screenshot of the code saving/loading mute list</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.44.40Screen%20Shot%202023-08-08%20at%2011.43.20%20PM.png.webp?alt=media&token=0d52e26e-1d14-4a59-937a-2851c804d03b"/></td></tr>
<tr><td> <em>Caption:</em> <p>Save-Load mute list<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">The saveList is save to<br>the mutedclients set into “mutedList.dat” file using the ObjectOutputStream.<o:p></o:p></p><p class="MsoNormal" style="margin: 0in; font-size:<br>12pt; font-family: Calibri, sans-serif;"><o:p>&nbsp;</o:p></p><p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">The loadList<br>loads the mutedClient set form “mutedList.dat” file using the<o:p></o:p></p><p class="MsoNormal" style="margin: 0in; font-size:<br>12pt; font-family: Calibri, sans-serif;">&nbsp;ObjectOutputStream.<o:p></o:p></p><p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;"><o:p>&nbsp;</o:p></p><p class="MsoNormal"<br>style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">ReadObject(ObjectInputStream inputStream) give the ability to read<br>the files form ObjectInputStream<o:p></o:p></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Client's will receive a message when they get muted/unmuted by another user </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a screenshot showing the related chat messages</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.47.34Muted%20Ric.png.webp?alt=media&token=b067ab3a-2935-476a-92de-2c2a0a5081ca"/></td></tr>
<tr><td> <em>Caption:</em> <p>Client inform muted<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add a screenshot of the related code snippets</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.49.35Screen%20Shot%202023-08-08%20at%2011.49.16%20PM.png.webp?alt=media&token=49aecd6c-3992-4b95-bb94-ed6bc8323bc1"/></td></tr>
<tr><td> <em>Caption:</em> <p>Mute/unmute code<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">The muteUser mutes the target<br>client once found it notify the target client, that they have been muted<br>and I created a separate broadcasting message to send message to target client.<o:p></o:p></p><p<br>class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;"><o:p>&nbsp;</o:p></p><p class="MsoNormal" style="margin: 0in; font-size: 12pt;<br>font-family: Calibri, sans-serif;">Once unmuted the target client received the same thing<o:p></o:p></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> User list should update per the status of each user </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot for Muted users by the client should appear grayed out</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.50.55Grayed%20mute%20screen.png.webp?alt=media&token=50f4ea8a-8086-47aa-9199-045e4a2315f9"/></td></tr>
<tr><td> <em>Caption:</em> <p>attempt to gray out<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot for Last person to send a message gets highlighted</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-08-09T03.52.03Highlights%20screen.png.webp?alt=media&token=1dc932c5-865c-4188-9df7-6333073fd027"/></td></tr>
<tr><td> <em>Caption:</em> <p>attempt highlight<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>isMuted method is pulled from serverThread to determine if the client is muted<br>or not. If the client is muted it get grey out.<div><br></div><div>isLastMessanger looks for<br>the last send and verify it is not the same send and highlights<br>it yellow. Neither function worked properly</div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-450-M23/it114-chatroom-milestone4/grade/fd238" target="_blank">Grading</a></td></tr></table>