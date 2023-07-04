<table><tr><td> <em>Assignment: </em> It114 Milestone1</td></tr>
<tr><td> <em>Student: </em> Farmouth Dessin (fd238)</td></tr>
<tr><td> <em>Generated: </em> 7/3/2023 11:21:24 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-450-M23/it114-milestone1/grade/fd238" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <ol><li>Create a new branch called Milestone1</li><li>At the root of your repository create a folder called Project if one doesn't exist yet</li><ol><li>You will be updating this folder with new code as you do milestones</li><li>You won't be creating separate folders for milestones; milestones are just branches</li></ol><li>Create a milestone1.md file inside the Project folder</li><li>Git add/commit/push it to Github (yes it'll be blank for now)</li><li>Create a pull request from Milestone1 to main (don't complete/merge it yet, just have it in open status)</li><li>Copy in the latest Socket sample code from the most recent Socket Part example of the lessons</li><ol><li>Recommended Part 5 (clients should be having names at this point and not ids)</li><li><a href="https://github.com/MattToegel/IT114/tree/Module5/Module5">https://github.com/MattToegel/IT114/tree/Module5/Module5</a>&nbsp;<br></li></ol><li>Fix the package references at the top of each file (these are the only edits you should do at this point)</li><li>Git add/commit the baseline</li><li>Ensure the sample is working and fill in the below deliverables</li><ol><li>Note: The client commands likely are different in part 5 with the /name and /connect options instead of just connect</li></ol><li>Get the markdown content or the file and paste it into the milestone1.md file or replace the file with the downloaded version</li><li>Git add/commit/push all changes</li><li>Complete the pull request merge from step 5</li><li>Locally checkout main</li><li>git pull origin main</li></ol></td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Startup </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot showing your server being started and running</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-03T20.41.06Module%205%20Part%204%20Server%20Active.png.webp?alt=media&token=18696084-a3d8-4456-9b5d-e29eb25966c1"/></td></tr>
<tr><td> <em>Caption:</em> <p>Module 5 Part 4 Server start up<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-03T20.41.30Module%205%20Part%205%20Server%20Active.png.webp?alt=media&token=d7f97926-1761-4798-b7aa-e734a6b8809e"/></td></tr>
<tr><td> <em>Caption:</em> <p>Module 5 Part 5 Server start up<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot showing your client being started and running</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-03T20.42.58Module%205%20Part%204%20Client%20Active.png.webp?alt=media&token=7cceeb24-e9b0-46e4-abc2-230db869c44f"/></td></tr>
<tr><td> <em>Caption:</em> <p>Module 5 Part 4 Client start up<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-03T20.43.15Module%205%20Part%205%20Client%20Active.png.webp?alt=media&token=d4cb7c9f-d33a-433d-90eb-9c467a1326b4"/></td></tr>
<tr><td> <em>Caption:</em> <p>Module 5 Part 5 Client start up<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-03T21.05.21Module%205%20Part%204%20Client%20Conn%20Success.png.webp?alt=media&token=32958358-9dd0-4909-af00-5e580e64669a"/></td></tr>
<tr><td> <em>Caption:</em> <p>Part 4 Client Connect Successful<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-04T00.22.23Module%205%20Part%205%20Client%20Conn%20Success.png.webp?alt=media&token=6275c981-5d9a-4612-a0f0-ea66eed6c893"/></td></tr>
<tr><td> <em>Caption:</em> <p>Part 5 Client Connect Successful<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the connection process</td></tr>
<tr><td> <em>Response:</em> <p>The clients connect via the socket objects that created by the server when<br>initialing connections. The server loops around waiting for the clients to connect by<br>calling the serverSocker.accept(). When the client connect the accept() returned a new socket<br>object confirm connection.<div><br></div><div>The connect process allow clients to establish a connect the server<br>through the socket. Server create a separate thread to handle communication with each<br>client</div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Sending/Receiving </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing evidence related to the checklist</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-04T00.57.34%20Part4-%202%20Client%20Messages.png.webp?alt=media&token=bf4df34b-15f9-400a-8b96-1a0bc343d3f0"/></td></tr>
<tr><td> <em>Caption:</em> <p>Part 4- 2 Clients Message<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-04T00.59.49Screen%20Shot%202023-07-03%20at%208.59.39%20PM.png.webp?alt=media&token=5b6b104f-ab9c-4bec-8a03-c47d842f2d97"/></td></tr>
<tr><td> <em>Caption:</em> <p>Part 5- 2 Clients Message<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the messages are sent, broadcasted (sent to all connected clients), and received</td></tr>
<tr><td> <em>Response:</em> <p>The message are sent through a server socket that is created by the<br>server. The server socket listen for the client connections. Once the clients are<br>connected, the server create a server-thread that handle the communication between clients.<div><br></div><div>The broadcast<br>method implemented in the server.java file is responsible to broadcast messages to all<br>client. The server keep track of all the clients and rooms. While the<br>have separate chat room where clients can join. broadcast iterate over the list<br>of room each send messages to each room using the sendMessage method.</div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Disconnecting / Terminating </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing evidence related to the checklist</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-04T02.07.47Part%204-%20disconnect%3Atermed.png.webp?alt=media&token=e1450249-bd09-4a3d-8999-6196ebf7d610"/></td></tr>
<tr><td> <em>Caption:</em> <p>Part 4 disconnect/termed<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Ffd238%2F2023-07-04T02.08.37Part%205-%20disconnect%3Atermed.png.webp?alt=media&token=c9cd2a40-acc8-4303-8524-d08ac4ff04db"/></td></tr>
<tr><td> <em>Caption:</em> <p>Part 5 disconnect/termed<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the various disconnects/terminations are handled</td></tr>
<tr><td> <em>Response:</em> <p>Being that there are various disconnects/terminations are handle. A server might get a<br>exception. A catch the exception goes through ServerThread and it is handle properly&nbsp;&nbsp;<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add the pull request for this branch</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/MsMou/-IT114---450--/pull/6">https://github.com/MsMou/-IT114---450--/pull/6</a> </td></tr>
<tr><td> <em>Sub-Task 2: </em> Talk about any issues or learnings during this assignment</td></tr>
<tr><td> <em>Response:</em> <p>i did not experience many issue. I have to do a lot of<br>the studies and videos over to full grasp the understand on the chat<br>room is ran and connects.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-450-M23/it114-milestone1/grade/fd238" target="_blank">Grading</a></td></tr></table>