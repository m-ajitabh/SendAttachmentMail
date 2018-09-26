Do the email related setup in credentials.xml..

<credential id="393">
      <hostserver>smtp.zoho.com</hostserver>
      <portno>587</portno>
      <username>abc@def.com</username>
      <password>welcome123</password>
      <toemailid>abc@gmail.com</toemailid>
      <pathtofile>testreport.html</pathtofile>
      <ssltlsenable>true</ssltlsenable>
      
   </credential>
   
   
   hostserver - email hosting server  
   
   portno - port number for that server, check for SSL/TSL
   
   username - username of email hosting server
   
   password - password of user
   
   toemailid - send to, single email or multiple email seperated by comma ,
   
   pathtofile - path of file to attach and send
   
   ssltlsenable - true if ssl is enabled