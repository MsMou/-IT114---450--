<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone 2</td></tr>
<tr><td> <em>Student: </em> Farmouth Dessin (fd238)</td></tr>
<tr><td> <em>Generated: </em> 7/14/2023 10:55:03 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-450-M23/it114-chatroom-milestone-2/grade/fd238" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone2 from the proposal document:&nbsp; <a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Payload </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Payload Screenshots</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.41.02Payload%20screen%201.png.webp?alt=media&token=c0a6efd0-801c-445c-af9d-2a693e95207a"/></td></tr>
<tr><td> <em>Caption:</em> <p>Payload 1<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.41.42Payload%20screen%202.png.webp?alt=media&token=8430ab82-7310-4595-987b-ae000d5dd632"/></td></tr>
<tr><td> <em>Caption:</em> <p>Payload 2<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.42.02Payload%20screen%203.png.webp?alt=media&token=942524c0-def3-43dc-8323-ca6993eeaba2"/></td></tr>
<tr><td> <em>Caption:</em> <p>Payload 3<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Server-side commands </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code for the mentioned commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.44.16Roll%20format%201%20code.png.webp?alt=media&token=332ac227-0f5f-4239-8e2c-ca6ec161c91a"/></td></tr>
<tr><td> <em>Caption:</em> <p>/roll # format 1 code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.44.36Roll%20format%202%20code.png.webp?alt=media&token=0b3756f9-090d-4260-bf4c-da8a98174256"/></td></tr>
<tr><td> <em>Caption:</em> <p>/roll #d# format 2 code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.46.09Flip%20code.png.webp?alt=media&token=988910c5-d907-42d3-89d6-c7325ed15d62"/></td></tr>
<tr><td> <em>Caption:</em> <p>/flip code<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Explain the logic/implementation of each commands</td></tr>
<tr><td> <em>Response:</em> <p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">The /roll command is used<br>to simulate the rolling dice to generate random numbers depending on the format<br>being used. For format 1 the command /roll # the client will provide<br>a single number after /roll. This will be the upper lint of the<br>range of the chosen random numbers to be generated.&nbsp; The RandomRolls() method handle<br>this method by the rollPart1 array as parameters.&nbsp; The rollPart1 array splits the<br>client input into 2 parts. A for loop is created to indicate, if<br>the length of rollPart1 is 2 it has 2 parts. A .contains("d") is<br>added to rollPart1 that allow the for the second part to search for<br>a "d". If both parts are true, length is 2 and the second<br>part contains a "d" to execute the command line /roll #d# generating random<br>numbers for the dice roll that then sent as a message using sendMessage()<br>method.<o:p></o:p></p><p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;"><o:p>&nbsp;</o:p></p><p class="MsoNormal" style="margin: 0in; font-size:<br>12pt; font-family: Calibri, sans-serif;"><o:p>&nbsp;</o:p></p><p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">The flipToss()<br>method uses the /flip command. The code initializes a Random object name random<br>the generate a random number that calls random.nextInt(2). random.nextInt(2) generate random number between<br>0 and 2 that determine the flip result. This represents the output of<br>heads or tails.<o:p></o:p></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Text Display </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code for the various style handling via markdown or special characters</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.47.39Text%20code.png.webp?alt=media&token=6438443c-9503-4a2c-b4ec-b8bfeee6f6cb"/></td></tr>
<tr><td> <em>Caption:</em> <p>Text change code<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show source message and the result output in the terminal (note, you won't actually see the styles until Milestone3)</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-15T02.49.18Text%20changes%20terminal%20.png.webp?alt=media&token=785c587e-6ca1-4545-a79f-d48c3a2db897"/></td></tr>
<tr><td> <em>Caption:</em> <p>Text change terminal<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Explain how you got each style applied</td></tr>
<tr><td> <em>Response:</em> <p class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">For TextChanges method, text.replace is<br>used for bold, italic, color change, and underline. By using * * for<br>bold, _ _ for italic, # for color change, and ^ ^ for<br>underline. Once these command are enter it print out text = text.replace("*", "&lt;/b&gt;");<o:p></o:p></p><p<br>class="MsoNormal" style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">&nbsp;&nbsp;&nbsp; text = text.replace("_", "&lt;/i&gt;");<o:p></o:p></p><p class="MsoNormal"<br>style="margin: 0in; font-size: 12pt; font-family: Calibri, sans-serif;">&nbsp;&nbsp;&nbsp; text = text.replace("#", "\"&gt;");<o:p></o:p></p><p class="MsoNormal" style="margin:<br>0in; font-size: 12pt; font-family: Calibri, sans-serif;">&nbsp;&nbsp;&nbsp; text = text.replace("^", "&lt;/u&gt;"); in return.<o:p></o:p></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Include the pull request for Milestone2 to main</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/MsMou/-IT114---450--/pull/10">https://github.com/MsMou/-IT114---450--/pull/10</a> </td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-450-M23/it114-chatroom-milestone-2/grade/fd238" target="_blank">Grading</a></td></tr></table>